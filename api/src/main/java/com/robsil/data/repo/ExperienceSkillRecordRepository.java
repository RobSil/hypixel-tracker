package com.robsil.data.repo;

import com.robsil.data.domain.record.ExperienceSkillRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ExperienceSkillRecordRepository extends MongoRepository<ExperienceSkillRecord, String> {

    Optional<ExperienceSkillRecord> findFirstByPlayerIdAndProfileIdOrderByCreatedDateDesc(String playerId, String profileId);
}
