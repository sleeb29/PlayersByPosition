package com.baseball.players_by_position;

import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.service.repository.PlayerRepository;
import com.baseball.players_by_position.service.service.PlayerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerTests {

    static String DUMMY_POSITION = "POSITION";
    static int playerIDCounter = 0;

    @Autowired
    private PlayerRepository playerRepository;

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
        starter.setDepth(PlayerServiceImpl.STARTING_DEPTH_POSITION_NUM);
        starter.setPosition(DUMMY_POSITION);
        addPlayerToRepository(starter);

        Player nonStarter = getDummyPlayer();
        nonStarter.setDepth(PlayerServiceImpl.STARTING_DEPTH_POSITION_NUM + 1);
        nonStarter.setPosition(DUMMY_POSITION);
        addPlayerToRepository(nonStarter);

        List<Player> starters = playerRepository.getAllStarters(PlayerServiceImpl.STARTING_DEPTH_POSITION_NUM);
        deletePlayersFromRepository();

        Assert.assertEquals(PlayerServiceImpl.STARTING_DEPTH_POSITION_NUM, starters.size());
        Assert.assertEquals(0, getPlayerRepositorySize());

    }

    @Test
    public void testGettingStartingPitchers() {

        for (int i = 1; i <= PlayerServiceImpl.STARTER_PITCHER_DEPTH_POSITION_NUM + 1; i++) {

            Player startingPitcher = getDummyPlayer();
            startingPitcher.setDepth(PlayerServiceImpl.STARTER_PITCHER_DEPTH_POSITION_NUM);
            startingPitcher.setPosition(PlayerServiceImpl.STARTER_PITCHER);
            startingPitcher.setDepth(i);
            addPlayerToRepository(startingPitcher);

        }

        List<Player> starters = playerRepository.getAllStarters(PlayerServiceImpl.STARTING_DEPTH_POSITION_NUM);
        deletePlayersFromRepository();

        Assert.assertEquals(PlayerServiceImpl.STARTER_PITCHER_DEPTH_POSITION_NUM, starters.size());
        Assert.assertEquals(0, getPlayerRepositorySize());

    }

    @Test
    public void testGettingClosingPitchers() {

        for (int i = 1; i <= PlayerServiceImpl.CLOSER_DEPTH_POSITION_NUM + 1; i++) {

            Player closer = getDummyPlayer();
            closer.setDepth(PlayerServiceImpl.CLOSER_DEPTH_POSITION_NUM);
            closer.setPosition(PlayerServiceImpl.CLOSER);
            closer.setDepth(i);
            addPlayerToRepository(closer);

        }

        List<Player> startingClosers = playerRepository.getAllStarters(PlayerServiceImpl.CLOSER_DEPTH_POSITION_NUM);
        deletePlayersFromRepository();

        Assert.assertEquals(PlayerServiceImpl.CLOSER_DEPTH_POSITION_NUM, startingClosers.size());
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
        player.setDepth(PlayerServiceImpl.STARTING_DEPTH_POSITION_NUM);
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
