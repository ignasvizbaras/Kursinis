package model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyChain extends Product {
    private String material;

    public KeyChain(String title, String description, String manufacturer, Warehouse warehouse, String material) {
        super(title, description, manufacturer, warehouse);
        this.material = material;
    }

    @Override
    public ProductType getProductType(){
        return ProductType.KEYCHAIN;
    }
}
