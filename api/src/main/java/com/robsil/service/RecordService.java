package com.robsil.service;

import com.robsil.data.domain.Player;
import com.robsil.data.domain.Profile;
import com.robsil.data.domain.record.*;
import com.robsil.model.OverallInformationDto;
import com.robsil.model.ShortOverallInformationDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public OverallInformationDto getCurrentOverallInformation(String playerId, String profileId) {

        Player player = playerService.getByUuid(playerId);
        Profile profile = profileService.getByHypixelId(profileId);

        log.info(player.getNickname());
        log.info(profile.getTitle());

        BalanceRecord balanceRecord = balanceRecordService.getLast(playerId);
        ExperienceSkillRecord experienceSkillRecord = experienceSkillRecordService.getLast(playerId, profileId);
        KillRecord killRecord = killRecordService.getLast(playerId, profileId);
        PlayerClassRecord playerClassRecord = playerClassRecordService.getLast(playerId, profileId);
        CollectionRecord collectionRecord = collectionRecordService.getLast(playerId, profileId);


        return OverallInformationDto.builder()
                .playerId(playerId)
                .profileId(profileId)
                .balanceRecord(balanceRecord)
                .experienceSkillRecord(experienceSkillRecord)
                .killRecord(killRecord)
                .playerClassRecord(playerClassRecord)
                .collectionRecord(collectionRecord)
                .build();
    }

    public OverallInformationDto getOverallInformation(String playerId, String profileId, LocalDate date) {



        return null;
    }

    public OverallInformationDto getDifferencedInformation(String playerId, String profileId, LocalDate dateFrom, LocalDate dateTo) {



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

                if (balanceRecord != null && killRecord != null && experienceSkillRecord != null && playerClassRecord != null && collectionRecord != null) {
                    return OverallInformationDto.builder()
                            .playerId(overallInformationDto.getPlayerId())
                            .profileId(overallInformationDto.getProfileId())
                            .balanceRecord(balanceRecord)
                            .killRecord(killRecord)
                            .experienceSkillRecord(experienceSkillRecord)
                            .playerClassRecord(playerClassRecord)
                            .collectionRecord(collectionRecord)
                            .build();
                }
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

            if (balanceRecord != null && killRecord != null && experienceSkillRecord != null && playerClassRecord != null && collectionRecord != null) {
                return OverallInformationDto.builder()
                        .playerId(shortOverallInformationDto.getPlayerId())
                        .profileId(shortOverallInformationDto.getProfileId())
                        .balanceRecord(balanceRecord)
                        .killRecord(killRecord)
                        .experienceSkillRecord(experienceSkillRecord)
                        .playerClassRecord(playerClassRecord)
                        .collectionRecord(collectionRecord)
                        .build();
            }
        }

        log.warn("recordService saveOverallInformation: something went wrong");
        return null;
    }
}
