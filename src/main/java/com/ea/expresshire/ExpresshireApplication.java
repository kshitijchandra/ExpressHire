package com.ea.expresshire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;

@SpringBootApplication
public class ExpresshireApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpresshireApplication.class, args);
	}
}
