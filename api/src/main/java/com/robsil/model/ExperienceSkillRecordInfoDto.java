package com.robsil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceSkillRecordInfoDto {

    private String playerUuid;

    private String hpId;

    private List<ExperienceSkill> experienceSkills;
}
