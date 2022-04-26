package com.robsil.service;

import com.robsil.data.domain.record.KillRecord;
import com.robsil.data.repo.KillRecordRepository;
import com.robsil.model.KillRecordInfoDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@NoArgsConstructor
public class KillRecordService {

    @Autowired
    private KillRecordRepository killRecordRepository;

    public KillRecord getById(String id) {
        KillRecord killRecord = killRecordRepository.findById(id).orElse(null);

        if (killRecord != null) {
            return killRecord;
        } else {
            log.warn("killRecord: can't find such a killRecord.");
            return null;
        }
    }

    public KillRecord getLast(String playerId, String profileId) {
        return killRecordRepository.findFirstByPlayerIdOrderByCreatedDateDesc(playerId, profileId).orElse(null);
    }

    public KillRecord save(KillRecord killRecord) {
        return killRecordRepository.save(killRecord);
    }

    public KillRecord save(KillRecordInfoDto killRecordInfoDto) {
        return save(KillRecord.builder()
                            .playerId(killRecordInfoDto.getPlayerId())
                            .profileId(killRecordInfoDto.getProfileId())
                            .kills(killRecordInfoDto.getKills())
                            .build());
    }
}
