package com.baseball.players_by_position.model;

import java.util.List;

public class PlayerServiceCustomMapping {

    int starterDepthLevel;
    List<AggregatePosition> aggregatePositions;
    List<CustomStarterDepthLevelPosition> customStarterDepthLevelPositions;

    public int getStarterDepthLevel() {
        return starterDepthLevel;
    }

    public void setStarterDepthLevel(int starterDepthLevel) {
        this.starterDepthLevel = starterDepthLevel;
    }

    public List<AggregatePosition> getAggregatePositions() {
        return aggregatePositions;
    }

    public void setAggregatePositions(List<AggregatePosition> aggregatePositions) {
        this.aggregatePositions = aggregatePositions;
    }

    public List<CustomStarterDepthLevelPosition> getCustomStarterDepthLevelPositions() {
        return customStarterDepthLevelPositions;
    }

    public void setCustomStarterDepthLevelPositions(List<CustomStarterDepthLevelPosition> customStarterDepthLevelPositions) {
        this.customStarterDepthLevelPositions = customStarterDepthLevelPositions;
    }
}
