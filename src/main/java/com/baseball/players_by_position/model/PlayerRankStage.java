package com.baseball.players_by_position.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Table(name = "PLAYER_RANK_STAGE")
@Entity
@XmlRootElement(name = "Player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerRankStage implements java.io.Serializable {

    @Id
    @XmlElement(name = "playerId")
    @Column(name = "playerId", nullable = false)
    String playerId;

    @XmlElement(name = "playerName")
    @Column(name = "playerName", nullable = false)
    String playerName;

    @XmlElement(name = "team")
    @Column(name = "team", nullable = false)
    String team;

    @XmlElement(name = "position")
    @Column(name = "position", nullable = true)
    String position;

    @XmlElement(name = "rank")
    @Column(name = "rank", nullable = false)
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

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
