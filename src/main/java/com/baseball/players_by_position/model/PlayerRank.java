package com.baseball.players_by_position.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "PLAYER_RANK")
@Entity
public class PlayerRank extends AbstractPlayer implements java.io.Serializable {

    @Id
    @Column(name = "playerId", nullable = false)
    String playerId;

    @Column(name = "playerName", nullable = false)
    String playerName;

    @Column(name = "team", nullable = false)
    String team;

    @Column(name = "jersey")
    int jersey;

    @Column(name = "rank", nullable = false)
    int rank;

    Boolean processed;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getKey() {

        return this.jersey + "|" + this.team;

    }

    @Override
    public String getFirstName() {
        return this.playerName.split(" ")[0];
    }

    @Override
    public String getLastName() {
        return this.playerName.split(" ")[1];
    }

    public Boolean isProcessed() {
        return processed != null && processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getJersey() {
        return jersey;
    }

    public void setJersey(int jersey) {
        this.jersey = jersey;
    }

}
