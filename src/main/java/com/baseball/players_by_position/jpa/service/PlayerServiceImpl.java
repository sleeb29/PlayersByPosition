package com.baseball.players_by_position.jpa.service;

import com.baseball.players_by_position.jpa.repository.PlayerRepository;
import com.baseball.players_by_position.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlayerServiceImpl implements PlayerService {

    public PlayerServiceImpl(){

    }

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public void populate(List<Player> players) {

        playerRepository.save(players);

    }

    @Transactional(readOnly=true)
    public HashMap<String, Set<Player>> getPositionToStartingPlayersMap(int startingDepthNum){

        Set<Player> players = playerRepository.getAllByDepth(startingDepthNum);
        HashMap<String, Set<Player>> positionToStartingPlayersMap = new HashMap<>();

        for(Player player : players){

            String position = player.getPosition();

            if(!positionToStartingPlayersMap.containsKey(position)){
                positionToStartingPlayersMap.put(position, new HashSet<>());
            }

            Set playerSetForPosition = positionToStartingPlayersMap.get(position);

            playerSetForPosition.add(player);

        }

        return positionToStartingPlayersMap;

    }

}