package com.business.manager.horario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@ComponentScan(basePackages = {"com.business.manager.horario"})
@EntityScan(basePackages = {"com.business.manager.horario.dao"})
@EnableDiscoveryClient
@RefreshScope
public class HorarioServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HorarioServerApplication.class, args);
	}

}
