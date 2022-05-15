package com.robsil.data.repo;

import com.robsil.data.domain.record.TotalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TotalRecordRepository extends MongoRepository<TotalRecord, String> {

    List<TotalRecord> findAllByHpId(String hpId);

//    List<TotalRecord> findAllByHpIdAndCreatedDateBetween(String hpId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<TotalRecord> findAllByHpIdAndCreatedDateBetween(String hpId, LocalDate dateFrom, LocalDate dateTo);
}
