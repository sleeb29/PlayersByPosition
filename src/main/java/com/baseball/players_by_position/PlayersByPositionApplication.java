package com.baseball.players_by_position;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan({"com.baseball.players_by_position.model",
		"com.baseball.players_by_position.service.service",
		"com.baseball.players_by_position.service.repository",
		"com.baseball.players_by_position.controller",
		"com.baseball.players_by_position.view",
		"com.baseball.players_by_position.view.mapper",
		"com.baseball.players_by_position.configuration.properties",
		"com.baseball.players_by_position.external.provider",
		"com.baseball.players_by_position.configuration.web"})
@EnableJpaRepositories("com.baseball.players_by_position.service.repository")
@EnableAsync
@PersistenceContext
@EnableCaching
public class PlayersByPositionApplication {

	private static final Logger logger = Logger.getLogger(PlayersByPositionApplication.class.getName());

	public static void main(String[] args) {

		logger.info("Starting up PlayersByPositionApplication");
		SpringApplication.run(PlayersByPositionApplication.class);

	}

}
