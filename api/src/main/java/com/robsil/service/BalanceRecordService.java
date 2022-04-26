package com.robsil.service;

import com.robsil.data.domain.record.BalanceRecord;
import com.robsil.data.repo.BalanceRecordRepository;
import com.robsil.model.BalanceRecordInfoDto;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@NoArgsConstructor
public class BalanceRecordService {

    @Autowired
    private BalanceRecordRepository balanceRecordRepository;

    public BalanceRecord getById(String id) {
        BalanceRecord balanceRecord = balanceRecordRepository.findById(id).orElse(null);

        if (balanceRecord != null) {
            return balanceRecord;
        } else {
            log.warn("balanceRecord: can't find such a balanceRecord.");
            return null;
        }
    }

    public BalanceRecord getLast(String playerId) {
        return balanceRecordRepository.findFirstByPlayerIdOrderByCreatedDateDesc(playerId).orElse(null);
    }

    public BalanceRecord save(BalanceRecord balanceRecord) {
        return balanceRecordRepository.save(balanceRecord);
    }

    public BalanceRecord save(BalanceRecordInfoDto balanceRecordInfoDto) {
        return save(BalanceRecord.builder()
                            .coinsInBank(balanceRecordInfoDto.getCoinsInBank())
                            .coinsInPurse(balanceRecordInfoDto.getCoinsInPurse())
                            .playerId(balanceRecordInfoDto.getPlayerId())
                            .build());
    }
}
