package com.baseball.players_by_position.service.repository;

import com.baseball.players_by_position.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query(value = "select p from Player p where p.depth = :depth or (p.position = 'SP' and p.depth <= 5) or (p.position = 'CL' and p.depth <= 2) order by p.rank")
    List<Player> getAllStarters(@Param("depth") int depth);

}