package com.example.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "social_accounts")
@Data
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String provider;

    @Column(name = "provider_id", length = 50)
    private String providerId;

    @Column(length = 150)
    private String email;

    @Column(length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
