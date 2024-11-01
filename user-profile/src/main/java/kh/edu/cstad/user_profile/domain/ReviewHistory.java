package kh.edu.cstad.user_profile.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ReviewHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reviewContent;
    private LocalDateTime reviewDate;

    @ManyToOne
    @JoinColumn(name = "profile_info_id")
    private ProfileInfo profileInfo;
}
