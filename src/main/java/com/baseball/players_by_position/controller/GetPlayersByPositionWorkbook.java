package com.baseball.players_by_position.controller;

import com.baseball.players_by_position.external.provider.HttpService;
import com.baseball.players_by_position.external.provider.SportRadarAPI;
import com.baseball.players_by_position.model.LeagueDepthChart;
import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.service.PlayerService;
import com.baseball.players_by_position.view.ExcelView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RestController
public class GetPlayersByPositionWorkbook {
    
    @Autowired
    PlayerService playerService;

    @RequestMapping(value = "/getPlayersByPositionsWorkbook", method = RequestMethod.GET)
    public ModelAndView getPlayersByPositionsWorkbook(Model model) {

        SportRadarAPI sportRadarAPI = new SportRadarAPI("http", "api.sportradar.us", "/mlb-t6/league/depth_charts.json",
                "json", HttpMethod.GET);

        HttpService httpService = new HttpService();
        httpService.setApi(sportRadarAPI);

        ResponseEntity httpResponse = httpService.getHTTPResponse(sportRadarAPI);
        String responseBody = httpResponse.getBody().toString();

        try {

            LeagueDepthChart leagueDepthChart = new ObjectMapper().readValue(responseBody, LeagueDepthChart.class);

            playerService.populate(leagueDepthChart.getPlayers());
            Map<String, Set<Player>> positionToStartingPlayersMap = playerService.getPositionToStartingPlayersMap();

            return new ModelAndView(new ExcelView(), "positionToStartingPlayersMap", positionToStartingPlayersMap);

        } catch(IOException exception){
            return null;
        }

    }

}