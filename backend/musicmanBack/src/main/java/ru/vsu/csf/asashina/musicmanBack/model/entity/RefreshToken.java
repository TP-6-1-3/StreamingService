package ru.vsu.csf.asashina.musicmanBack.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Table(name = "refresh_token")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {

    @Id
    private UUID token;

    @Column(nullable = false)
    private Instant validTill;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}