package com.baseball.players_by_position.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "PLAYER_STAGE")
@Entity
public class PlayerStage extends AbstractPlayer implements java.io.Serializable {

    @Id
    @Column(name = "id", nullable = false)
    String id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "team", nullable = false)
    String team;

    @Column(name = "position", nullable = false)
    String position;

    @Column(name = "depth", nullable = false)
    int depth;

    @Column(name = "jersey", nullable = true)
    int jersey;

    @Column(name = "status", nullable = true)
    String status;

    @Column(name = "rank", nullable = true)
    int rank;

    @Value("${java.lang.Boolean:true}")
    Boolean processed;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isProcessed() {
        return processed != null && processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public String getKey() {

        return this.jersey + "|" + this.team;

    }

    public String getSecondaryKey() {

        return this.firstName + " " + this.lastName + "|" + this.team;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
