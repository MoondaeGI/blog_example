package com.example.blog_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class BlogExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogExampleApplication.class, args);
		System.out.println("blog example");
	}
}
