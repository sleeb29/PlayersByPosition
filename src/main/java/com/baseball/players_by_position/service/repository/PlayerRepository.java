package com.baseball.players_by_position.service.repository;

import com.baseball.players_by_position.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query(name = "player_repository.get_all_by_depth")
    Set<Player> getAllByDepth(@Param("depth") int depth);

}