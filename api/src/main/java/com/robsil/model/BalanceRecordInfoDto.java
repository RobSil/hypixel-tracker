package com.robsil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceRecordInfoDto {

    private String playerUuid;

    private BigDecimal coinsInPurse;

    private BigDecimal coinsInBank;
}
