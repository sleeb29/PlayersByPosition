package com.baseball.players_by_position;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.PersistenceContext;

@SpringBootApplication
@ComponentScan({"com.baseball.players_by_position.model",
               "com.baseball.players_by_position.service",
               "com.baseball.players_by_position.repository",
               "com.baseball.players_by_position.controller",
               "com.baseball.players_by_position.web",
               "com.baseball.players_by_position.view",
               "com.baseball.players_by_position.excel.formatter",
        "com.baseball.players_by_position.excel.mapper",
        "com.baseball.players_by_position.properties",
        "com.baseball.players_by_position.external.provider"})
@EnableJpaRepositories("com.baseball.players_by_position.repository")
@PersistenceContext
public class PlayersByPositionApplication {

	public static void main(String[] args) {

        SpringApplication.run(PlayersByPositionApplication.class);

	}

}
