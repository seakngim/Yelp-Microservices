package kh.edu.cstad.identity.domain;

import jakarta.persistence.*;

public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserCredential userCredential;
}
