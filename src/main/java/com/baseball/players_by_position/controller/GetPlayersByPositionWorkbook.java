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
import java.util.logging.Logger;

@RestController
public class GetPlayersByPositionWorkbook {

    private static final Logger logger = Logger.getLogger(GetPlayersByPositionWorkbook.class.getName());

    @Autowired
    PlayerService playerService;
    @Autowired
    HttpServicesParams httpServicesParams;
    @Autowired
    IExcelRowMapper excelRowMapper;
    @Autowired
    HttpService httpService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView getIndex() {

        logger.info("Serving index page.");

        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        return model;

    }

    @RequestMapping(value = "/getStartersByPositionWorkbook", method = RequestMethod.GET)
    @Cacheable("positionToStartingPlayersWorkbook")
    public ModelAndView getStartersByPositionWorkbook(Model model) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, JAXBException, XMLStreamException, ExecutionException, InterruptedException {

        logger.info("in getStartersByPositionWorkbook.");

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

                logger.info("Finished fetching data from DepthChartService: " + httpServicesParams.getDepthChartServiceParams().getExternalApiHost());

                depthChartResponseComplete = true;

                ResponseEntity depthChartResponse = depthChartResponseFuture.get();
                String depthChartBody = depthChartResponse.getBody().toString();

                DepthChartServiceTransformer depthChartServiceTransformer = new DepthChartServiceTransformer();
                leagueDepthChartFuture = depthChartServiceTransformer.transformPayloadToLeagueDepthChart(depthChartBody);

            }

            if (!playerRankingResponseComplete && playerRankingResponseFuture.isDone()) {

                logger.info("Finished fetching data from PlayerRankingService: " + httpServicesParams.getPlayerRankingServiceParams().getExternalApiHost());

                playerRankingResponseComplete = true;

                ResponseEntity playerRankListResponse = playerRankingResponseFuture.get();
                String playerRankListBody = playerRankListResponse.getBody().toString();

                PlayerRankingServiceTransformer playerRankingServiceTransformer = new PlayerRankingServiceTransformer();
                playerRankListFuture = playerRankingServiceTransformer.transformPayloadToPlayerRankingList(playerRankListBody);

            }

            if (!depthChartComplete && leagueDepthChartFuture != null && leagueDepthChartFuture.isDone()) {
                logger.info("Finished parsing payload for DepthChartService");
                depthChartComplete = true;
                leagueDepthChart = leagueDepthChartFuture.get();
            }

            if (!playerRankingComplete && playerRankListFuture != null && playerRankListFuture.isDone()) {
                logger.info("Finished parsing payload for PlayerRankList");
                playerRankingComplete = true;
                playerRankList = playerRankListFuture.get();
            }

            done = leagueDepthChart != null && playerRankList != null;

            if (done) {

                playerService.populate(leagueDepthChart.getPlayers(), playerRankList.getPlayerRankings());
                positionToStartingPlayersMap = playerService.getPositionToStartingPlayersMap();

            }

        }

        logger.info("Finished building positionToStartingPlayersMap. Serving worksheet.");

        return new ModelAndView(new ExcelView(excelRowMapper), "positionToStartingPlayersMap", positionToStartingPlayersMap);

    }

}