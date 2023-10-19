package model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Other extends Product {
    private double weight;
    private String rarity;

    public Other(String title, String description, String manufacturer, Warehouse warehouse, double weight, String rarity) {
        super(title, description, manufacturer, warehouse);
        this.weight = weight;
        this.rarity = rarity;
    }

    @Override
    public ProductType getProductType(){
        return ProductType.OTHER;
    }
}
