package cn.wbw.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka 引导程序
 * <p>
 * 只需要增加@EnableEurekaServer即可启动Eureka服务器相关功能
 * </p>
 *
 * @author wbw
 * @date 2019/12/17 9:53
 */
@EnableEurekaServer
@SpringBootApplication
public class ServiceDiscoveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryApplication.class, args);
    }
}
