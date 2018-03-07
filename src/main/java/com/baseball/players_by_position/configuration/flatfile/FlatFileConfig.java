package com.baseball.players_by_position.configuration.flatfile;

import com.baseball.players_by_position.model.TeamCrossWalk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FlatFileConfig {

    @Bean
    ItemReader<TeamCrossWalk> csvFileItemReader() {
        FlatFileItemReader<TeamCrossWalk> csvFileReader = new FlatFileItemReader<>();
        csvFileReader.setResource(new ClassPathResource("team_cross_walk.csv"));

        LineMapper<TeamCrossWalk> teamCrossWalkLineMapper = createTeamCrossWalkLineMapper();
        csvFileReader.setLineMapper(teamCrossWalkLineMapper);

        return csvFileReader;
    }

    private LineMapper<TeamCrossWalk> createTeamCrossWalkLineMapper() {
        DefaultLineMapper<TeamCrossWalk> teamCrossWalkLineMapper = new DefaultLineMapper<>();

        LineTokenizer teamCrossWalkLineTokenizer = createTeamCrossWalkLineTokenizer();
        teamCrossWalkLineMapper.setLineTokenizer(teamCrossWalkLineTokenizer);

        FieldSetMapper<TeamCrossWalk> teamCrossWalkMapper = createTeamCrossWalkMapper();
        teamCrossWalkLineMapper.setFieldSetMapper(teamCrossWalkMapper);

        return teamCrossWalkLineMapper;
    }

    private LineTokenizer createTeamCrossWalkLineTokenizer() {
        DelimitedLineTokenizer teamCrossWalkLineTokenizer = new DelimitedLineTokenizer();
        teamCrossWalkLineTokenizer.setDelimiter(",");
        teamCrossWalkLineTokenizer.setNames(new String[]{"commonName", "depthChartServiceName", "playerRankingName"});
        return teamCrossWalkLineTokenizer;
    }

    private FieldSetMapper<TeamCrossWalk> createTeamCrossWalkMapper() {
        BeanWrapperFieldSetMapper<TeamCrossWalk> teamCrossWalkMapper = new BeanWrapperFieldSetMapper<>();
        teamCrossWalkMapper.setTargetType(TeamCrossWalk.class);
        return teamCrossWalkMapper;
    }
}
