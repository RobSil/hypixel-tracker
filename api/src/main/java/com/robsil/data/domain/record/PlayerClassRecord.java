package com.robsil.data.domain.record;

import com.robsil.model.PlayerClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "PlayerClassesRecord")
public class PlayerClassRecord {

    @Id
    private String id;

    @CreatedDate
    private LocalDate createdDate;

    private String playerId;

    private String profileId;

    private List<PlayerClass> playerClasses;
}
