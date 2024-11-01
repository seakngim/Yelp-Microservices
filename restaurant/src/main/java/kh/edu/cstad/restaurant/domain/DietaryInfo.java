package kh.edu.cstad.restaurant.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class DietaryInfo {
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean nutFree;
}
