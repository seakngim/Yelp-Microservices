package kh.edu.cstad.user_profile.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Collections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String collectionName;

    @ManyToOne
    @JoinColumn(name = "profile_info_id")
    private ProfileInfo profileInfo;
}
