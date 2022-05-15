package com.robsil.controller;

import com.robsil.data.domain.Player;
import com.robsil.data.domain.Profile;
import com.robsil.model.PlayerCreationDto;
import com.robsil.model.PlayerProfilesDto;
import com.robsil.model.ShortOverallInformationDto;
import com.robsil.service.PlayerService;
import com.robsil.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;
    private final ProfileService profileService;

    @GetMapping("/api/v1/players")
    public List<Player> getAll() {
        return playerService.getAll();
    }

    @GetMapping("/api/v1/players/allWithProfiles")
    public List<PlayerProfilesDto> getAllWithProfiles() {
        return playerService.getAllWithProfiles();
    }

    @GetMapping("/api/v1/players/withProfiles")
    public PlayerProfilesDto getWithProfiles(@RequestParam String playerId) {
        return playerService.getWithProfiles(playerId);
    }

    @GetMapping("/api/v1/players/byId")
    public Player getById(@RequestParam String id) {
        return playerService.getById(id);
    }

    @GetMapping("/api/v1/players/byUuid")
    public Player getByUuid(@RequestParam String uuid) {
        return playerService.getByUuid(uuid);
    }

    @GetMapping("/api/v1/players/profiles")
    public List<Profile> getAllProfiles(@RequestParam String playerId) {
        return profileService.getAllByPlayerId(playerId);
    }

    @PostMapping("/api/v1/players/save")
    public Player save(@RequestBody PlayerCreationDto playerCreationDto) {
        return playerService.create(playerCreationDto);
    }

    @PostMapping("/api/v1/players/updateProfiles")
    public List<Profile> updatePlayerProfiles(@RequestParam String playerId) {
        return playerService.updatePlayerProfiles(playerId);
    }

    @GetMapping("/api/v1/players/hypixelProfiles")
    public List<Profile> fetchHypixelProfiles(@RequestParam String playerId) {
        return profileService.fetchProfiles(playerId);
    }

    @GetMapping("/api/v1/players/collectInformation")
    public ShortOverallInformationDto collectInformation(@RequestParam String profileId, @RequestParam String playerId) {
        return profileService.collectInformation(profileId, playerId);
    }

    @DeleteMapping("/api/v1/players/byId")
    public void deleteById(@RequestParam String id) {
        playerService.deleteById(id);
    }

}
