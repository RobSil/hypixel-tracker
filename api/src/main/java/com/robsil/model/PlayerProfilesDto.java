package com.robsil.model;

import com.robsil.data.domain.Player;
import com.robsil.data.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerProfilesDto {

    Player player;

    List<Profile> profiles;
}
