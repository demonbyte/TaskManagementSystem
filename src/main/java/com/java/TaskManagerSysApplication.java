package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.java"})
public class TaskManagerSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerSysApplication.class, args);
	}

}
