package com.baseball.players_by_position.service;

import com.baseball.players_by_position.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface PlayerService {

    void populate(List<Player> players);

    HashMap<String, Set<Player>> getPositionToStartingPlayersMap();
}