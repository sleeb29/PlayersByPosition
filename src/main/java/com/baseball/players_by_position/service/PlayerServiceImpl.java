package com.baseball.players_by_position.service;

import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.service.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlayerServiceImpl implements PlayerService {

    final static int STARTING_DEPTH_POSITION_NUM = 1;
    final static String OUTFIELD = "OF";

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public void populate(List<Player> players) {

        playerRepository.save(players);

    }

    @Transactional(readOnly=true)
    public HashMap<String, Set<Player>> getPositionToStartingPlayersMap(){

        HashMap<String, String> positionsToAggregateMap = new HashMap<>();
        positionsToAggregateMap.put(POSITIONS_TO_AGGREGATE.CF.name(), OUTFIELD);
        positionsToAggregateMap.put(POSITIONS_TO_AGGREGATE.LF.name(), OUTFIELD);
        positionsToAggregateMap.put(POSITIONS_TO_AGGREGATE.RF.name(), OUTFIELD);

        Set<Player> players = playerRepository.getAllByDepth(STARTING_DEPTH_POSITION_NUM);
        HashMap<String, Set<Player>> positionToStartingPlayersMap = new HashMap<>();

        for(Player player : players){

            String position = player.getPosition();

            if (positionsToAggregateMap.containsKey(position)) {
                position = positionsToAggregateMap.get(position);
            }

            if(!positionToStartingPlayersMap.containsKey(position)){
                positionToStartingPlayersMap.put(position, new HashSet<>());
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

}