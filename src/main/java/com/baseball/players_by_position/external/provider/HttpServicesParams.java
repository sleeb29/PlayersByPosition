package com.baseball.players_by_position.external.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HttpServicesParams {

    @Autowired
    DepthChartServiceParams depthChartServiceParams;
    @Autowired
    PlayerRankingServiceParams playerRankingServiceParams;

    public HttpServicesParams() {

    }

    public DepthChartServiceParams getDepthChartServiceParams() {
        return depthChartServiceParams;
    }

    public PlayerRankingServiceParams getPlayerRankingServiceParams() {
        return playerRankingServiceParams;
    }
}