package cn.wbw.study.api;

import cn.wbw.study.dao.UserRepository;
import cn.wbw.study.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户接口服务
 *
 * @author wbw
 * @date 2019/12/17 16:14
 */
@RestController
@RequestMapping("/user")
@Log4j2
public class UserApi {
    @Resource
    private UserRepository repository;

    @RequestMapping("/save")
    public String save() {
        User user = new User();
        user.setName("张三");
        user.setUrl("http://zhangsan");
        User save = repository.save(user);
        log.debug("save\t{}", save.toString());
        return "ok";
    }

    @RequestMapping("/getAll")
    public Object getAll() {
        return repository.findAll();
    }
}
