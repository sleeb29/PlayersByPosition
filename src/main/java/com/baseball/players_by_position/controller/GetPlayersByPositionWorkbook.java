package com.baseball.players_by_position.controller;

import com.baseball.players_by_position.external.provider.HttpService;
import com.baseball.players_by_position.external.provider.HttpServicesParams;
import com.baseball.players_by_position.model.LeagueDepthChart;
import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.model.PlayerRankList;
import com.baseball.players_by_position.service.service.PlayerService;
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.SortedSet;

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
    public ModelAndView getStartersByPositionWorkbook(Model model) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, JAXBException, XMLStreamException {

        ResponseEntity depthChartResponse = httpService.getHTTPResponse(httpServicesParams.getDepthChartServiceParams());
        String depthChartBody = depthChartResponse.getBody().toString();

        LeagueDepthChart leagueDepthChart = new ObjectMapper().readValue(depthChartBody, LeagueDepthChart.class);

        ResponseEntity playerRankingResponse = httpService.getHTTPResponse(httpServicesParams.getPlayerRankingServiceParams());
        String playerRankingBody = playerRankingResponse.getBody().toString();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(playerRankingBody));

        JAXBContext jaxbContext = JAXBContext.newInstance(PlayerRankList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PlayerRankList playerRankList = (PlayerRankList) jaxbUnmarshaller.unmarshal(xmlStreamReader);

        playerService.populate(leagueDepthChart.getPlayers(), playerRankList.getPlayerRankings());

        Map<String, SortedSet<Player>> positionToStartingPlayersMap = playerService.getPositionToStartingPlayersMap();

        return new ModelAndView(new ExcelView(excelRowMapper), "positionToStartingPlayersMap", positionToStartingPlayersMap);

    }

}