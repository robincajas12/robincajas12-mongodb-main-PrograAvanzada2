package com.pavanzada.db.services.impl;

import com.pavanzada.db.models.BookAuthor;
import com.pavanzada.db.services.inter.ServiceBookAuthor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class ServiceBookAuthorImpl implements ServiceBookAuthor {

    private final DbClient dbClient;

    @Inject
    ServiceBookAuthorImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public Optional<BookAuthor> findById(Long id) {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("select * from book_author where authors_id = ?")
                .params(id)
                .execute();

        return rows.findFirst().map(this::parse);
    }

    @Override
    public List<BookAuthor> findAll() {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("select * from book_author")
                .execute();

        return rows.map(this::parse).toList();
    }

    @Override
    public void insert(BookAuthor entity) {
        dbClient.execute()
                .createInsert("insert into book_author (books_isbn, authors_id) values (?,?)")
                .params(entity.getBooksIsbn(), entity.getAuthorsId())
                .execute();
    }

    @Override
    public void update(BookAuthor entity) {
        // No tiene sentido actualizar PKs, pero lo dejo igual a tu interfaz
        dbClient.execute()
                .createUpdate("update book_author set books_isbn = ? where authors_id = ?")
                .params(entity.getBooksIsbn(), entity.getAuthorsId())
                .execute();
    }

    @Override
    public void delete(Long id) {
        dbClient.execute()
                .createDelete("delete from book_author where authors_id = ?")
                .params(id)
                .execute();
    }

    private BookAuthor parse(DbRow row) {
        return BookAuthor.builder()
                .booksIsbn(row.column("books_isbn").getString())
                .authorsId(row.column("authors_id").as(Long.class).get())
                .build();
    }
}
