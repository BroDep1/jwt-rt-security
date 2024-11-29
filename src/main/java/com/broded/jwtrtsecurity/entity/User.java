package com.broded.jwtrtsecurity.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid", unique = true, nullable = false)
    private UUID guid;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "refresh_token_id")
    private RefreshToken refreshToken;
}
