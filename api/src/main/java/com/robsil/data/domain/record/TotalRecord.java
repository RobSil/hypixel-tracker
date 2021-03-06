package com.robsil.data.domain.record;

import com.robsil.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "TotalRecord")
public class TotalRecord {

    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    private String playerUuid;

    private String hpId;

    private BalanceRecordInfoDto balanceRecord;

    private CollectionRecordInfoDto collectionRecord;

    private ExperienceSkillRecordInfoDto experienceSkillRecord;

    private KillRecordInfoDto killRecord;

    private PlayerClassRecordInfoDto playerClassRecord;

//    private String balanceRecordId;
//
//    private String collectionRecordId;
//
//    private String experienceSkillRecordId;
//
//    private String killRecordId;
//
//    private String playerClassRecordId;
}
