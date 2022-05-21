package com.robsil.data.repo;

import com.robsil.data.domain.record.BalanceRecord;
import com.robsil.data.domain.record.TotalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TotalRecordRepository extends MongoRepository<TotalRecord, String> {

    List<TotalRecord> findAllByHpId(String hpId);

//    List<TotalRecord> findAllByHpIdAndCreatedDateBetween(String hpId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<TotalRecord> findAllByHpIdAndCreatedDateBetween(String hpId, LocalDateTime dateFrom, LocalDateTime dateTo);

    Optional<TotalRecord> findFirstByHpIdOrderByCreatedDateDesc(String hpId);
}
