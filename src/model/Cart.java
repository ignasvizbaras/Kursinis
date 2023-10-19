package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateCreated;

//    @OneToMany(mappedBy = "", cascade = CascadeType.ALL)
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<Comment> comments;

    @OneToMany(mappedBy = "", cascade = CascadeType.MERGE, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> itemsInCart;

    public Cart(LocalDate dateCreated, List<Product> itemsInCart) {
        this.dateCreated = dateCreated;
        this.itemsInCart = itemsInCart;
    }

//    public Cart(LocalDate dateCreated, List<Comment> comments, List<Product> itemsInCart) {
//        this.dateCreated = dateCreated;
//        this.comments = comments;
//        this.itemsInCart = itemsInCart;
//    }
}
