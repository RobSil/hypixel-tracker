package com.robsil.controller;

import com.robsil.data.domain.Profile;
import com.robsil.model.OverallInformationDto;
import com.robsil.service.ProfileService;
import com.robsil.service.RecordService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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

//    @GetMapping("/api/v1/profiles/currentOverallInformation")
//    public OverallInformationDto getCurrentOverallInformation(@RequestParam String playerUuid,
//                                                              @RequestParam String hpId) {
//        return recordService.getOverallInformation(playerUuid, hpId);
//    }

    @GetMapping("/api/v1/profiles/overallInformation")
    public OverallInformationDto getOverallInformationByDate(@RequestParam String playerUuid,
                                                             @RequestParam String hpId,
                                                             @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        return recordService.handleOverallInformation(playerUuid, hpId, date);
    }
}
