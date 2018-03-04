package com.baseball.players_by_position;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.persistence.PersistenceContext;

@SpringBootApplication
@ComponentScan({"com.baseball.players_by_position.model",
		"com.baseball.players_by_position.service.service",
		"com.baseball.players_by_position.service.repository",
               "com.baseball.players_by_position.controller",
               "com.baseball.players_by_position.view",
		"com.baseball.players_by_position.view.mapper",
		"com.baseball.players_by_position.configuration.properties",
		"com.baseball.players_by_position.external.provider"})
@EnableJpaRepositories("com.baseball.players_by_position.service.repository")
@EnableAsync
@PersistenceContext
@EnableCaching
public class PlayersByPositionApplication {

	public static void main(String[] args) {

		SpringApplication.run(PlayersByPositionApplication.class);

	}

}
