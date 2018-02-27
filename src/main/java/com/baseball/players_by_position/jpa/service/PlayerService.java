package com.baseball.players_by_position.jpa.service;
import com.baseball.players_by_position.model.Player;

import java.util.*;

public interface PlayerService {

    void populate(List<Player> players);

    HashMap<String, Set<Player>> getPositionToStartingPlayersMap();
}