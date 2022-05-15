package com.robsil.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerClassRecordInfoDto {

    private String playerUuid;

    private String hpId;

    private List<PlayerClass> playerClasses;
}
