package ru.tinkoff.edu.java.scrapper.domain.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@IdClass(TrackPrimaryKey.class)
@Table(name = "tracking")
public class TrackEntity {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Id
    @Column(name = "link_id")
    private Long linkId;
}
