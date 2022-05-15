package com.robsil.service;

import com.robsil.data.domain.Player;
import com.robsil.data.domain.Profile;
import com.robsil.model.ShortOverallInformationDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.standard.TemporalAccessorParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.List;

@Service
@Log4j2
@NoArgsConstructor
public class InformationCollectionService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ProfileService profileService;

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
    private RecordService recordService;

    private void updateAllPlayersProfiles() {

        List<Player> allPlayers = playerService.getAll();

        allPlayers.forEach(player -> profileService.updatePlayerProfiles(player.getId()));

        //        Map<Player, List<Profile>> playersToProfilesMap = new HashMap<>();
        //
        //        playersToProfilesMap = allPlayers.stream()
        //                .map(player -> new SimpleEntry(player,
        //                                               profileService.getAllByPlayerId(player.getId())))
        //                .collect(Collectors.toMap((entry) -> (Player) entry.getKey(),
        //                                          (entry) -> (List<Profile>) entry.getValue()));
        //
        //        for (Entry<Player, List<Profile>> entry : playersToProfilesMap.entrySet()) {
        //            Player player = entry.getKey();
        //
        //            List<Profile> currentProfiles = entry.getValue();
        //
        //            List<Profile> actualProfiles = profileService.fetchProfiles(player.getId());
        //
        //            if (currentProfiles.size() == actualProfiles.size() && currentProfiles.containsAll(actualProfiles) && actualProfiles.containsAll(currentProfiles)) {
        //                continue;
        //            } else {
        //                profileService.deleteAllByPlayerId(player.getId());
        //                profileService.saveAll(actualProfiles);
        //            }
        //        }

    }

    //rework, that we do not depending on how many members on profile.
    private void updateAllPlayersAllProfilesInformation() {

        List<Profile> allProfiles = profileService.getAll();

        for (Profile profile : allProfiles) {

            ShortOverallInformationDto shortOverallInformationDto = profileService.collectInformation(profile.getHpId(),
                                                                                                      profile.getPlayerUuid());

            recordService.saveOverallInformation(shortOverallInformationDto);
        }
    }

    //    @Scheduled(cron = "0 0 0 * * *")
        @Scheduled(fixedDelay = 5000L)
    private void updateGlobalInformation() {
        updateAllPlayersProfiles();

        updateAllPlayersAllProfilesInformation();
    }
}
