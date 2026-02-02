package com.uce.models;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "isbn", length = 16)
    private String isbn;
    @Column(length = 128)
    private String title;
    private int value;

    /*
    @ManyToMany
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "book_isbn"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @ToString.Exclude
    private List<Author> authors;
    */
    @OneToOne(mappedBy = "book")
    private Inventory inventory;
}