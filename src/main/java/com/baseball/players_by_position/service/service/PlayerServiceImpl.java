package com.baseball.players_by_position.service.service;

import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.model.PlayerRank;
import com.baseball.players_by_position.service.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PlayerServiceImpl implements PlayerService {

    final static int STARTING_DEPTH_POSITION_NUM = 1;
    final static String OUTFIELD = "OF";

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public void populate(List<Player> players, List<PlayerRank> playerRanks) {

        HashMap<String, Integer> playerIdentifierToRankMap = new HashMap<>();

        for (PlayerRank playerRank : playerRanks) {
            String playerIdentifierHash = playerRank.getPlayerName() + "|" +
                    playerRank.getTeam();
            playerIdentifierToRankMap.put(playerIdentifierHash, playerRank.getRank());
        }

        for (Player player : players) {

            String playerIdentifierHash = player.getFirstName() + " " + player.getLastName() + "|" +
                    player.getTeam();

            if (playerIdentifierToRankMap.containsKey(playerIdentifierHash)) {
                player.setRank(playerIdentifierToRankMap.get(playerIdentifierHash));
            }

        }

        playerRepository.save(players);

    }

    @Transactional(readOnly = true)
    public HashMap<String, SortedSet<Player>> getPositionToStartingPlayersMap() {

        HashMap<String, String> positionsToAggregateMap = new HashMap<>();
        positionsToAggregateMap.put(POSITIONS_TO_AGGREGATE.CF.name(), OUTFIELD);
        positionsToAggregateMap.put(POSITIONS_TO_AGGREGATE.LF.name(), OUTFIELD);
        positionsToAggregateMap.put(POSITIONS_TO_AGGREGATE.RF.name(), OUTFIELD);

        Set<Player> players = playerRepository.getAllByDepth(STARTING_DEPTH_POSITION_NUM);
        HashMap<String, SortedSet<Player>> positionToStartingPlayersMap = new HashMap<>();

        for (Player player : players) {

            String position = player.getPosition();

            if (positionsToAggregateMap.containsKey(position)) {
                position = positionsToAggregateMap.get(position);
            }

            if (!positionToStartingPlayersMap.containsKey(position)) {
                positionToStartingPlayersMap.put(position, new TreeSet<>(new CustomPlayerRankComparator()));
            }

            Set playerSetForPosition = positionToStartingPlayersMap.get(position);

            playerSetForPosition.add(player);

        }

        return positionToStartingPlayersMap;

    }

    enum POSITIONS_TO_AGGREGATE {
        LF,
        RF,
        CF
    }

    class CustomPlayerRankComparator implements Comparator<Player> {
        public int compare(Player playerOne, Player playerTwo) {

            int playerOneRank = playerOne.getRank();
            int playerTwoRank = playerTwo.getRank();

            Boolean playerOneRankGreater = playerOneRank > playerTwoRank && playerTwoRank != 0;

            if (playerOneRank == playerTwoRank) {
                return 0;
            } else if (playerOneRankGreater || playerOneRank == 0) {
                return 1;
            } else {
                return -1;
            }
        }
    }

}