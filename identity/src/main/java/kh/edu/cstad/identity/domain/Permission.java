package kh.edu.cstad.identity.domain;

import jakarta.persistence.*;

import java.util.Set;

public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    private Set<UserCredential> users;
}
