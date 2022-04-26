package com.robsil.data.repo;

import com.robsil.data.domain.record.BalanceRecord;
import com.robsil.data.domain.record.KillRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface KillRecordRepository extends MongoRepository<KillRecord, String> {

    Optional<KillRecord> findFirstByPlayerIdOrderByCreatedDateDesc(String playerId, String profileId);
}
