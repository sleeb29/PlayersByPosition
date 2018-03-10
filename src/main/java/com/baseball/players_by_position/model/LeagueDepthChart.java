package com.baseball.players_by_position.model;

import com.baseball.players_by_position.external.provider.deserialize.DepthChartDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(using = DepthChartDeserializer.class)
public class LeagueDepthChart {

    private List<PlayerStage> players;

    public List<PlayerStage> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerStage> players) {
        this.players = players;
    }

}
