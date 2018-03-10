package com.baseball.players_by_position.external.provider.service;

import com.baseball.players_by_position.external.provider.flatfile.TeamCrossWalkParser;
import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.model.TeamCrossWalk;
import com.baseball.players_by_position.service.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

@Service
public class GetPlayerService {

    @Autowired
    TeamCrossWalkParser teamCrossWalkParser;

    @Autowired
    LeagueDepthChartService leagueDepthChartService;

    @Autowired
    PlayerRankingService playerRankingService;

    @Autowired
    PlayerService playerService;

    @Cacheable("positionToStartingPlayersWorkbook")
    public Map<String, List<Player>> getPositionToStartingPlayersMap() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, JAXBException, XMLStreamException {

        List<TeamCrossWalk> teamCrossWalkList = teamCrossWalkParser.getTeamCrossWalkList();
        playerService.populateTeamCrossWalkTable(teamCrossWalkList);

        leagueDepthChartService.put();
        playerRankingService.put();

        playerService.aggregateStagingTablesAndLoadActual();

        return playerService.getPositionToStartingPlayersMap();

    }

}
