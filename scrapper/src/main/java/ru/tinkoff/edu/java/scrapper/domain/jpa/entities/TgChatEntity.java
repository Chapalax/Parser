package ru.tinkoff.edu.java.scrapper.domain.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "chats")
public class TgChatEntity {
    @Id
    @Column(name = "id")
    private Long id;
}
