package com.robsil.service;

import com.robsil.data.domain.record.PlayerClassRecord;
import com.robsil.data.repo.PlayerClassRecordRepository;
import com.robsil.model.PlayerClassRecordInfoDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@NoArgsConstructor
public class PlayerClassRecordService {

    @Autowired
    private PlayerClassRecordRepository playerClassRecordRepository;

    public PlayerClassRecord getById(String id) {
        PlayerClassRecord playerClassRecord = playerClassRecordRepository.findById(id).orElse(null);

        if (playerClassRecord != null) {
            return playerClassRecord;
        } else {
            log.warn("playerClassRecord: can't find such a playerClassRecord.");
            return null;
        }
    }

    public PlayerClassRecord getLast(String playerId, String profileId) {
        return playerClassRecordRepository.findFirstByPlayerIdOrderByCreatedDateDesc(playerId, profileId).orElse(null);
    }

    public PlayerClassRecord save(PlayerClassRecord playerClassRecord) {
        return playerClassRecordRepository.save(playerClassRecord);
    }

    public PlayerClassRecord save(PlayerClassRecordInfoDto playerClassRecordInfoDto) {
        return save(PlayerClassRecord.builder()
                            .playerId(playerClassRecordInfoDto.getPlayerId())
                            .profileId(playerClassRecordInfoDto.getProfileId())
                            .playerClasses(playerClassRecordInfoDto.getPlayerClasses())
                            .build());
    }
}
