package com.robsil.model;

import com.robsil.data.domain.record.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverallInformationDto {

    String playerId;

    String profileId;

    private CollectionRecord collectionRecord;

    private ExperienceSkillRecord experienceSkillRecord;

    private KillRecord killRecord;

    private PlayerClassRecord playerClassRecord;

    private BalanceRecord balanceRecord;
}
