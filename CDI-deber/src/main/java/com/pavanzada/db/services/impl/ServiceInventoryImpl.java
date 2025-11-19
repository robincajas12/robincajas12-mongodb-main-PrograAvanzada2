package com.pavanzada.db.services.impl;

import com.pavanzada.db.models.Inventory;
import com.pavanzada.db.services.inter.ServiceInventory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class ServiceInventoryImpl implements ServiceInventory {

    private final DbClient dbClient;

    @Inject
    public ServiceInventoryImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public Optional<Inventory> findById(String isbn) {
        return dbClient.execute()
                .createQuery("SELECT * FROM inventory WHERE book_isbn = ?")
                .params(isbn)
                .execute()
                .map(this::parse)
                .findFirst();
    }

    @Override
    public List<Inventory> findAll() {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("SELECT * FROM inventory")
                .execute();

        return rows.map(this::parse).toList();
    }

    @Override
    public void insert(Inventory entity) {
        dbClient.execute()
                .createInsert("INSERT INTO inventory (book_isbn, sold, supplied, version) VALUES (?, ?, ?, ?)")
                .params(entity.getBookIsbn(), entity.getSold(), entity.getSupplied(), entity.getVersion())
                .execute();
    }

    @Override
    public void update(Inventory entity) {
        dbClient.execute()
                .createUpdate("UPDATE inventory SET sold = ?, supplied = ?, version = ? WHERE book_isbn = ?")
                .params(entity.getSold(), entity.getSupplied(), entity.getVersion(), entity.getBookIsbn())
                .execute();
    }

    @Override
    public void delete(String isbn) {
        dbClient.execute()
                .createDelete("DELETE FROM inventory WHERE book_isbn = ?")
                .params(isbn)
                .execute();
    }

    private Inventory parse(DbRow row) {
        return Inventory.builder()
                .bookIsbn(row.column("book_isbn").getString())
                .sold(row.column("sold").getInt())
                .supplied(row.column("supplied").getInt())
                .version(row.column("version").getInt())
                .build();
    }
}
