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

    private String playerId;

    private String profileId;

    private List<PlayerClass> playerClasses;
}
