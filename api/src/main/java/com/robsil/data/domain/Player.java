package com.robsil.data.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "Player")
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = { "createdDate", "lastModifiedDate" })
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

    @Id
    private String id;

    @Indexed(unique = true)
    private String uuid;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;

    @Version
    long version;

    private String nickname;
}
