package com.robsil.data.event;

import com.robsil.data.domain.Player;
import com.robsil.data.domain.Profile;
import com.robsil.service.PlayerService;
import com.robsil.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerHandler extends AbstractMongoEventListener<Player> {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ProfileService profileService;

    @Override
    public void onAfterSave(AfterSaveEvent<Player> event) {
        Player savedPlayer = event.getSource();

        List<Profile> playerProfiles = profileService.getAllByPlayerId(savedPlayer.getId());

        // or maybe update for all?
        if (playerProfiles.size() == 0) {
            profileService.updatePlayerProfiles(savedPlayer.getId());
        }
    }

}
