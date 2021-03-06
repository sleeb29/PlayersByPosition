package com.baseball.players_by_position.model;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "FantasyBaseballNerd")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
public class PlayerRankList {

    @XmlElement(name = "Player")
    List<PlayerRankStage> playerRankings;

    public List<PlayerRankStage> getPlayerRankings() {
        return playerRankings;
    }

    public void setPlayerRankings(List<PlayerRankStage> playerRankings) {
        this.playerRankings = playerRankings;
    }

}
