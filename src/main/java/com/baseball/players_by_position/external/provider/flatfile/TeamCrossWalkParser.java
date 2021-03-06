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
    @Value("${team_crosswalk_file_delimiter}")
    String fileDelimiter;

    @Value("${team_crosswalk.lines_to_skip}")
    int linesToSkip;
    @Value("${team_crosswalk_file_num_of_columns}")
    int numOfColumns;

    public List<TeamCrossWalk> getTeamCrossWalkList() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        Path file = new File(classLoader.getResource(teamCrossWalkFilePath).getFile()).toPath();
        Stream<String> lines = Files.lines(file);
        List<TeamCrossWalk> teamCrossWalkList = lines
                .skip(linesToSkip)
                .map((line) -> line.split(fileDelimiter))
                .filter(record -> record.length == numOfColumns)
                .map(record ->
                        new TeamCrossWalk(replaceWhiteSpace(record[0]),
                                replaceWhiteSpace(record[1]),
                                replaceWhiteSpace(record[2])))
                .collect(Collectors.toList());

        return teamCrossWalkList;

    }

    private String replaceWhiteSpace(String source) {

        return source.replace(" ", "");

    }

}
