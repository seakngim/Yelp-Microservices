package kh.edu.cstad.review.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "helpful_votes")
public class HelpfulVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private boolean isHelpful;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
}
