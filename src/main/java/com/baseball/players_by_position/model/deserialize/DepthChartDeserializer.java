package com.baseball.players_by_position.model.deserialize;

import com.baseball.players_by_position.model.model.LeagueDepthChart;
import com.baseball.players_by_position.model.model.Player;
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
        TreeNode rootNode = node.get("teams");
        List<Player> players = getPlayers(rootNode);

        LeagueDepthChart leagueDepthChart = new LeagueDepthChart();
        leagueDepthChart.setPlayers(Collections.unmodifiableList(new ArrayList<>(players)));

        return leagueDepthChart;

    }

    private List<Player> getPlayers(TreeNode rootNode){

        List<Player> players = new ArrayList<>();
        Integer numOfTeams = rootNode.size();

        for(int i = 0; i < numOfTeams; i++){

            TreeNode teamNode = rootNode.get(i);
            String teamName = teamNode.get("abbr").toString();

            players.addAll(getPlayersForTeam(teamNode, teamName));

        }

        return players;

    }

    private List<Player> getPlayersForTeam(TreeNode teamNode, String teamName){

        TreeNode positionsNode = teamNode.get("positions");
        Integer numOfPositions = positionsNode.size();
        ArrayList<Player> players = new ArrayList<>();

        for(int i = 0; i < numOfPositions; i++){

            TreeNode positionNode = positionsNode.get(i);
            if(positionNode == null){
                continue;
            }

            TreeNode playersNode = positionNode.get("players");
            if(playersNode == null){
                continue;
            }

            String positionName = positionNode.get("name").toString().replace("\"","");

            players.addAll(getPlayersForPositionForTeam(playersNode, teamName, positionName));

        }

        return players;

    }

    private List<Player> getPlayersForPositionForTeam(TreeNode playersNode, String teamName, String positionName){

        Integer numOfPlayers = playersNode.size();
        ArrayList<Player> players = new ArrayList<>();

        for(int i = 0; i < numOfPlayers; i++){

            TreeNode playerNode = playersNode.get(i);
            Player player = getPlayer(playerNode, teamName, positionName);

            if(player == null){
                continue;
            }

            players.add(player);

        }

        return players;

    }

    private Player getPlayer(TreeNode playerNode, String teamName, String positionName){

        if(playerNode == null){
            return null;
        }

        TreeNode depthNode = playerNode.get("depth");

        if(depthNode == null){
            return null;
        }

        int depth = Integer.parseInt(playerNode.get("depth").toString());

        Player player = new Player();
        player.setFirstName(playerNode.get("first_name").toString().replace("\"",""));
        player.setLastName(playerNode.get("last_name").toString().replace("\"",""));
        player.setTeam(teamName.replace("\"",""));
        player.setPosition(positionName);
        player.setDepth(depth);

        return player;

    }

}
