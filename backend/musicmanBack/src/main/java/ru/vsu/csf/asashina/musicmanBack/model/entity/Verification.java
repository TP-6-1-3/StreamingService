package ru.vsu.csf.asashina.musicmanBack.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "verification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID verificationId;

    @Column(nullable = false)
    private Instant validTill;

    @Column(nullable = false, unique = true)
    private UUID code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
