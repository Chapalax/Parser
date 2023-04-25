package ru.tinkoff.edu.java.scrapper.domain.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"tracks"})
@ToString(exclude = {"tracks"})
@NoArgsConstructor
@Table(name = "chats")
public class TgChatEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "chatId", orphanRemoval = true)
    private List<TrackEntity> tracks = new ArrayList<>();
}
