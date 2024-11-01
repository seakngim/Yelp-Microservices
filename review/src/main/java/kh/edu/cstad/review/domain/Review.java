package kh.edu.cstad.review.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private Set<Photo> photos;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private Set<HelpfulVote> helpfulVotes;
}
