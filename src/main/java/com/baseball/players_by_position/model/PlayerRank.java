package com.baseball.players_by_position.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerRank implements java.io.Serializable {

    @XmlElement(name = "playerName")
    String playerName;

    @XmlElement(name = "team")
    String team;

    @XmlElement(name = "rank")
    int rank;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setRanking(int rank) {
        this.rank = rank;
    }

}
