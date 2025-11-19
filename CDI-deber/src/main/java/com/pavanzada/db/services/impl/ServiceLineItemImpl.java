package com.pavanzada.db.services.impl;

import com.pavanzada.db.models.LineItem;
import com.pavanzada.db.services.inter.ServiceLineItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class ServiceLineItemImpl implements ServiceLineItem {

    private final DbClient dbClient;

    @Inject
    public ServiceLineItemImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public Optional<LineItem> findById(Long orderId) {
        return dbClient.execute()
                .createQuery("SELECT * FROM lineitem WHERE order_id = ?")
                .params(orderId)
                .execute()
                .map(this::parse)
                .findFirst();
    }

    @Override
    public List<LineItem> findAll() {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("SELECT * FROM lineitem")
                .execute();

        return rows.map(this::parse).toList();
    }

    @Override
    public void insert(LineItem entity) {
        dbClient.execute()
                .createInsert("INSERT INTO lineitem (order_id, idx, book_isbn, quantity) VALUES (?, ?, ?, ?)")
                .params(entity.getOrderId(), entity.getIdx(), entity.getBookIsbn(), entity.getQuantity())
                .execute();
    }

    @Override
    public void update(LineItem entity) {
        dbClient.execute()
                .createUpdate("UPDATE lineitem SET book_isbn = ?, quantity = ? WHERE order_id = ? AND idx = ?")
                .params(entity.getBookIsbn(), entity.getQuantity(), entity.getOrderId(), entity.getIdx())
                .execute();
    }

    @Override
    public void delete(Long orderId) {
        dbClient.execute()
                .createDelete("DELETE FROM lineitem WHERE order_id = ?")
                .params(orderId)
                .execute();
    }

    private LineItem parse(DbRow row) {
        return LineItem.builder()
                .orderId(row.column("order_id").getLong())
                .idx(row.column("idx").getInt())
                .bookIsbn(row.column("book_isbn").getString())
                .quantity(row.column("quantity").getInt())
                .build();
    }
}
