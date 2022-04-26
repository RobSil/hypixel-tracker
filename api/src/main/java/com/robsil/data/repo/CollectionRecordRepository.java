package com.robsil.data.repo;

import com.robsil.data.domain.record.BalanceRecord;
import com.robsil.data.domain.record.CollectionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CollectionRecordRepository extends MongoRepository<CollectionRecord, String> {

    Optional<CollectionRecord> findFirstByPlayerIdOrderByCreatedDateDesc(String playerId, String profileId);
}
