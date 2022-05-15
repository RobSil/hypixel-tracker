package com.robsil.service;

import com.robsil.data.domain.record.TotalRecord;
import com.robsil.data.repo.TotalRecordRepository;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
@NoArgsConstructor
public class TotalRecordService {

    @Autowired
    private TotalRecordRepository totalRecordRepository;

    public TotalRecord getEntityById(String id) {
        TotalRecord totalRecord = totalRecordRepository.findById(id).orElse(null);

        if (totalRecord != null) {
            return totalRecord;
        } else {
            log.warn("totalRecord: totalRecord is null");
            return null;
        }
    }

//    public List<TotalRecord> getAllByHpIdAndBetweenDates(String hpId, LocalDateTime dateFrom, LocalDateTime dateTo) {
//        return totalRecordRepository.findAllByHpIdAndCreatedDateBetween(hpId, dateFrom, dateTo);
//    }
    public List<TotalRecord> getAllByHpIdAndBetweenDates(String hpId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return totalRecordRepository.findAllByHpIdAndCreatedDateBetween(hpId, dateFrom, dateTo);
    }

    public List<TotalRecord> getAllByProfileId(String hpId) {
        return totalRecordRepository.findAllByHpId(hpId);
    }

    public TotalRecord save(TotalRecord totalRecord) {
        return totalRecordRepository.save(totalRecord);
    }

    public void deleteById(String id) {
        totalRecordRepository.deleteById(id);
    }

    public void deleteAll() {
        totalRecordRepository.deleteAll();
    }
}
