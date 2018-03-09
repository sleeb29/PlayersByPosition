package com.baseball.players_by_position.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Table(name = "PLAYER_RANK_STAGE_PLAYER")
@Entity
@XmlRootElement(name = "Player")
@XmlAccessorType(XmlAccessType.FIELD)
public class RankPlayerStage {

    @Id
    @XmlElement(name = "playerId")
    String playerId;

    @XmlElement(name = "jersey")
    int jersey;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getJersey() {
        return jersey;
    }

    public void setJersey(int jersey) {
        this.jersey = jersey;
    }

}
