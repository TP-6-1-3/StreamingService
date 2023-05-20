package ru.vsu.csf.asashina.musicmanBack.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "statistic")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID statisticId;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE })
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(nullable = false)
    private Long amount;

    public void incrementAmount() {
        amount++;
    }
}
