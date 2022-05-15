package com.robsil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortOverallInformationDto {

    String playerUuid;

    String profileId;

    private CollectionRecordInfoDto collectionRecordInfoDto;

    private ExperienceSkillRecordInfoDto experienceSkillRecordInfoDto;

    private PlayerClassRecordInfoDto playerClassRecordInfoDto;

    private KillRecordInfoDto killRecordInfoDto;

    private BalanceRecordInfoDto balanceRecordInfoDto;
}
