package kh.edu.cstad.user_profile.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Preferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean receiveNotifications;
    private String themePreference;

    @OneToOne
    @JoinColumn(name = "profile_info_id")
    private ProfileInfo profileInfo;
}
