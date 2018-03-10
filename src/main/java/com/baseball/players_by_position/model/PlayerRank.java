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

    @Column(name = "position", nullable = true)
    String position;

    @Column(name = "lookup_first_name", nullable = false)
    String lookupFirstName;

    @Column(name = "lookup_last_name", nullable = false)
    String lookupLastName;

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

    @Override
    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public int getJersey() {
        return jersey;
    }

    public void setJersey(int jersey) {
        this.jersey = jersey;
    }

    public Boolean isProcessed() {
        return processed != null && processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    @Override
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String getLookupFirstName() {
        return lookupFirstName;
    }

    public void setLookupFirstName(String lookupFirstName) {
        this.lookupFirstName = lookupFirstName;
    }

    @Override
    public String getLookupLastName() {
        return lookupLastName;
    }

    public void setLookupLastName(String lookupLastName) {
        this.lookupLastName = lookupLastName;
    }
}
