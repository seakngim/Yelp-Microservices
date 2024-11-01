package kh.edu.cstad.service.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "service")
    private List<TimeSlot> timeSlots;

    @ManyToMany
    private List<Staff> staffMembers;
}
