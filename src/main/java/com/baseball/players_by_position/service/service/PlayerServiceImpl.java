package com.baseball.players_by_position.service.service;

import com.baseball.players_by_position.model.*;
import com.baseball.players_by_position.service.repository.*;
import com.baseball.players_by_position.service.search.PlayerTrie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger logger = Logger.getLogger(PlayerServiceImpl.class.getName());

    final static int STARTING_DEPTH_POSITION_NUM = 1;
    final static String OUTFIELD = "OF";

    @Autowired
    private TeamCrossWalkRepository teamCrossWalkRepository;

    @Autowired
    private PlayerRankStageRepository playerRankStageRepository;

    @Autowired
    private RankPlayerStageRepository rankPlayerStageRepository;

    @Autowired
    private PlayerStageRepository playerStageRepository;

    @Autowired
    private PlayerRankRepository playerRankRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public void populateTeamCrossWalkTable(List<TeamCrossWalk> teamCrossWalkList) {
        teamCrossWalkRepository.save(teamCrossWalkList);
    }

    @Transactional
    public void populatePlayerStagingTable(List<PlayerStage> playerStageList) {

        Iterable<TeamCrossWalk> teamCrossWalkIterable = teamCrossWalkRepository.findAll();
        Boolean runStreamInParallel = true;
        Stream teamCrossWalkStream = StreamSupport.stream(teamCrossWalkIterable.spliterator(), runStreamInParallel);

        Map<String, String> depthTeamNameToCommonNameMap = (Map<String, String>) teamCrossWalkStream.collect(
                Collectors.toMap(TeamCrossWalk::getDepthChartName, TeamCrossWalk::getCommonName));

        for (PlayerStage playerStage : playerStageList) {
            playerStage.setTeam(getCommonTeamName(playerStage.getTeam(), depthTeamNameToCommonNameMap));
        }

        playerStageRepository.save(playerStageList);
    }

    @Transactional
    public void populatePlayerRankStagingTable(List<PlayerRankStage> playerRankStageList) {
        playerRankStageRepository.save(playerRankStageList);
    }

    @Transactional
    public void populateRankPlayerStagingTable(List<RankPlayerStage> rankPlayerStageList) {
        rankPlayerStageRepository.save(rankPlayerStageList);
    }

    @Transactional
    public void aggregatePlayerRankTables() {

        Iterable<PlayerRankStage> playerRankStageIterable = playerRankStageRepository.findAll();
        Iterable<RankPlayerStage> rankPlayerStageIterable = rankPlayerStageRepository.findAll();

        Boolean runStreamInParallel = true;

        Stream playerRankStageStream = StreamSupport.stream(playerRankStageIterable.spliterator(), runStreamInParallel);
        Map<String, PlayerRankStage> playerRankStageMap = (Map<String, PlayerRankStage>) playerRankStageStream.collect(
                Collectors.toMap(PlayerRankStage::getPlayerId, playerRankStage -> playerRankStage));

        Stream rankPlayerStageStream = StreamSupport.stream(rankPlayerStageIterable.spliterator(), runStreamInParallel);
        Map<String, RankPlayerStage> rankPlayerStageMap = (Map<String, RankPlayerStage>) rankPlayerStageStream.collect(
                Collectors.toMap(RankPlayerStage::getPlayerId, rankPlayerStage -> rankPlayerStage));

        List<PlayerRank> playerRankList = getPlayerRankList(rankPlayerStageMap, playerRankStageMap);

        playerRankRepository.save(playerRankList);

    }

    private List<PlayerRank> getPlayerRankList(Map<String, RankPlayerStage> rankPlayerStageMap, Map<String, PlayerRankStage> playerRankStageMap) {

        Iterable<TeamCrossWalk> teamCrossWalkIterable = teamCrossWalkRepository.findAll();
        Boolean runStreamInParallel = true;
        Stream teamCrossWalkStream = StreamSupport.stream(teamCrossWalkIterable.spliterator(), runStreamInParallel);

        Map<String, String> rankTeamNameToCommonNameMap = (Map<String, String>) teamCrossWalkStream.collect(
                Collectors.toMap(TeamCrossWalk::getPlayerRankingName, TeamCrossWalk::getCommonName));

        List<PlayerRank> playerRankList = new ArrayList<>();

        for (Map.Entry<String, PlayerRankStage> entry : playerRankStageMap.entrySet()) {

            String playerRankStageKey = entry.getKey();
            PlayerRankStage playerRankStage = entry.getValue();

            PlayerRank playerRank = new PlayerRank();
            playerRank.setPlayerId(playerRankStage.getPlayerId());
            playerRank.setPlayerName(playerRankStage.getPlayerName());

            String team = getCommonTeamName(playerRankStage.getTeam(), rankTeamNameToCommonNameMap);

            playerRank.setTeam(team);
            playerRank.setRank(playerRankStage.getRank());
            playerRank.setPosition(playerRankStage.getPosition());

            if (rankPlayerStageMap.containsKey(playerRankStageKey)) {

                RankPlayerStage playerRankStagePlayer = rankPlayerStageMap.get(playerRankStageKey);
                playerRank.setJersey(playerRankStagePlayer.getJersey());

            } else {

                logger.info("Unable to match record: " + playerRankStageKey + ". Please check incoming data");

            }

            playerRankList.add(playerRank);

        }

        return playerRankList;

    }

    private String getCommonTeamName(String sourceTeam, Map<String, String> sourceToTargetMap) {

        if (sourceToTargetMap.containsKey(sourceTeam)) {
            return sourceToTargetMap.get(sourceTeam);
        }

        return sourceTeam;
    }

    @Transactional
    public void aggregateStagingTablesAndLoadActual() {

        Iterator<PlayerRank> playerRankIterable = playerRankRepository.findAll().iterator();
        Iterable<PlayerStage> playerStageIterable = playerStageRepository.findAll();

        PlayerTrie playerStageTrie = buildPlayerTrie(playerStageIterable);

        List<Player> players = new ArrayList<>();

        while (playerRankIterable.hasNext()) {

            PlayerRank playerRank = playerRankIterable.next();

            AbstractPlayer trieResult = playerStageTrie.get(playerRank);

            if (trieResult == null) {
                logger.info(playerRank.getPlayerName() + " UNABLE TO FIND MATCH FantasyBaseballNerds. playing for " +
                        playerRank.getTeam() + " " + playerRank.getJersey() + " " + playerRank.getTeam());

                continue;

            }

            PlayerStage playerStage = (PlayerStage) trieResult;
            Player player = new Player();

            player.setId(playerStage.getId());
            player.setFirstName(playerStage.getFirstName());
            player.setLastName(playerStage.getLastName());
            player.setTeam(playerStage.getTeam());
            player.setPosition(playerStage.getPosition());
            player.setDepth(playerStage.getDepth());
            player.setStatus(playerStage.getStatus());
            player.setRank(playerRank.getRank());

            players.add(player);
            playerStage.setProcessed(true);
            playerRank.setProcessed(true);

        }

        playerRepository.save(players);

    }

    public PlayerTrie buildPlayerTrie(Iterable<PlayerStage> playerIterable) {

        PlayerTrie playerTrie = new PlayerTrie();

        for (PlayerStage player : playerIterable) {
            playerTrie.put(player);
        }

        return playerTrie;

    }

    @Transactional(readOnly = true)
    public HashMap<String, List<Player>> getPositionToStartingPlayersMap() {

        HashMap<String, String> positionsToAggregateMap = new HashMap<>();
        positionsToAggregateMap.put(POSITIONS_TO_AGGREGATE.CF.name(), OUTFIELD);
        positionsToAggregateMap.put(POSITIONS_TO_AGGREGATE.LF.name(), OUTFIELD);
        positionsToAggregateMap.put(POSITIONS_TO_AGGREGATE.RF.name(), OUTFIELD);

        List<Player> players = playerRepository.getAllByDepth(STARTING_DEPTH_POSITION_NUM);
        HashMap<String, List<Player>> positionToStartingPlayersMap = new HashMap<>();

        for (Player player : players) {

            String position = player.getPosition();

            if (positionsToAggregateMap.containsKey(position)) {
                position = positionsToAggregateMap.get(position);
            }

            if (!positionToStartingPlayersMap.containsKey(position)) {
                positionToStartingPlayersMap.put(position, new ArrayList<>());
            }

            List playerSetForPosition = positionToStartingPlayersMap.get(position);

            playerSetForPosition.add(player);

        }

        return positionToStartingPlayersMap;

    }

    enum POSITIONS_TO_AGGREGATE {
        LF,
        RF,
        CF
    }

}