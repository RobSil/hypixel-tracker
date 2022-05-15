package com.robsil.data.domain.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BalanceRecord")
public class BalanceRecord {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    private String playerUuid;

    private BigDecimal coinsInPurse;

    private BigDecimal coinsInBank;
}
