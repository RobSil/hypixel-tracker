package com.robsil.service;

import com.robsil.data.domain.Profile;
import com.robsil.data.domain.record.*;
import com.robsil.model.*;
import com.robsil.util.ExperienceUtil;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
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


    public OverallInformationDto handleOverallInformation(String playerUuid, String hpId, LocalDate date) {
        if ((playerUuid == null || hpId == null) || (playerUuid.isBlank() || hpId.isBlank())) {
            throw new IllegalArgumentException();
        }
        if (date != null) {
            return getOverallInformation(playerUuid, hpId, date);
        }
        return getOverallInformation(playerUuid, hpId);
    }

    public OverallInformationDto getOverallInformation(String playerUuid, String hpId) {

        //        Profile profile = profileService.getByHpId(hpId);
        Profile profile = profileService.getByHpIdAndPlayerUuid(hpId,
                                                                playerUuid);
        //        String playerUuid = profile.getPlayerUuid();

        log.info(profile.getTitle());

        BalanceRecord balanceRecord = balanceRecordService.getLast(playerUuid);
        ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordService.getLast(playerUuid,
                                                                                           hpId);

        KillRecord killRecord = killRecordService.getLast(playerUuid,
                                                          hpId);
        PlayerClassRecord playerClassRecord = playerClassRecordService.getLast(playerUuid,
                                                                               hpId);


        CollectionRecord collectionRecord = collectionRecordService.getLast(playerUuid,
                                                                            hpId);


        return formatOverallInformationDto(playerUuid,
                                           hpId,
                                           balanceRecord,
                                           killRecord,
                                           experienceSkillRecord,
                                           playerClassRecord,
                                           collectionRecord);
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

    public OverallInformationDto getOverallInformation(String playerUuid, String hpId, LocalDate date) {
//        List<TotalRecord> totalRecords = totalRecordService.getAllByHpIdAndBetweenDates(hpId,
//                                                                                        date.atStartOfDay(),
//                                                                                        date.atTime(23,
//                                                                                                    59,
//                                                                                                    59));
        List<TotalRecord> totalRecords = totalRecordService.getAllByHpIdAndBetweenDates(hpId,
                                                                                        date.minusDays(1),
                                                                                        date.plusDays(1));

        TotalRecord totalRecord = totalRecords.stream().findFirst().orElse(null);

        if (totalRecord != null) {
            BalanceRecord balanceRecord = balanceRecordService.getById(totalRecord.getBalanceRecordId());
            KillRecord killRecord = killRecordService.getById(totalRecord.getKillRecordId());
            ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordService.getById(totalRecord.getExperienceSkillRecordId());
            PlayerClassRecord playerClassRecord = playerClassRecordService.getById(totalRecord.getPlayerClassRecordId());
            CollectionRecord collectionRecord = collectionRecordService.getById(totalRecord.getCollectionRecordId());

            return formatOverallInformationDto(playerUuid,
                                               hpId,
                                               balanceRecord,
                                               killRecord,
                                               experienceSkillRecord,
                                               playerClassRecord,
                                               collectionRecord);
//            return OverallInformationDto.builder()
//                    .playerUuid(playerUuid)
//                    .hpId(hpId)
//                    .balanceRecord(balanceRecord)
//                    .killRecord(killRecord)
//                    .experienceSkillRecord(experienceSkillRecord)
//                    .playerClassRecord(playerClassRecord)
//                    .collectionRecord(collectionRecord)
//                    .build();
        }

        return null;
    }

    public OverallInformationDto getDifferenceInformation(String playerUuid, String hpId, LocalDate dateFrom, LocalDate dateTo) {


        return null;
    }

    public OverallInformationDto saveOverallInformation(OverallInformationDto overallInformationDto) {

        if (overallInformationDto != null) {
            BalanceRecord balanceRecord = balanceRecordService.getById(overallInformationDto.getBalanceRecord().getId());
            KillRecord killRecord = killRecordService.getById(overallInformationDto.getKillRecord().getId());
            ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordService.getById(overallInformationDto.getExperienceSkillRecord().getId());
            PlayerClassRecord playerClassRecord = playerClassRecordService.getById(overallInformationDto.getPlayerClassRecord().getId());
            CollectionRecord collectionRecord = collectionRecordService.getById(overallInformationDto.getCollectionRecord().getId());

            if (balanceRecord != null && killRecord != null && experienceSkillRecord != null && playerClassRecord != null & collectionRecord != null) {

                balanceRecord = balanceRecordService.save(balanceRecord);
                killRecord = killRecordService.save(killRecord);
                experienceSkillRecord = experienceSkillRecordService.save(experienceSkillRecord);
                playerClassRecord = playerClassRecordService.save(playerClassRecord);
                collectionRecord = collectionRecordService.save(collectionRecord);

                //                if (balanceRecord != null && killRecord != null && experienceSkillRecord != null && playerClassRecord != null && collectionRecord != null) {
                totalRecordService.save(TotalRecord.builder()
                                                .playerUuid(overallInformationDto.getPlayerUuid())
                                                .hpId(overallInformationDto.getHpId())
                                                .balanceRecordId(balanceRecord.getId())
                                                .collectionRecordId(collectionRecord.getId())
                                                .experienceSkillRecordId(experienceSkillRecord.getId())
                                                .killRecordId(killRecord.getId())
                                                .playerClassRecordId(playerClassRecord.getId())
                                                .build());

                return OverallInformationDto.builder()
                        .playerUuid(overallInformationDto.getPlayerUuid())
                        .hpId(overallInformationDto.getHpId())
                        .balanceRecord(balanceRecord)
                        .killRecord(killRecord)
                        .experienceSkillRecord(experienceSkillRecord)
                        .playerClassRecord(playerClassRecord)
                        .collectionRecord(collectionRecord)
                        .build();
                //                }
            }

        }

        log.warn("recordService saveOverallInformation: something went wrong");
        return null;
    }

    public OverallInformationDto saveOverallInformation(ShortOverallInformationDto shortOverallInformationDto) {

        if (shortOverallInformationDto != null) {
            BalanceRecord balanceRecord = balanceRecordService.save(shortOverallInformationDto.getBalanceRecordInfoDto());
            KillRecord killRecord = killRecordService.save(shortOverallInformationDto.getKillRecordInfoDto());
            ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordService.save(shortOverallInformationDto.getExperienceSkillRecordInfoDto());
            PlayerClassRecord playerClassRecord = playerClassRecordService.save(shortOverallInformationDto.getPlayerClassRecordInfoDto());
            CollectionRecord collectionRecord = collectionRecordService.save(shortOverallInformationDto.getCollectionRecordInfoDto());

            //            if (balanceRecord != null && killRecord != null && experienceSkillRecord != null && playerClassRecord != null && collectionRecord != null) {
            totalRecordService.save(TotalRecord.builder()
                                            .playerUuid(shortOverallInformationDto.getPlayerUuid())
                                            .hpId(shortOverallInformationDto.getProfileId())
                                            .balanceRecordId(balanceRecord.getId())
                                            .collectionRecordId(collectionRecord.getId())
                                            .experienceSkillRecordId(experienceSkillRecord.getId())
                                            .killRecordId(killRecord.getId())
                                            .playerClassRecordId(playerClassRecord.getId())
                                            .build());

            return OverallInformationDto.builder()
                    .playerUuid(shortOverallInformationDto.getPlayerUuid())
                    .hpId(shortOverallInformationDto.getProfileId())
                    .balanceRecord(balanceRecord)
                    .killRecord(killRecord)
                    .experienceSkillRecord(experienceSkillRecord)
                    .playerClassRecord(playerClassRecord)
                    .collectionRecord(collectionRecord)
                    .build();
            //            }
        }

        log.warn("recordService saveOverallInformation: something went wrong");
        return null;
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
