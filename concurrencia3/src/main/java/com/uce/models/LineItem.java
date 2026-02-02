package com.uce.models;

import lombok.Data;
import lombok.ToString;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.TableGenerator;
import lombok.Builder;

@Data
@Builder
@Entity
public class LineItem {
    @Id
    @GeneratedValue(generator = "gen-author")
    @TableGenerator(name = "gen-author",
        table = "ids_generados",
        pkColumnName = "tabla",
        valueColumnName = "sigid",
        pkColumnValue = "xxxxx"
    )

    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;
    
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private List<Book> books;
    private Integer quantity;
}
