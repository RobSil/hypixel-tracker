package com.robsil.service;

import com.robsil.model.PlayerCreationDto;
import com.robsil.util.NicknameUtil;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@NoArgsConstructor
public class InitService {

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
    private NicknameUtil nicknameUtil;

    public void init() {
        initPlayers();
        // Profiles will be saved automatically.
    }

    public void initPlayers() {
        playerService.create(new PlayerCreationDto("wailas"));
        playerService.create(new PlayerCreationDto("ezvacss"));
    }

    public void deleteAllPlayers() {
        playerService.deleteAll();
    }

    public void deleteAllProfiles() {
        profileService.deleteAll();
    }
}
