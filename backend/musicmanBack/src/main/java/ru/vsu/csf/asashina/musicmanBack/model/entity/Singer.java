package ru.vsu.csf.asashina.musicmanBack.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "singer")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Singer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long singerId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "singer")
    private Set<Song> songs;
}
