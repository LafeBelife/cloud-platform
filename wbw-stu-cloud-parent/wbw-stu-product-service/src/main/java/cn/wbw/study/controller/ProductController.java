package cn.wbw.study.controller;

import cn.wbw.study.rpc.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 生产者
 *
 * @author wbw
 * @date 2019/12/18 17:20
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    @Qualifier(value = "restTemplate")
    private RestTemplate template;
    @Autowired
    private UserService userService;

    @RequestMapping("/user/list")
    public Object list() {
        return template.getForEntity("http://WBW-STU-USER-SERVICE/user/getAll", List.class).getBody();
    }

    @RequestMapping("/user/save")
    public String save() {
        return userService.save();
    }
}
