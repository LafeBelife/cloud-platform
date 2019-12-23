package cn.wbw.study.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 模板远程
 *
 * @author wbw
 * @date 2019/12/18 17:18
 */
@Component
@EnableDiscoveryClient
public class ServiceTemplate {

    @Bean(value = "restTemplate")
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
