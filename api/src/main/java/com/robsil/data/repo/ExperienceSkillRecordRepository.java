package com.robsil.data.repo;

import com.robsil.data.domain.record.BalanceRecord;
import com.robsil.data.domain.record.ExperienceSkillRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ExperienceSkillRecordRepository extends MongoRepository<ExperienceSkillRecord, String> {

    Optional<ExperienceSkillRecord> findFirstByPlayerIdOrderByCreatedDateDesc(String playerId, String profileId);
}
