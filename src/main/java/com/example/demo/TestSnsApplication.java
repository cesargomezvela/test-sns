package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class TestSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSnsApplication.class, args);
	}

}
