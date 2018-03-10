package com.baseball.players_by_position;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan({"com.baseball.players_by_position.model",
		"com.baseball.players_by_position.service.service",
		"com.baseball.players_by_position.service.repository",
		"com.baseball.players_by_position.controller",
		"com.baseball.players_by_position.view",
		"com.baseball.players_by_position.view.mapper",
		"com.baseball.players_by_position.configuration.properties",
		"com.baseball.players_by_position.external.provider",
		"com.baseball.players_by_position.external.provider.service",
		"com.baseball.players_by_position.configuration.web",
		"com.baseball.players_by_position.external.provider.flatfile",
		"com.baseball.players_by_position.external.provider.deserialize"})
public class PlayersByPositionApplicationTests {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(PlayerTests.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println(result.wasSuccessful());
	}

}
