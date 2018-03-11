package com.baseball.players_by_position.configuration.custom;

import com.baseball.players_by_position.model.AggregatePosition;
import com.baseball.players_by_position.model.CustomStarterDepthLevelPosition;
import com.baseball.players_by_position.model.PlayerServiceCustomMapping;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class PlayerServiceCustomMappingConfig implements InitializingBean {

    @Value("${custom_mapping.properties_file_location}")
    private String playerServiceCustomMappingLocation;
    private PlayerServiceCustomMapping playerServiceCustomMapping;
    private Map<String, String> positionsToAggregateMap;
    private Map<String, Integer> positionToCustomDepthLevelMap;

    @Override
    public void afterPropertiesSet() {

        try {

            ClassLoader classLoader = getClass().getClassLoader();
            File customMappingFile = new File(classLoader.getResource(playerServiceCustomMappingLocation).getFile());
            FileInputStream fis = new FileInputStream(customMappingFile);
            byte[] customMappingFileData = new byte[(int) customMappingFile.length()];
            fis.read(customMappingFileData);
            fis.close();

            String json = new String(customMappingFileData, "UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            playerServiceCustomMapping = mapper.readValue(json, PlayerServiceCustomMapping.class);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public PlayerServiceCustomMapping getPlayerServiceCustomMapping() {
        return playerServiceCustomMapping;
    }

    public int getStarterDepthLevel() {
        return this.playerServiceCustomMapping.getStarterDepthLevel();
    }

    public Map<String, String> getPositionsToAggregateMap() {

        if (positionsToAggregateMap != null) {
            return positionsToAggregateMap;
        }

        List<AggregatePosition> aggregatePositions = getAggregatePositions();
        positionsToAggregateMap = new HashMap<>();

        for (AggregatePosition aggregatePosition : aggregatePositions) {
            for (String position : aggregatePosition.getPositionsToAggregate()) {
                positionsToAggregateMap.put(position, aggregatePosition.getPosition());
            }
        }

        return positionsToAggregateMap;

    }

    private List<AggregatePosition> getAggregatePositions() {
        return this.playerServiceCustomMapping.getAggregatePositions();
    }

    public Map<String, Integer> getPositionToCustomDepthLevelMap() {

        if (positionToCustomDepthLevelMap != null) {
            return positionToCustomDepthLevelMap;
        }

        List<CustomStarterDepthLevelPosition> customStarterDepthLevelPositions = getCustomStarterDepthLevelPositions();

        positionToCustomDepthLevelMap = new HashMap<>();
        for (CustomStarterDepthLevelPosition customStarterDepthLevelPosition : customStarterDepthLevelPositions) {
            positionToCustomDepthLevelMap.put(customStarterDepthLevelPosition.getPosition(),
                    customStarterDepthLevelPosition.getDepth());
        }

        return positionToCustomDepthLevelMap;

    }

    private List<CustomStarterDepthLevelPosition> getCustomStarterDepthLevelPositions() {
        return this.playerServiceCustomMapping.getCustomStarterDepthLevelPositions();
    }

}
