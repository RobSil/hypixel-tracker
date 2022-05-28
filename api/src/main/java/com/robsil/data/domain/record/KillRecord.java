package com.robsil.data.domain.record;

import com.robsil.model.Kill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "KillRecord")
@Deprecated
public class KillRecord {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    private String playerId;

    private String profileId;

    private List<Kill> kills;
}
