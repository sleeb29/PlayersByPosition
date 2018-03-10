package com.baseball.players_by_position.external.provider.deserialize;

import com.baseball.players_by_position.model.LeagueDepthChart;
import com.baseball.players_by_position.model.PlayerStage;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepthChartDeserializer extends StdDeserializer<LeagueDepthChart> {

    private final String TEAMS_FIELD = "teams";
    private final String TEAM_NAME_FIELD = "abbr";
    private final String POSITIONS_FIELD = "positions";
    private final String PLAYERS_FIELD = "players";
    private final String POSITION_NAME_FIELD = "name";
    private final String DEPTH_FIELD = "depth";
    private final String JERSEY_NUMBER_FIELD = "jersey_number";
    private final String FIRST_NAME_FIELD = "first_name";
    private final String LAST_NAME_FIELD = "last_name";
    private final String PLAYER_ID_FIELD = "id";
    private final String STATUS_FIELD = "status";

    public DepthChartDeserializer() {
        this(null);
    }

    public DepthChartDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LeagueDepthChart deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        TreeNode rootNode = node.get(TEAMS_FIELD);
        List<PlayerStage> players = getPlayers(rootNode);

        LeagueDepthChart leagueDepthChart = new LeagueDepthChart();
        leagueDepthChart.setPlayers(Collections.unmodifiableList(new ArrayList<>(players)));

        return leagueDepthChart;

    }

    private List<PlayerStage> getPlayers(TreeNode rootNode) {

        List<PlayerStage> players = new ArrayList<>();
        Integer numOfTeams = rootNode.size();

        for (int i = 0; i < numOfTeams; i++) {

            TreeNode teamNode = rootNode.get(i);
            String teamName = teamNode.get(TEAM_NAME_FIELD).toString();

            players.addAll(getPlayersForTeam(teamNode, teamName));

        }

        return players;

    }

    private List<PlayerStage> getPlayersForTeam(TreeNode teamNode, String teamName) {

        TreeNode positionsNode = teamNode.get(POSITIONS_FIELD);
        Integer numOfPositions = positionsNode.size();
        ArrayList<PlayerStage> players = new ArrayList<>();

        for (int i = 0; i < numOfPositions; i++) {

            TreeNode positionNode = positionsNode.get(i);
            if (positionNode == null) {
                continue;
            }

            TreeNode playersNode = positionNode.get(PLAYERS_FIELD);
            if (playersNode == null) {
                continue;
            }

            String positionName = formatTreeNodeToString(positionNode.get(POSITION_NAME_FIELD));

            players.addAll(getPlayersForPositionForTeam(playersNode, teamName, positionName));

        }

        return players;

    }

    private List<PlayerStage> getPlayersForPositionForTeam(TreeNode playersNode, String teamName, String positionName) {

        Integer numOfPlayers = playersNode.size();
        ArrayList<PlayerStage> players = new ArrayList<>();

        for (int i = 0; i < numOfPlayers; i++) {

            TreeNode playerNode = playersNode.get(i);
            PlayerStage playerStage = getPlayerStage(playerNode, teamName, positionName);

            if (playerStage == null) {
                continue;
            }

            players.add(playerStage);

        }

        return players;

    }

    private PlayerStage getPlayerStage(TreeNode playerNode, String teamName, String positionName) {

        if (playerNode == null) {
            return null;
        }

        TreeNode depthNode = playerNode.get(DEPTH_FIELD);

        if (depthNode == null) {
            return null;
        }

        int depth = Integer.parseInt(playerNode.get(DEPTH_FIELD).toString());

        TreeNode jerseyNode = playerNode.get(JERSEY_NUMBER_FIELD);

        String firstName = formatTreeNodeToString(playerNode.get(FIRST_NAME_FIELD));
        String lastName = formatTreeNodeToString(playerNode.get(LAST_NAME_FIELD));

        int jersey = 0;

        if (jerseyNode != null) {
            jersey = Integer.parseInt(formatTreeNodeToString(playerNode.get(JERSEY_NUMBER_FIELD)));
        }

        PlayerStage playerStage = new PlayerStage();

        playerStage.setId(formatTreeNodeToString(playerNode.get(PLAYER_ID_FIELD)));
        playerStage.setFirstName(firstName);
        playerStage.setLastName(lastName);
        playerStage.setTeam(teamName.replace("\"", ""));
        playerStage.setPosition(positionName);
        playerStage.setDepth(depth);
        playerStage.setStatus(formatTreeNodeToString(playerNode.get(STATUS_FIELD)));
        playerStage.setJersey(jersey);

        return playerStage;

    }

    private String formatTreeNodeToString(TreeNode sourceString) {
        return sourceString.toString().replace("\"", "");
    }

}
