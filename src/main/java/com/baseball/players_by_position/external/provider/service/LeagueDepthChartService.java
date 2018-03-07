package com.baseball.players_by_position.external.provider.service;

import com.baseball.players_by_position.external.provider.LeagueDepthChartServiceParams;
import com.baseball.players_by_position.model.LeagueDepthChart;
import com.baseball.players_by_position.service.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Service
public class LeagueDepthChartService extends HttpService {

    @Autowired
    LeagueDepthChartServiceParams leagueDepthChartServiceParams;

    @Autowired
    PlayerService playerService;

    public void put() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        ResponseEntity leagueDepthChartResponse = this.getHTTPResponse(leagueDepthChartServiceParams);
        LeagueDepthChart leagueDepthChart = transformPayloadToLeagueDepthChart(leagueDepthChartResponse.getBody().toString());

        playerService.populatePlayerStagingTable(leagueDepthChart.getPlayers());

    }


    @Cacheable("transformPayloadToLeagueDepthChart")
    public LeagueDepthChart transformPayloadToLeagueDepthChart(String depthChartBody) throws IOException {
        LeagueDepthChart leagueDepthChart = new ObjectMapper().readValue(depthChartBody, LeagueDepthChart.class);
        return leagueDepthChart;
    }

}
