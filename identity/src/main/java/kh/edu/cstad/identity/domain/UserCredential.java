package kh.edu.cstad.identity.domain;

import jakarta.persistence.*;

import java.util.Set;

public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "userCredential", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AccessToken> accessTokens;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
}
