package com.pavanzada.db.services.impl;

import com.pavanzada.db.models.Book;
import com.pavanzada.db.services.inter.ServiceBook;

import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class ServiceBookImpl implements ServiceBook {

    private final DbClient dbClient;

    @Inject
    public ServiceBookImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public Optional<Book> findById(String id) {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("SELECT * FROM book WHERE isbn = :id")
                .addParam("id", id)
                .execute();

        return rows
                .findFirst()
                .map(this::parse);
    }

    @Override
    public List<Book> findAll() {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("SELECT * FROM book")
                .execute();

        return rows.map(this::parse).toList();
    }

    @Override
    public void insert(Book entity) {
        dbClient.execute()
                .createInsert("INSERT INTO book (isbn, title, price, version) VALUES (:isbn, :title, :price, :version)")
                .addParam("isbn", entity.getIsbn())
                .addParam("title", entity.getTitle())
                .addParam("price", entity.getPrice())
                .addParam("version", entity.getVersion())
                .execute();
    }

    @Override
    public void update(Book entity) {
        dbClient.execute()
                .createUpdate("UPDATE book SET title = :title, price = :price, version = :version WHERE isbn = :isbn")
                .addParam("title", entity.getTitle())
                .addParam("price", entity.getPrice())
                .addParam("version", entity.getVersion())
                .addParam("isbn", entity.getIsbn())
                .execute();
    }

    @Override
    public void delete(String id) {
        dbClient.execute()
                .createDelete("DELETE FROM book WHERE isbn = :isbn")
                .addParam("isbn", id)
                .execute();
    }

    private Book parse(DbRow row) {
        return Book.builder()
                .isbn(row.column("isbn").getString())
                .price(row.column("price").getDouble())
                .title(row.column("title").getString())
                .version(row.column("version").getInt())
                .build();
    }
}
