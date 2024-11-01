package kh.edu.cstad.review.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
}
