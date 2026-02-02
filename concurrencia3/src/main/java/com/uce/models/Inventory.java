package com.uce.models;

import lombok.Data;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Data
@Builder
@Table(name="inventory")
@Entity
public class Inventory {
    @Id
    @OneToOne
    @JoinColumn(name = "book_isbn")
    private Book book;
    private Integer sold;
    private Integer supplied;
    private Integer version;

}
