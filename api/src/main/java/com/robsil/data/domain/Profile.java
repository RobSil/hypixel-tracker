package com.robsil.data.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Builder
@Getter
@Setter
@ToString
//@EqualsAndHashCode(exclude = { "createdDate", "lastModifiedDate" , "id"})
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Profile")
public class Profile {

    private String id;

    private String hpId;

    private String playerId;

    private String playerUuid;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Profile profile = (Profile) o;

        return getHpId().equals(profile.getHpId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getHpId(),
                            getPlayerId(),
                            getTitle());
    }
}
