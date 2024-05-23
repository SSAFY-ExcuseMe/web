package com.ssafy.excuseMe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy.excuseMe"})
public class ExcuseMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcuseMeApplication.class, args);
	}

}
