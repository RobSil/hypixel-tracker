package com.robsil.data.repo;

import com.robsil.data.domain.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile, String> {

    List<Profile> findAllByPlayerId(String playerId);

    Optional<Profile> findByHpId(String hpId);

    Optional<Profile> findByHpIdAndPlayerUuid(String hpId, String playerUuid);

    void deleteAllByPlayerId(String playerId);
}
