package cn.wbw.study.config;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * feign 开启支持
 *
 * @author wbw
 * @date 2019/12/20 10:02
 */
@Component
@EnableFeignClients(basePackages = "cn.wbw.study*")
public class FeignConfig {

    /**
     * 自定义 ribbon 算法
     *
     * @return IRule
     */
    @Bean
    public IRule rule() {
//        RoundRobinRule roundRobinRule = new RoundRobinRule();//轮询
//        RetryRule retryRule = new RetryRule();//重试
//        此算法是判断默认服务是否有空余请求连接，有则请求该服务，没有则继续判断其他服务是否有空余请求连接，
//        如果请求连接都没有空闲，则调用父类(RoundRobinRule)轮询负载
//        return new BestAvailableRule();
        return new RoundRobinRule();
    }
}
