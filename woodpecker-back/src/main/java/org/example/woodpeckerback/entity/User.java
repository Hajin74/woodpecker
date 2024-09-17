package org.example.woodpeckerback.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kakaoId;

    private String username;

    @Builder
    public User(Long id, Long kakaoId, String username) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.username = username;
    }

}
