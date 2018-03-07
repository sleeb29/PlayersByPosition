package com.baseball.players_by_position.service.repository;

import com.baseball.players_by_position.model.RankPlayerStage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankPlayerStageRepository extends CrudRepository<RankPlayerStage, Long> {

}