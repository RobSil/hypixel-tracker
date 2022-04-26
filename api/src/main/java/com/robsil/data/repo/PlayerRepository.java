package com.robsil.data.repo;

import com.robsil.data.domain.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String> {

    Optional<Player> findByUuid(String hypixelId);

    void deleteByUuid(String hypixelId);
}
