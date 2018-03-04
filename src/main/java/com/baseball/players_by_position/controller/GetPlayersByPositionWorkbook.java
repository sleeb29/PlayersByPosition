package com.baseball.players_by_position.controller;

import com.baseball.players_by_position.external.provider.HttpService;
import com.baseball.players_by_position.external.provider.HttpServicesParams;
import com.baseball.players_by_position.external.provider.transformers.DepthChartServiceTransformer;
import com.baseball.players_by_position.external.provider.transformers.PlayerRankingServiceTransformer;
import com.baseball.players_by_position.model.LeagueDepthChart;
import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.model.PlayerRankList;
import com.baseball.players_by_position.service.service.PlayerService;
import com.baseball.players_by_position.view.ExcelView;
import com.baseball.players_by_position.view.mapper.IExcelRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    @RequestMapping(value = "/getStartersByPositionWorkbook", method = RequestMethod.GET)
    @Cacheable("positionToStartingPlayersWorkbook")
    public ModelAndView getStartersByPositionWorkbook(Model model) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, JAXBException, XMLStreamException, ExecutionException, InterruptedException {

        Future<ResponseEntity> depthChartResponseFuture = httpService.getHTTPResponse(httpServicesParams.getDepthChartServiceParams());
        Future<ResponseEntity> playerRankingResponseFuture = httpService.getHTTPResponse(httpServicesParams.getPlayerRankingServiceParams());

        Boolean depthChartResponseComplete = false;
        Boolean playerRankingResponseComplete = false;

        Boolean depthChartComplete = false;
        Boolean playerRankingComplete = false;

        Boolean done = false;

        Future<LeagueDepthChart> leagueDepthChartFuture = null;
        Future<PlayerRankList> playerRankListFuture = null;

        LeagueDepthChart leagueDepthChart = null;
        PlayerRankList playerRankList = null;

        Map<String, SortedSet<Player>> positionToStartingPlayersMap = null;

        while (!done) {

            if (!depthChartResponseComplete && depthChartResponseFuture.isDone()) {

                depthChartResponseComplete = true;

                ResponseEntity depthChartResponse = depthChartResponseFuture.get();
                String depthChartBody = depthChartResponse.getBody().toString();

                DepthChartServiceTransformer depthChartServiceTransformer = new DepthChartServiceTransformer();
                leagueDepthChartFuture = depthChartServiceTransformer.transformPayloadToLeagueDepthChart(depthChartBody);

            }

            if (!playerRankingResponseComplete && playerRankingResponseFuture.isDone()) {

                playerRankingResponseComplete = false;

                ResponseEntity playerRankListResponse = playerRankingResponseFuture.get();
                String playerRankListBody = playerRankListResponse.getBody().toString();

                PlayerRankingServiceTransformer playerRankingServiceTransformer = new PlayerRankingServiceTransformer();
                playerRankListFuture = playerRankingServiceTransformer.transformPayloadToPlayerRankingList(playerRankListBody);

            }

            if (!depthChartComplete && leagueDepthChartFuture != null && leagueDepthChartFuture.isDone()) {
                depthChartComplete = true;
                leagueDepthChart = leagueDepthChartFuture.get();
            }

            if (!playerRankingComplete && playerRankListFuture != null && playerRankListFuture.isDone()) {
                playerRankingComplete = true;
                playerRankList = playerRankListFuture.get();
            }

            done = leagueDepthChart != null && playerRankList != null;

            if (done) {

                playerService.populate(leagueDepthChart.getPlayers(), playerRankList.getPlayerRankings());
                positionToStartingPlayersMap = playerService.getPositionToStartingPlayersMap();

            }

        }

        return new ModelAndView(new ExcelView(excelRowMapper), "positionToStartingPlayersMap", positionToStartingPlayersMap);

    }

}