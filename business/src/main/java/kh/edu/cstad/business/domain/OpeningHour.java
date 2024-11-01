package kh.edu.cstad.business.domain;

import jakarta.persistence.Column;

public class OpeningHour {
    @Column (unique = true, nullable = false)
    private int day;
    @Column(unique = true, nullable = false)
    private String start;
    @Column (unique = true, nullable = false)
    private String end;
    @Column (unique = true, nullable = false)
    private boolean isOvernight;
}
