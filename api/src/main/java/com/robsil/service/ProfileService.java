package com.robsil.service;

import com.robsil.data.domain.Player;
import com.robsil.data.domain.Profile;
import com.robsil.data.repo.ProfileRepository;
import com.robsil.model.*;
import com.robsil.util.ApiUtil;
import com.robsil.util.NicknameUtil;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
@Log4j2
@NoArgsConstructor
public class ProfileService {

    @Value("${hypixel.apiUrl}")
    private String hypixelApiUrl;

    @Value("${hypixel.apiKey}")
    private String hypixelApiKey;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private NicknameUtil nicknameUtil;

    @Autowired
    private ApiUtil apiUtil;

    public List<Profile> getAll() {
        return profileRepository.findAll();
    }

    public List<Profile> getAllByPlayerId(String playerId) {
        return profileRepository.findAllByPlayerId(playerId);
    }

    public Profile getById(String id) {
        Profile profile = profileRepository.findById(id).orElse(null);

        if (profile != null) {
            return profile;
        } else {
            log.warn("ProfileService: can't find profile with such an ID: " + id);

            return null;
        }
    }

    public Profile getByHpId(String hpId) {
        return profileRepository.findByHpId(hpId).orElse(null);
    }

    public Profile getByHpIdAndPlayerUuid(String hpId, String playerUuid) {
        return profileRepository.findByHpIdAndPlayerUuid(hpId, playerUuid).orElse(null);
    }


    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    public List<Profile> saveAll(Iterable<Profile> profile) {
        return profileRepository.saveAll(profile);
    }

    public void deleteById(String id) {
        profileRepository.deleteById(id);
    }

    public void deleteAllByPlayerId(String playerId) {
        profileRepository.deleteAllByPlayerId(playerId);
    }

    public List<Profile> updatePlayerProfiles(String playerId) {

        Player player = playerService.getById(playerId);

        if (player == null) {
            log.warn("updatePlayerProfiles: no such a player.");
            return null;
        }

        List<Profile> currentProfiles = getAllByPlayerId(player.getId());

        List<Profile> hypixelProfiles = fetchProfiles(player.getId());

        if (currentProfiles.size() == hypixelProfiles.size() && currentProfiles.containsAll(hypixelProfiles) && hypixelProfiles.containsAll(currentProfiles)) {
            return currentProfiles;
        } else {
            deleteAllByPlayerId(playerId);
            List<Profile> savedHypixelProfiles = saveAll(hypixelProfiles);

            return savedHypixelProfiles;
        }
    }

    public List<Profile> fetchProfiles(String playerId) {

        Player player = playerService.getById(playerId);

        if (player == null) {
            return null;
        }

        String uuid = player.getUuid();

        List<Profile> profiles = new ArrayList<>();

        HttpResponse<String> response = apiUtil.sendRequest(URI.create(hypixelApiUrl + "/player?key=" + hypixelApiKey + "&uuid=" + uuid));

        String body = response.body();

        Map<String, Object> preProfilesMap = new JSONObject(body).getJSONObject("player").getJSONObject("stats").getJSONObject("SkyBlock").getJSONObject("profiles").toMap();

        for (Entry<String, Object> entry : preProfilesMap.entrySet()) {
            //                Profile profile = Profile.builder().hypixelId(entry.getKey()).title((entry.getValue()).hypixelId(uuid).build();
            Map<String, Object> idToNameProfileMap = (Map<String, Object>) entry.getValue();

            String profileId = null;
            String title = null;

            for (Entry<String, Object> internalEntry : idToNameProfileMap.entrySet()) {
                String key = internalEntry.getKey();

                if (key.equalsIgnoreCase("profile_id")) {
                    profileId = (String) internalEntry.getValue();
                } else if (key.equalsIgnoreCase("cute_name")) {
                    title = (String) internalEntry.getValue();
                }
            }

            if (profileId != null && title != null) {
                profiles.add(Profile.builder().hpId(profileId).title(title).playerId(playerId).playerUuid(uuid).build());
            }

        }


        return profiles;
    }

