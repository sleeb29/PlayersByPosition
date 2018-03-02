package com.baseball.players_by_position.controller;

import com.baseball.players_by_position.external.provider.HttpService;
import com.baseball.players_by_position.external.provider.HttpServicesParams;
import com.baseball.players_by_position.model.LeagueDepthChart;
import com.baseball.players_by_position.model.Player;
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
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.Set;

@RestController
public class GetPlayersByPositionWorkbook {

    @Autowired
    PlayerService playerService;

    @Autowired
    HttpServicesParams httpServicesParams;

    @Autowired
    IExcelRowMapper excelRowMapper;

    @Autowired
    HttpService httpService;

    @RequestMapping(value = "/getPlayersByPositionsWorkbook", method = RequestMethod.GET)
    @Cacheable("positionToStartingPlayersWorkbook")
    public ModelAndView getPlayersByPositionsWorkbook(Model model) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        ResponseEntity depthChartResponse = httpService.getHTTPResponse(httpServicesParams.getDepthChartServiceParams());
        String depthChartBody = depthChartResponse.getBody().toString();

        ResponseEntity playerRankingResponse = httpService.getHTTPResponse(httpServicesParams.getPlayerRankingServiceParams());
        String playerRankingBody = playerRankingResponse.getBody().toString();

        try {

            LeagueDepthChart leagueDepthChart = new ObjectMapper().readValue(depthChartBody, LeagueDepthChart.class);

            playerService.populate(leagueDepthChart.getPlayers());
            Map<String, Set<Player>> positionToStartingPlayersMap = playerService.getPositionToStartingPlayersMap();

            return new ModelAndView(new ExcelView(excelRowMapper), "positionToStartingPlayersMap", positionToStartingPlayersMap);

        } catch(IOException exception){
            return null;
        }

    }

}