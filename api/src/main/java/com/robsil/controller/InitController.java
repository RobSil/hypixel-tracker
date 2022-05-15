package com.robsil.controller;

import com.robsil.service.*;
import com.robsil.util.ExperienceUtil;
import com.robsil.util.NicknameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

    @Autowired
    private ExperienceUtil experienceUtil;

    @Autowired
    private InitService initService;

    @GetMapping("/api/v1/init")
    public void init() {
        initService.init();
    }

    @GetMapping("/api/v1/init/players")
    public void initPlayers() {
        initService.initPlayers();
    }

    @DeleteMapping("/api/v1/init/players")
    public void deleteAllPlayers() {
        initService.deleteAllPlayers();
    }

    @DeleteMapping("/api/v1/init/profiles")
    public void deleteAllProfiles() {
        initService.deleteAllProfiles();
    }

    @GetMapping("/api/v1/test/xp")
    public int testXpLeveling(@RequestParam int xp) {
        return experienceUtil.universalXpToLevel(xp);
    }
}
