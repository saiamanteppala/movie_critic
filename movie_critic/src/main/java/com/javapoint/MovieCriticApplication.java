package com.javapoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MovieCriticApplication {
	
	public static void main(String[] args) 
	{
		SpringApplication.run(MovieCriticApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return (WebMvcConfigurer) new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}