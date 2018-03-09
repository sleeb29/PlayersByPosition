package com.baseball.players_by_position.model;

import javax.persistence.*;

@Table(name = "TEAM_CROSSWALK")
@Entity
public class TeamCrossWalk {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Column(name = "common_name", nullable = false)
    String commonName;

    @Column(name = "league_depth_chart_name", nullable = false)
    String depthChartName;

    @Column(name = "player_ranking_name", nullable = false)
    String playerRankingName;

    public TeamCrossWalk(String commonName, String depthChartName, String playerRankingName) {
        this.commonName = commonName;
        this.depthChartName = depthChartName;
        this.playerRankingName = playerRankingName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getDepthChartName() {
        return depthChartName;
    }

    public void setDepthChartName(String depthChartName) {
        this.depthChartName = depthChartName;
    }

    public String getPlayerRankingName() {
        return playerRankingName;
    }

    public void setPlayerRankingName(String playerRankingName) {
        this.playerRankingName = playerRankingName;
    }
}
