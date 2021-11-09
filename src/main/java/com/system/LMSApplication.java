package com.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(LMSApplication.class, args);
		System.out.println("LEND-A-BOOK");
	}

}