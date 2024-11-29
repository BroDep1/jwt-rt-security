package com.broded.jwtrtsecurity.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "ip", nullable = false)
    private String ip;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}