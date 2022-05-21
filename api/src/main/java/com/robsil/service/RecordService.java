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

import javax.annotation.PostConstruct;
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

    private TotalRecordMapper totalRecordMapper;


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

        //        Profile profile = profileService.getByHpId(hpId);
        Profile profile = profileService.getByHpIdAndPlayerUuid(hpId,
                                                                playerUuid);
        //        String playerUuid = profile.getPlayerUuid();

        log.info(profile.getTitle());

        TotalRecord totalRecord = totalRecordService.getLastByHpId(hpId);

        if (totalRecord == null) {
            return null;
        }

        totalRecordMapper = Mappers.getMapper(TotalRecordMapper.class);

        return totalRecordMapper.totalRecordToTotalRecordDto(totalRecord);

//        BalanceRecord balanceRecord = balanceRecordService.getLast(playerUuid);
//        ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordService.getLast(playerUuid,
//                                                                                           hpId);
//
//        KillRecord killRecord = killRecordService.getLast(playerUuid,
//                                                          hpId);
//        PlayerClassRecord playerClassRecord = playerClassRecordService.getLast(playerUuid,
//                                                                               hpId);
//
//
//        CollectionRecord collectionRecord = collectionRecordService.getLast(playerUuid,
//                                                                            hpId);
//
//        if (balanceRecord == null && killRecord == null && experienceSkillRecord == null && playerClassRecord == null && collectionRecord == null) {
//            throw new NoContentException("No content found");
//        }


//        return formatOverallInformationDto(playerUuid,
//                                           hpId,
//                                           balanceRecord,
//                                           killRecord,
//                                           experienceSkillRecord,
//                                           playerClassRecord,
//                                           collectionRecord);
        //        return OverallInformationDto.builder()
        //                .playerUuid(playerUuid)
        //                .hpId(hpId)
        //                .balanceRecord(balanceRecord)
        //                .experienceSkillRecord(experienceSkillRecord)
        //                .killRecord(killRecord)
        //                .playerClassRecord(playerClassRecord)
        //                .collectionRecord(collectionRecord)
        //                .build();
    }

    public TotalRecordDto getOverallInformation(String playerUuid, String hpId, LocalDate date) {
        //        List<TotalRecord> totalRecords = totalRecordService.getAllByHpIdAndBetweenDates(hpId,
        //                                                                                        date.atStartOfDay(),
        //                                                                                        date.atTime(23,
        //                                                                                                    59,
        //                                                                                                    59));
        List<TotalRecord> totalRecords = totalRecordService.getAllByHpIdAndBetweenDates(hpId,
                                                                                        date.atStartOfDay(),
                                                                                        date.plusDays(1).atStartOfDay());

        TotalRecord totalRecord = totalRecords.stream().findFirst().orElse(null);

        if (totalRecord == null) {
            throw new NoContentException("No content found");
        }

        return totalRecordMapper.totalRecordToTotalRecordDto(totalRecord);

//        BalanceRecord balanceRecord = balanceRecordService.getById(totalRecord.getBalanceRecordId());
//        KillRecord killRecord = killRecordService.getById(totalRecord.getKillRecordId());
//        ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordService.getById(totalRecord.getExperienceSkillRecordId());
//        PlayerClassRecord playerClassRecord = playerClassRecordService.getById(totalRecord.getPlayerClassRecordId());
//        CollectionRecord collectionRecord = collectionRecordService.getById(totalRecord.getCollectionRecordId());
//
//
//        return formatOverallInformationDto(playerUuid,
//                                           hpId,
//                                           balanceRecord,
//                                           killRecord,
//                                           experienceSkillRecord,
//                                           playerClassRecord,
//                                           collectionRecord);

    }

    public OverallInformationDto getDifferenceInformation(String playerUuid, String hpId, LocalDate dateFrom, LocalDate dateTo) {


        return null;
    }

