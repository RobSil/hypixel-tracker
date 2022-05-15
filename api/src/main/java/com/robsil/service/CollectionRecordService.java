package com.robsil.service;

import com.robsil.data.domain.record.CollectionRecord;
import com.robsil.data.repo.CollectionRecordRepository;
import com.robsil.model.CollectionRecordInfoDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@NoArgsConstructor
public class CollectionRecordService {

    @Autowired
    private CollectionRecordRepository collectionRecordRepository;

    public CollectionRecord getById(String id) {
        CollectionRecord collectionRecord = collectionRecordRepository.findById(id).orElse(null);

        if (collectionRecord != null) {
            return collectionRecord;
        } else {
            log.warn("collectionRecord: can't find such a collectionRecord.");
            return null;
        }
    }

    public CollectionRecord getLast(String playerId, String profileId) {
        return collectionRecordRepository.findFirstByPlayerIdAndProfileIdOrderByCreatedDateDesc(playerId, profileId).orElse(null);
    }

    public CollectionRecord save(CollectionRecord collectionRecord) {
        return collectionRecordRepository.save(collectionRecord);
    }

    public CollectionRecord save(CollectionRecordInfoDto collectionRecordInfoDto) {
        return save(CollectionRecord.builder()
                            .playerId(collectionRecordInfoDto.getPlayerUuid())
                            .profileId(collectionRecordInfoDto.getHpId())
                            .resources(collectionRecordInfoDto.getResources())
                            .build());
    }

}
