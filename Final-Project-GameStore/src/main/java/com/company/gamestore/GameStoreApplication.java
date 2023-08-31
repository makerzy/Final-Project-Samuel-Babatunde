package com.company.gamestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//Comment out to test the graphql
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@EnableResourceServer // comment out to test the graphql
@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class})
public class GameStoreApplication {

	public static void main(String[] args) {

		SpringApplication.run(GameStoreApplication.class, args);
	}

}
