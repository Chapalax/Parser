package ru.tinkoff.edu.java.scrapper.domain.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"tracks"})
@ToString(exclude = {"tracks"})
@NoArgsConstructor
@Table(name = "links")
public class LinkEntity {
    @Id
    @SequenceGenerator(name = "link_seq", sequenceName = "link_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "path", nullable = false, unique = true)
    private String path;

    @Column(name = "last_activity", nullable = false)
    private OffsetDateTime lastActivity = OffsetDateTime.now();

    @Column(name = "action_count", nullable = false)
    private Integer actionCount = 0;

    @Column(name = "checked_at", nullable = false)
    private OffsetDateTime checkedAt = OffsetDateTime.now();

    @OneToMany(mappedBy = "linkId", orphanRemoval = true)
    private List<TrackEntity> tracks = new ArrayList<>();
}
