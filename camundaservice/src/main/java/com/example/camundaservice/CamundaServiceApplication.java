package com.example.camundaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan(basePackages = {
//		"com.example.camundaservice"
//})
@EnableJpaRepositories(basePackages = "com.example.camundaservice.repository")
@EnableConfigurationProperties
public class CamundaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamundaServiceApplication.class, args);
	}

}
