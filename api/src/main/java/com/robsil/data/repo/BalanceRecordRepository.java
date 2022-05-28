package com.robsil.data.repo;

import com.robsil.data.domain.record.BalanceRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface BalanceRecordRepository extends MongoRepository<BalanceRecord, String> {

//    @Query("select x from BalanceRecord x where x.playerId = ?1 order by x.createdDate desc")
    Optional<BalanceRecord> findFirstByPlayerUuidOrderByCreatedDateDesc(String playerId);
}
