package com.baseball.players_by_position;

import com.baseball.players_by_position.external.provider.HttpService;
import com.baseball.players_by_position.jpa.service.PlayerServiceImpl;
import com.baseball.players_by_position.model.LeagueDepthChart;
import com.baseball.players_by_position.external.provider.SportRadarAPI;
import com.baseball.players_by_position.jpa.service.PlayerService;
import com.baseball.players_by_position.model.Player;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceContext;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
@ComponentScan({"com.baseball.players_by_position.model",
               "com.baseball.players_by_position.jpa.service",
               "com.baseball.players_by_position.jpa.repository"})
@EntityScan("com.baseball.players_by_position.model")
@EnableJpaRepositories("com.baseball.players_by_position.jpa.repository")
@PersistenceContext
public class PlayersByPositionApplication {

    @Autowired
    private ApplicationContext appContext;

	public static void main(String[] args) {

        SpringApplication.run(PlayersByPositionApplication.class);

        //playerService.populate(leagueDepthChart.getPlayers());

        //Map<String, Set<Player>> positionToPlayerMap = playerService.getPositionToStartingPlayersMap(1);

		/*for (Map.Entry<String, List<Player>> entry : leagueDepthChart.getPositionToPlayersMap().entrySet()){

			String position = entry.getKey();
			List players = entry.getValue();

			CsvMapper mapper = new CsvMapper();
			CsvSchema schema = mapper.schemaFor(Player.class).withHeader();
			schema.withColumnSeparator(',');

			ObjectWriter objectWriter = mapper.writer(schema);
			File tempFile = new File("outgoing/" + position + ".csv");
			FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
			OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
			objectWriter.writeValue(writerOutputStream, players);

		}*/
	}

    @Bean
    public CommandLineRunner demo(PlayerService playerService) throws IOException {

        return (args) -> {
            SportRadarAPI sportRadarAPI = new SportRadarAPI("http", "api.sportradar.us", "/mlb-t6/league/depth_charts.json",
                    "json", HttpMethod.GET);

            HttpService httpService = new HttpService();
            httpService.setApi(sportRadarAPI);

            ResponseEntity httpResponse = httpService.getHTTPResponse(sportRadarAPI);
            String responseBody = httpResponse.getBody().toString();
            LeagueDepthChart leagueDepthChart = new ObjectMapper().readValue(responseBody, LeagueDepthChart.class);


            //PlayerService playerService = context.getBean(PlayerService.class);
            //playerService.populate(leagueDepthChart.getPlayers());

            //PlayerService playerService = appContext.getBean(PlayerServiceImpl.class);
            playerService.populate(leagueDepthChart.getPlayers());
            HashMap<String, Set<Player>> positionToStartingPlayersMap = playerService.getPositionToStartingPlayersMap();

            Boolean b = false;

        };
    }

}
