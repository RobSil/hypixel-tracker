package com.robsil.service;

import com.robsil.data.domain.Profile;
import com.robsil.data.domain.record.*;
import com.robsil.model.*;
import com.robsil.model.exception.NoContentException;
import com.robsil.model.mapstruct.TotalRecordMapper;
import com.robsil.util.ExperienceUtil;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@NoArgsConstructor
public class RecordService {

    @Autowired
    private BalanceRecordService balanceRecordService;

    @Autowired
    private ExperienceSkillRecordService experienceSkillRecordService;

    @Autowired
    private KillRecordService killRecordService;

    @Autowired
    private PlayerClassRecordService playerClassRecordService;

    @Autowired
    private CollectionRecordService collectionRecordService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private TotalRecordService totalRecordService;

    @Autowired
    private ExperienceUtil experienceUtil;

    private final TotalRecordMapper totalRecordMapper = TotalRecordMapper.INSTANCE;


    public TotalRecordDto handleOverallInformation(String playerUuid, String hpId, LocalDate date) {
        if ((playerUuid == null || hpId == null) || (playerUuid.isBlank() || hpId.isBlank())) {
            throw new IllegalArgumentException();
        }
        if (date != null) {
            return getOverallInformation(playerUuid,
                                         hpId,
                                         date);
        }
        return getOverallInformation(playerUuid,
                                     hpId);
    }

    public TotalRecordDto getOverallInformation(String playerUuid, String hpId) {

        Profile profile = profileService.getByHpIdAndPlayerUuid(hpId,
                                                                playerUuid);

        log.info(profile.getTitle());

        TotalRecord totalRecord = totalRecordService.getLastByHpId(hpId);

        if (totalRecord == null) {
            return null;
        }

        return totalRecordMapper.toTotalRecordDto(totalRecord);
    }

    public TotalRecordDto getOverallInformation(String playerUuid, String hpId, LocalDate date) {
        List<TotalRecord> totalRecords = totalRecordService.getAllByHpIdAndBetweenDates(hpId,
                                                                                        date.atStartOfDay(),
                                                                                        date.plusDays(1).atStartOfDay());

        TotalRecord totalRecord = totalRecords.stream().findFirst().orElse(null);

        if (totalRecord == null) {
            throw new NoContentException("No content found");
        }

        return totalRecordMapper.toTotalRecordDto(totalRecord);

    }

    public OverallInformationDto getDifferenceInformation(String playerUuid, String hpId, LocalDate dateFrom, LocalDate dateTo) {


        return null;
    }

    public TotalRecord saveOverallInformation(ShortOverallInformationDto dto) {

        if (dto == null) {
            return null;
        }

        TotalRecord totalRecord = TotalRecord.builder()
                .playerUuid(dto.getPlayerUuid())
                .hpId(dto.getHpId())
                .balanceRecord(dto.getBalanceRecordInfoDto())
                .collectionRecord(dto.getCollectionRecordInfoDto())
                .killRecord(dto.getKillRecordInfoDto())
                .experienceSkillRecord(dto.getExperienceSkillRecordInfoDto())
                .playerClassRecord(dto.getPlayerClassRecordInfoDto())
                .build();

        totalRecord = totalRecordService.save(totalRecord);

        return totalRecord;
    }

    public OverallInformationDto formatOverallInformationDto(String playerUuid,
                                                             String hpId,
                                                             BalanceRecord balanceRecord,
                                                             KillRecord killRecord,
                                                             ExperienceSkillRecord experienceSkillRecord,
                                                             PlayerClassRecord playerClassRecord,
                                                             CollectionRecord collectionRecord) {


        if (playerClassRecord != null) {
            playerClassRecord.setPlayerClasses(playerClassRecord.getPlayerClasses()
                                                       .stream()
                                                       .map(playerClass -> new PlayerClassDto(playerClass.getClassName(),
                                                                                              playerClass.getExp(),
                                                                                              experienceUtil.dungeoneeringXpToLevel(playerClass.getExp().intValue())))
                                                       .collect(Collectors.toList()));
        }

        if (experienceSkillRecord != null) {
            experienceSkillRecord
                    .setExperienceSkills(experienceSkillRecord.getExperienceSkills() != null ?
                                                 experienceSkillRecord.getExperienceSkills()
                                                         .stream()
                                                         .map(experienceSkill -> new ExperienceSkillDto(experienceSkill.getSkillName(),
                                                                                                        experienceSkill.getSkillEntity(),
                                                                                                        experienceSkill.getExp(),
                                                                                                        experienceUtil.universalXpToLevel(experienceSkill.getExp().intValue())))
                                                         .collect(Collectors.toList()) : null);
        }

        return OverallInformationDto.builder()
                .playerUuid(playerUuid)
                .hpId(hpId)
                .balanceRecord(balanceRecord)
                .experienceSkillRecord(experienceSkillRecord)
                .killRecord(killRecord)
                .playerClassRecord(playerClassRecord)
                .collectionRecord(collectionRecord)
                .build();
    }
}
