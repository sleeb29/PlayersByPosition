package com.baseball.players_by_position.model;

import com.baseball.players_by_position.deserialize.DepthChartDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = DepthChartDeserializer.class)

public class LeagueDepthChart {

    private List<Player> players;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
