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
public class TradingCards extends Product {
    private String type;
    private String collection;
    private int quantityInPack;

    public TradingCards(String title, String description, String manufacturer, Warehouse warehouse, String type, String collection, int quantityInPack) {
        super(title, description, manufacturer, warehouse);
        this.type = type;
        this.collection = collection;
        this.quantityInPack = quantityInPack;
    }

    @Override
    public ProductType getProductType(){
        return ProductType.TRADINGCARDS;
    }
}
