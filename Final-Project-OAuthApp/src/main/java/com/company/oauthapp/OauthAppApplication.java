package com.company.oauthapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class OauthAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthAppApplication.class, args);
	}

}
