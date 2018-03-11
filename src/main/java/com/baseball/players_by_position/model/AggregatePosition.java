package com.baseball.players_by_position.model;

import java.util.List;

public class AggregatePosition {

    String position;
    List<String> positionsToAggregate;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<String> getPositionsToAggregate() {
        return positionsToAggregate;
    }

    public void setPositionsToAggregate(List<String> positionsToAggregate) {
        this.positionsToAggregate = positionsToAggregate;
    }
}
