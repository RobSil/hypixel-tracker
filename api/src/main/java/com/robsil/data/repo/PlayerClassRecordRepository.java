package com.robsil.data.repo;

import com.robsil.data.domain.record.PlayerClassRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlayerClassRecordRepository extends MongoRepository<PlayerClassRecord, String> {

    Optional<PlayerClassRecord> findFirstByPlayerIdAndProfileIdOrderByCreatedDateDesc(String playerId, String profileId);
}
