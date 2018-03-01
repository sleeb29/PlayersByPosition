package com.baseball.players_by_position.controller;

import com.baseball.players_by_position.external.provider.HttpService;
import com.baseball.players_by_position.external.provider.HttpServiceParams;
import com.baseball.players_by_position.model.model.LeagueDepthChart;
import com.baseball.players_by_position.model.model.Player;
import com.baseball.players_by_position.service.PlayerService;
import com.baseball.players_by_position.view.ExcelView;
import com.baseball.players_by_position.view.mapper.IExcelRowMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    HttpServiceParams httpServiceParams;

    @Autowired
    IExcelRowMapper excelRowMapper;

    @Autowired
    HttpService httpService;

    @RequestMapping(value = "/getPlayersByPositionsWorkbook", method = RequestMethod.GET)
    @Cacheable("positionToStartingPlayersWorkbook")
    public ModelAndView getPlayersByPositionsWorkbook(Model model) {

        ResponseEntity httpResponse = httpService.getHTTPResponse(httpServiceParams);
        String responseBody = httpResponse.getBody().toString();

        try {

            LeagueDepthChart leagueDepthChart = new ObjectMapper().readValue(responseBody, LeagueDepthChart.class);

            playerService.populate(leagueDepthChart.getPlayers());
            Map<String, Set<Player>> positionToStartingPlayersMap = playerService.getPositionToStartingPlayersMap();

            return new ModelAndView(new ExcelView(excelRowMapper), "positionToStartingPlayersMap", positionToStartingPlayersMap);

        } catch(IOException exception){
            return null;
        }

    }

}