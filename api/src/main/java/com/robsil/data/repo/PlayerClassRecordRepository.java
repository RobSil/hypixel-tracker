package com.robsil.data.repo;

import com.robsil.data.domain.record.BalanceRecord;
import com.robsil.data.domain.record.PlayerClassRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PlayerClassRecordRepository extends MongoRepository<PlayerClassRecord, String> {

    Optional<PlayerClassRecord> findFirstByPlayerIdOrderByCreatedDateDesc(String playerId, String profileId);
}
