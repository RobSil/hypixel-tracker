package com.robsil.service;

import com.robsil.data.domain.record.ExperienceSkillRecord;
import com.robsil.data.repo.ExperienceSkillRecordRepository;
import com.robsil.model.ExperienceSkillRecordInfoDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@NoArgsConstructor
public class ExperienceSkillRecordService {

    @Autowired
    private ExperienceSkillRecordRepository experienceSkillRecordRepository;

    public ExperienceSkillRecord getById(String id) {
        ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordRepository.findById(id).orElse(null);

        if (experienceSkillRecord != null) {
            return experienceSkillRecord;
        } else {
            log.warn("experienceSkillRecord: can't find such a experienceSkillRecord.");
            return null;
        }
    }

    public ExperienceSkillRecord getLast(String playerId, String profileId) {
        return experienceSkillRecordRepository.findFirstByPlayerIdOrderByCreatedDateDesc(playerId, profileId).orElse(null);
    }

    public ExperienceSkillRecord save(ExperienceSkillRecord experienceSkillRecord) {
        return experienceSkillRecordRepository.save(experienceSkillRecord);
    }

    public ExperienceSkillRecord save(ExperienceSkillRecordInfoDto experienceSkillRecordInfoDto) {
        return save(ExperienceSkillRecord.builder()
                            .playerId(experienceSkillRecordInfoDto.getPlayerId())
                            .profileId(experienceSkillRecordInfoDto.getProfileId())
                            .experienceSkills(experienceSkillRecordInfoDto.getExperienceSkills())
                            .build());
    }
}
