package com.robsil.data.repo;

import com.robsil.data.domain.record.BalanceRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BalanceRecordRepository extends MongoRepository<BalanceRecord, String> {

//    @Query("select x from BalanceRecord x where x.playerId = ?1 order by x.createdDate desc")
    Optional<BalanceRecord> findFirstByPlayerIdOrderByCreatedDateDesc(String playerId);
}
