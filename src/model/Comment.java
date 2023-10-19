package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String commentTitle;
    private String commentBody;
    private LocalDate dateCreated;

    @ManyToOne
    private Cart cart;

    public Comment(String commentTitle, String commentBody, LocalDate dateCreated, Cart cart) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = dateCreated;
        this.cart = cart;
    }
}
