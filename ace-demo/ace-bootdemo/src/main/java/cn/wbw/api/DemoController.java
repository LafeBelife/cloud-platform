package cn.wbw.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping("/persion")
    public Object test(){
        return "xxxxxxxxx";
    }
}
