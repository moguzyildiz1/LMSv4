package com.gcit.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.gcit.lms")
public class LibrarianServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarianServiceApplication.class, args);
	}
}
