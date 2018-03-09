package com.baseball.players_by_position.external.provider.flatfile;

import com.baseball.players_by_position.model.TeamCrossWalk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TeamCrossWalkParser {

    @Value("${team_crosswalk.file_path}")
    String teamCrossWalkFilePath;

    public List<TeamCrossWalk> getTeamCrossWalkList() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        Path file = new File(classLoader.getResource(teamCrossWalkFilePath).getFile()).toPath();
        Stream<String> lines = Files.lines(file);
        List<TeamCrossWalk> teamCrossWalkList = lines
                .skip(1)
                .map((line) -> line.split(","))
                .filter(record -> record.length == 3)
                .map(record -> new TeamCrossWalk(record[0], record[1], record[2]))
                .collect(Collectors.toList());

        return teamCrossWalkList;

    }

}
