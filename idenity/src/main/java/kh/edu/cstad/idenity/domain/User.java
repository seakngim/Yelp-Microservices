package kh.edu.cstad.idenity.domain;


import jakarta.persistence.*;
import kh.edu.cstad.idenity.config.jpa.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(nullable = false, unique = true, length = 64)
    private String username;

    @Column(nullable = false, unique = true, length = 256)
    private String email;

    @Column(nullable = false, length = 256)
    private String password;

    @Column(unique = true, length = 64)
    private String facebookId;

    @Column(unique = true, length = 64)
    private String googleId;

    @Column(unique = true, length = 64)
    private String xId;

    @Column(unique = true, length = 64)
    private String telegramId;

    @Column(unique = true, length = 64)
    private String appleId;

    @Column(nullable = false, columnDefinition = "Text")
    private String familyName;

    @Column(nullable = false, columnDefinition = "Text")
    private String givenName;

    @Column(unique = true)
    private String phoneNumber;

    private String gender;

    private LocalDate dob;

    @Column(length = 256)
    private String profileImage;

    @Column(length = 256)
    private String coverImage;

    @Column(length = 256)
    private String ipAddress;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean accountNonExpired;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean accountNonLocked;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean credentialsNonExpired;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isEnabled;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean emailVerified;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<UserAuthority> userAuthorities;

}
