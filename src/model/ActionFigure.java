package model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ActionFigure extends Product {

    private String dimensions;
    private String category;

    public ActionFigure(String title, String description, String dimensions, String category) {
        super(title, description);
        this.dimensions = dimensions;
        this.category = category;
    }

    public ActionFigure(String title, String description, String manufacturer, Warehouse warehouse, String dimensions, String category) {
        super(title, description, manufacturer, warehouse);
        this.dimensions = dimensions;
        this.category = category;
    }

    @Override
    public ProductType getProductType(){
        return ProductType.ACTIONFIGURE;
    }
}