//    public OverallInformationDto saveOverallInformation(OverallInformationDto overallInformationDto) {
//
//        if (overallInformationDto != null) {
//            BalanceRecord balanceRecord = balanceRecordService.getById(overallInformationDto.getBalanceRecord().getId());
//            KillRecord killRecord = killRecordService.getById(overallInformationDto.getKillRecord().getId());
//            ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordService.getById(overallInformationDto.getExperienceSkillRecord().getId());
//            PlayerClassRecord playerClassRecord = playerClassRecordService.getById(overallInformationDto.getPlayerClassRecord().getId());
//            CollectionRecord collectionRecord = collectionRecordService.getById(overallInformationDto.getCollectionRecord().getId());
//
//            if (balanceRecord != null && killRecord != null && experienceSkillRecord != null && playerClassRecord != null & collectionRecord != null) {
//
//                balanceRecord = balanceRecordService.save(balanceRecord);
//                killRecord = killRecordService.save(killRecord);
//                experienceSkillRecord = experienceSkillRecordService.save(experienceSkillRecord);
//                playerClassRecord = playerClassRecordService.save(playerClassRecord);
//                collectionRecord = collectionRecordService.save(collectionRecord);
//
//                //                if (balanceRecord != null && killRecord != null && experienceSkillRecord != null && playerClassRecord != null && collectionRecord != null) {
//                totalRecordService.save(TotalRecord.builder()
//                                                .playerUuid(overallInformationDto.getPlayerUuid())
//                                                .hpId(overallInformationDto.getHpId())
//                                                //                                                .balanceRecordId(balanceRecord.getId())
//                                                .collectionRecordId(collectionRecord.getId())
//                                                .experienceSkillRecordId(experienceSkillRecord.getId())
//                                                .killRecordId(killRecord.getId())
//                                                .playerClassRecordId(playerClassRecord.getId())
//                                                .build());
//
//                return OverallInformationDto.builder()
//                        .playerUuid(overallInformationDto.getPlayerUuid())
//                        .hpId(overallInformationDto.getHpId())
//                        .balanceRecord(balanceRecord)
//                        .killRecord(killRecord)
//                        .experienceSkillRecord(experienceSkillRecord)
//                        .playerClassRecord(playerClassRecord)
//                        .collectionRecord(collectionRecord)
//                        .build();
//                //                }
//            }
//
//        }
//
//        log.warn("recordService saveOverallInformation: something went wrong");
//        return null;
//    }

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

    //    public OverallInformationDto saveOverallInformation(ShortOverallInformationDto shortOverallInformationDto) {
    //
    //        if (shortOverallInformationDto != null) {
    //            BalanceRecord balanceRecord = balanceRecordService.save(shortOverallInformationDto.getBalanceRecordInfoDto());
    //            KillRecord killRecord = killRecordService.save(shortOverallInformationDto.getKillRecordInfoDto());
    //            ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordService.save(shortOverallInformationDto.getExperienceSkillRecordInfoDto());
    //            PlayerClassRecord playerClassRecord = playerClassRecordService.save(shortOverallInformationDto.getPlayerClassRecordInfoDto());
    //            CollectionRecord collectionRecord = collectionRecordService.save(shortOverallInformationDto.getCollectionRecordInfoDto());
    //
    //            //            if (balanceRecord != null && killRecord != null && experienceSkillRecord != null && playerClassRecord != null && collectionRecord != null) {
    //            totalRecordService.save(TotalRecord.builder()
    //                                            .playerUuid(shortOverallInformationDto.getPlayerUuid())
    //                                            .hpId(shortOverallInformationDto.getProfileId())
    //                                            .balanceRecordId(balanceRecord.getId())
    //                                            .collectionRecordId(collectionRecord.getId())
    //                                            .experienceSkillRecordId(experienceSkillRecord.getId())
    //                                            .killRecordId(killRecord.getId())
    //                                            .playerClassRecordId(playerClassRecord.getId())
    //                                            .build());
    //
    //            return OverallInformationDto.builder()
    //                    .playerUuid(shortOverallInformationDto.getPlayerUuid())
    //                    .hpId(shortOverallInformationDto.getProfileId())
    //                    .balanceRecord(balanceRecord)
    //                    .killRecord(killRecord)
    //                    .experienceSkillRecord(experienceSkillRecord)
    //                    .playerClassRecord(playerClassRecord)
    //                    .collectionRecord(collectionRecord)
    //                    .build();
    //            //            }
    //        }
    //
    //        log.warn("recordService saveOverallInformation: something went wrong");
    //        return null;
    //    }

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
