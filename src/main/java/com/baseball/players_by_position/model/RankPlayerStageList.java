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
public class RankPlayerStageList {

    @XmlElement(name = "Player")
    List<RankPlayerStage> rankPlayerList;

    public List<RankPlayerStage> getPlayerRankings() {
        return rankPlayerList;
    }

    public void setPlayerRankings(List<RankPlayerStage> rankPlayerList) {
        this.rankPlayerList = rankPlayerList;
    }

}
