package com.example.Final.Project.Forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.config.LoadBalancerAutoConfiguration;
import org.springframework.cloud.loadbalancer.config.BlockingLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
		LoadBalancerAutoConfiguration.class,
		BlockingLoadBalancerClientAutoConfiguration.class
})
@EnableEurekaClient
public class FinalProjectForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectForumApplication.class, args);
	}

}
