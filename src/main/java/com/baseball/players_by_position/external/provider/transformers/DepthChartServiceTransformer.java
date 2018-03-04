package com.baseball.players_by_position.external.provider.transformers;

import com.baseball.players_by_position.model.LeagueDepthChart;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Future;

@Service
public class DepthChartServiceTransformer {

    @Async
    @Cacheable("transformPayloadToLeagueDepthChart")
    public Future<LeagueDepthChart> transformPayloadToLeagueDepthChart(String depthChartBody) throws IOException {

        LeagueDepthChart leagueDepthChart = new ObjectMapper().readValue(depthChartBody, LeagueDepthChart.class);

        return new AsyncResult<>(leagueDepthChart);
    }

}
