package com.baseball.players_by_position.service.service;

import com.baseball.players_by_position.model.*;

import java.util.HashMap;
import java.util.List;

public interface PlayerService {

    void populateTeamCrossWalkTable(List<TeamCrossWalk> teamCrossWalkList);

    void populatePlayerStagingTable(List<PlayerStage> playerStageList);

    void populatePlayerRankStagingTable(List<PlayerRankStage> playerRankStageList);

    void populateRankPlayerStagingTable(List<RankPlayerStage> playerRankPlayerStageList);

    void aggregatePlayerRankTables();

    void aggregateStagingTablesAndLoadActual();

    HashMap<String, List<Player>> getPositionToStartingPlayersMap();

}