    public ShortOverallInformationDto collectInformation(String hpId, String playerUuid) {

        String url = hypixelApiUrl + "/skyblock/profile?key=" + hypixelApiKey + "&profile=" + hpId;

        HttpResponse<String> response = apiUtil.sendRequest(URI.create(url));

        String body = response.body();

        JSONObject bodyJson = new JSONObject(body);

        JSONObject playerProfileInfo = bodyJson.getJSONObject("profile").getJSONObject("members").getJSONObject(nicknameUtil.trimNickname(playerUuid));

//        if (!checkProperties(bodyJson, playerProfileInfo)) {
//            return null;
//        }

        JSONObject statsInfo = playerProfileInfo.isNull("stats") ? new JSONObject() : playerProfileInfo.getJSONObject("stats");
        JSONObject dungeonsInfo = playerProfileInfo.isNull("dungeons") ? new JSONObject() : playerProfileInfo.getJSONObject("dungeons");
        JSONObject collectionInfo = playerProfileInfo.isNull("collection") ? new JSONObject() : playerProfileInfo.getJSONObject("collection");
        JSONObject bankInfo = bodyJson.getJSONObject("profile").isNull("banking") ? new JSONObject() : bodyJson.getJSONObject("profile").getJSONObject("banking");

        JSONObject playerClassesInfo = dungeonsInfo.isNull("player_classes") ? new JSONObject() : dungeonsInfo.getJSONObject("player_classes");

        List<ExperienceSkill> experienceSkills = new ArrayList<>();
        List<Kill> killStats = new ArrayList<>();
        List<PlayerClass> playerClassesStats = new ArrayList<>();
        List<Resource> collectionStats = new ArrayList<>();

        int deathCount = -1;
        BigDecimal coinsInPurse = new BigDecimal(-1);
        BigDecimal bankBalance = new BigDecimal(-1);

        for (Entry<String, Object> entry : playerProfileInfo.toMap().entrySet()) {

            String key = entry.getKey();

            if (key.startsWith("experience_skill_")) {
                experienceSkills.add(new ExperienceSkill(key,
                                                         key.substring("experience_skill_".length()),
                                                         (BigDecimal) entry.getValue()));
            }
            if (key.startsWith("death_count")) {
                deathCount = (Integer) entry.getValue();
            }
            if (key.startsWith("coin_purse")) {
                coinsInPurse = (BigDecimal) entry.getValue();
            }
        }

        for (Entry<String, Object> entry : statsInfo.toMap().entrySet()) {
            String key = entry.getKey();

            if (key.startsWith("kills_")) {
                killStats.add(new Kill(key,
                                       key.substring("kills_".length()),
                                       (BigDecimal) entry.getValue()));
            }
        }

        for (Entry<String, Object> entry : playerClassesInfo.toMap().entrySet()) {

            String className = entry.getKey();

            Map<String, Object> valueMap = (Map<String, Object>) entry.getValue();

            for (Entry<String, Object> valueEntry : valueMap.entrySet()) {
                playerClassesStats.add(new PlayerClass(className,
                                                       (BigDecimal) valueEntry.getValue()));
            }
        }

        for (Entry<String, Object> entry : bankInfo.toMap().entrySet()) {

            String key = entry.getKey();

            if (key.startsWith("balance")) {
                bankBalance = (BigDecimal) entry.getValue();
                break;
            }
        }

        collectionStats = collectionInfo.toMap().entrySet().stream()
                .map(entry -> new Resource(entry.getKey(),
                                           (Integer) entry.getValue()))
                .collect(Collectors.toList());

        Map<String, Object> statsMap = playerProfileInfo.getJSONObject("stats").toMap();


        CollectionRecordInfoDto collectionRecordInfoDto = new CollectionRecordInfoDto(playerUuid,
                                                                                      hpId,
                                                                                      collectionStats);
        ExperienceSkillRecordInfoDto experienceSkillRecordInfoDto = new ExperienceSkillRecordInfoDto(playerUuid,
                                                                                                     hpId,
                                                                                                     experienceSkills);
        KillRecordInfoDto killRecordInfoDto = new KillRecordInfoDto(playerUuid,
                                                                    hpId,
                                                                    killStats);
        PlayerClassRecordInfoDto playerClassRecordInfoDto = new PlayerClassRecordInfoDto(playerUuid,
                                                                                         hpId,
                                                                                         playerClassesStats);
        BalanceRecordInfoDto balanceRecordInfoDto = new BalanceRecordInfoDto(playerUuid,
                                                                             coinsInPurse,
                                                                             bankBalance);

        ShortOverallInformationDto shortOverallInformationDto =
                new ShortOverallInformationDto(playerUuid,
                                               hpId,
                                               collectionRecordInfoDto,
                                               experienceSkillRecordInfoDto,
                                               playerClassRecordInfoDto,
                                               killRecordInfoDto,
                                               balanceRecordInfoDto);


        log.info("scheduler: successfully collected information!");

        return shortOverallInformationDto;
    }

    private boolean checkProperties(JSONObject bodyJson, JSONObject playerProfileInfo) {

        if (playerProfileInfo.isNull("stats")) {
            log.warn("propertyCheck: stats is null.");
            return false;
        }
        if (playerProfileInfo.isNull("dungeons")) {
            log.warn("propertyCheck: dungeons is null.");
            return false;
        }
        if (playerProfileInfo.isNull("collection")) {
            log.warn("propertyCheck: collections is null.");
            return false;
        }
        if (bodyJson.getJSONObject("profile").isNull("banking")) {
            log.warn("propertyCheck: banking is null.");
            return false;
        }
        return true;
    }

    public void deleteAll() {
        profileRepository.deleteAll();
    }
}
