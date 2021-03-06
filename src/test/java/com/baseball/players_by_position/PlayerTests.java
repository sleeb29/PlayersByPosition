package com.baseball.players_by_position;

import com.baseball.players_by_position.configuration.custom.PlayerServiceCustomMappingConfig;
import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.service.repository.PlayerRepository;
import com.baseball.players_by_position.service.service.PlayerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerTests {

    static int playerIDCounter = 0;

    final String DUMMY_POSITION = "POSITION";
    final String STARTING_PITCHER_STRING = "SP";

    @Autowired
    private PlayerServiceCustomMappingConfig playerServiceCustomMappingConfig;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @Test
    public void testAddingOnePlayer() {

        Player player = getDummyPlayer();

        addPlayerToRepository(player);
        int sizeOfRepository = getPlayerRepositorySize();
        deletePlayersFromRepository();

        Assert.assertEquals(1, sizeOfRepository);
        Assert.assertEquals(0, getPlayerRepositorySize());

    }

    @Test
    public void testGettingOnlyStarters() {

        Player starter = getDummyPlayer();
        starter.setDepth(playerServiceCustomMappingConfig.getStarterDepthLevel());
        starter.setPosition(DUMMY_POSITION);
        addPlayerToRepository(starter);

        Player nonStarter = getDummyPlayer();
        nonStarter.setDepth(playerServiceCustomMappingConfig.getStarterDepthLevel() + 1);
        nonStarter.setPosition(DUMMY_POSITION);
        addPlayerToRepository(nonStarter);

        HashMap<String, List<Player>> positionToStartingPlayersMap = playerService.getPositionToStartingPlayersMap();
        List<Player> starters = positionToStartingPlayersMap.get(DUMMY_POSITION);

        deletePlayersFromRepository();

        Assert.assertEquals(playerServiceCustomMappingConfig.getStarterDepthLevel(), starters.size());
        Assert.assertEquals(0, getPlayerRepositorySize());

    }

    @Test
    public void testGettingStartingPitchers() {

        Map<String, Integer> positionToCustomDepthLevelMap = playerServiceCustomMappingConfig.getPositionToCustomDepthLevelMap();
        int startingPitcherDepthNum = positionToCustomDepthLevelMap.get(STARTING_PITCHER_STRING);

        for (int i = 1; i <= startingPitcherDepthNum + 1; i++) {

            Player startingPitcher = getDummyPlayer();
            startingPitcher.setDepth(startingPitcherDepthNum);
            startingPitcher.setPosition(STARTING_PITCHER_STRING);
            startingPitcher.setDepth(i);
            addPlayerToRepository(startingPitcher);

        }

        HashMap<String, List<Player>> positionToStartingPlayersMap = playerService.getPositionToStartingPlayersMap();
        List<Player> starters = positionToStartingPlayersMap.get(STARTING_PITCHER_STRING);
        deletePlayersFromRepository();

        Assert.assertEquals(startingPitcherDepthNum, starters.size());
        Assert.assertEquals(0, getPlayerRepositorySize());

    }

    private Player getDummyPlayer() {

        Player player = new Player();
        player.setId(Integer.toString(playerIDCounter++));
        player.setPosition(DUMMY_POSITION);
        player.setLastName("LAST");
        player.setFirstName("FIRST");
        player.setRank(1);
        player.setStatus("MIN");
        player.setTeam("TEAM");
        player.setDepth(playerServiceCustomMappingConfig.getStarterDepthLevel());
        player.setLookupFirstName(player.getFirstName());
        player.setLookupLastName(player.getLastName());

        return player;

    }

    private void addPlayerToRepository(Player player) {
        playerRepository.save(player);
    }

    private void deletePlayersFromRepository() {
        playerRepository.deleteAll();
    }

    private int getPlayerRepositorySize() {

        Iterable playerStageIterable = playerRepository.findAll();
        int size = 0;
        for (Object object : playerStageIterable) {
            size++;
        }
        return size;

    }

}
