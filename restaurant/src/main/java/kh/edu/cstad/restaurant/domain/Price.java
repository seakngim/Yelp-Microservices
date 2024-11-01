package kh.edu.cstad.restaurant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Embeddable
public class Price {
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency;
}
