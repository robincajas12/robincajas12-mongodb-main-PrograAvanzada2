package com.pavanzada.db.models;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class BookAuthor {
    private String booksIsbn;
    private Long authorsId;
}
