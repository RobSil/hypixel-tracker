package com.robsil.service;

import com.robsil.data.domain.Profile;
import com.robsil.data.domain.record.*;
import com.robsil.model.*;
import com.robsil.model.exception.NoContentException;
import com.robsil.model.exception.TotalRecordNotFound;
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

    public TotalRecordDto handleOverallDifference(String playerUuid, String hpId, LocalDate dateFrom, LocalDate dateTo) {
        List<TotalRecord> totalRecordsFrom = totalRecordService.getAllByHpIdAndBetweenDates(hpId,
                                                                                            dateFrom.atStartOfDay(),
                                                                                            dateFrom.plusDays(1).atStartOfDay());

        List<TotalRecord> totalRecordsTo = totalRecordService.getAllByHpIdAndBetweenDates(hpId,
                                                                                            dateTo.atStartOfDay(),
                                                                                          dateTo.plusDays(1).atStartOfDay());

        TotalRecord totalRecordFrom = totalRecordsFrom.stream().findFirst().orElse(null);
        TotalRecord totalRecordTo = totalRecordsTo.stream().findFirst().orElse(null);

        if (totalRecordFrom == null) {
            throw new TotalRecordNotFound("Can't find any totalRecord -> FROM. LocalDate: " + dateFrom.toString());
        }
        if (totalRecordTo == null) {
            throw new TotalRecordNotFound("Can't find any totalRecord -> TO. LocalDate: " + dateTo.toString());
        }

//        ExperienceSkillRecordInfoDto experienceSkillTo = totalRecordTo.getExperienceSkillRecord();
//        ExperienceSkillRecordInfoDto experienceSkillFrom = totalRecordFrom.getExperienceSkillRecord();
//        experienceSkillTo.setExperienceSkills(experienceSkillTo.getExperienceSkills().forEach(experienceSkill -> new ExperienceSkill(experienceSkill.getSkillName(),
//                                                                                                                                     experienceSkill.getSkillEntity(),
//                                                                                                                                     experienceSkill.)););
//        totalRecordTo.setExperienceSkillRecord(total);

        return new TotalRecordDto();
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

}
