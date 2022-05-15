package com.robsil.data.repo;

import com.robsil.data.domain.record.KillRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface KillRecordRepository extends MongoRepository<KillRecord, String> {

    Optional<KillRecord> findFirstByPlayerIdAndProfileIdOrderByCreatedDateDesc(String playerId, String profileId);
}
