package com.robsil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalRecordDto {

    private String id;

    private LocalDateTime createdDate;

    private String playerUuid;

    private String hpId;

    private BalanceRecordInfoDto balanceRecord;

    private CollectionRecordInfoDto collectionRecord;

    private ExperienceSkillRecordInfoDto experienceSkillRecord;

    private KillRecordInfoDto killRecord;

    private PlayerClassRecordInfoDto playerClassRecord;
}
