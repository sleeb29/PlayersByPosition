package com.baseball.players_by_position.jpa.repository;

import com.baseball.players_by_position.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query("select p from Player p where p.depth = :depth")
    public Set<Player> getAllByDepth(@Param("depth") int depth);

    //public void saveAll(List<Player> player);

}