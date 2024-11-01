package kh.edu.cstad.user_profile.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class EliteStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isElite;
    private LocalDate membershipExpiry;

    @OneToOne
    @JoinColumn(name = "profile_info_id")
    private ProfileInfo profileInfo;
}
