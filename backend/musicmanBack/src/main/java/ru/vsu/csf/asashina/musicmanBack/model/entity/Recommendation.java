package ru.vsu.csf.asashina.musicmanBack.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "recommendation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID recommendationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(nullable = false)
    private Instant till;
}
