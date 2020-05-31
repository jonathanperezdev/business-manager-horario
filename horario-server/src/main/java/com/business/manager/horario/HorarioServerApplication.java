package com.business.manager.horario;

import com.business.manager.horario.dao.entities.Empleado;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.business.manager.horario"})
@EntityScan(basePackages = {"com.business.manager.horario.dao"})
public class HorarioServerApplication {

	public static void main(String[] args) {
		Empleado empleado =  new Empleado();
		SpringApplication.run(HorarioServerApplication.class, args);
	}

}
