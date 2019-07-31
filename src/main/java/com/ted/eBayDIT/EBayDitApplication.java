package com.ted.eBayDIT;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EBayDitApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBayDitApplication.class, args);
	}


	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SpringApplicationContext SpringApplicationContext()  {
		return new SpringApplicationContext();
	}
}
