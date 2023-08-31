package com.company.gamestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@EnableResourceServer
@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class})
public class GameStoreApplication {

	public static void main(String[] args) {

		SpringApplication.run(GameStoreApplication.class, args);
	}

}
