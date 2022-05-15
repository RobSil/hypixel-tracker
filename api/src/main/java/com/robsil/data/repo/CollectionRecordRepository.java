package com.robsil.data.repo;

import com.robsil.data.domain.record.CollectionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CollectionRecordRepository extends MongoRepository<CollectionRecord, String> {

    Optional<CollectionRecord> findFirstByPlayerIdAndProfileIdOrderByCreatedDateDesc(String playerId, String profileId);
}
