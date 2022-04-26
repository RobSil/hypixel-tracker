package com.robsil.controller;

import com.robsil.data.domain.Profile;
import com.robsil.model.OverallInformationDto;
import com.robsil.service.ProfileService;
import com.robsil.service.RecordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    private final RecordService recordService;

    @GetMapping("/api/v1/profiles/byPlayerId")
    public List<Profile> getAllByPlayerId(@RequestParam String playerId) {
        return profileService.getAllByPlayerId(playerId);
    }

    @GetMapping("/api/v1/profiles/overallInformation")
    public OverallInformationDto getCurrentOverallInformation(@RequestParam String playerId,
                                                              @RequestParam String profileId) {
        return recordService.getCurrentOverallInformation(playerId, profileId);
    }
}
