package ru.vsu.csf.asashina.musicmanBack.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Table(name = "user_info")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private Boolean isVerified = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH })
    @JoinTable(name = "user_song",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")})
    private Set<Song> songs;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH })
    @JoinTable(name = "friend",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")})
    private Set<User> friends;

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public void deleteFriend(User friend) {
        friends.remove(friend);
    }
}
