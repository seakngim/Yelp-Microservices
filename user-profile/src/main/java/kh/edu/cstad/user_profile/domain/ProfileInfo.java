package kh.edu.cstad.user_profile.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class ProfileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private LocalDate birthdate;

    @OneToOne(mappedBy = "profileInfo")
    private EliteStatus eliteStatus;
}
