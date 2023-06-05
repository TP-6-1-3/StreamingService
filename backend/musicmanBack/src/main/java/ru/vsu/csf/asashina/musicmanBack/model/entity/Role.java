package ru.vsu.csf.asashina.musicmanBack.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "role")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false, unique = true)
    private String name;
}
