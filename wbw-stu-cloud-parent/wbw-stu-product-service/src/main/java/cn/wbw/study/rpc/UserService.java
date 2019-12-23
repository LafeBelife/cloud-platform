package cn.wbw.study.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * rpc 用户服务
 *
 * @author wbw
 * @date 2019/12/20 10:12
 */
@FeignClient("WBW-STU-USER-SERVICE")
public interface UserService {
    /**
     * 保存数据
     *
     * @return msg
     */
    @RequestMapping("/user/save")
    String save();
}
