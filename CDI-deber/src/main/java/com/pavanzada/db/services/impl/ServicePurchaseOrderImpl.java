package com.pavanzada.db.services.impl;

import com.pavanzada.db.models.PurchaseOrder;
import com.pavanzada.db.services.inter.ServicePurchaseOrder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class ServicePurchaseOrderImpl implements ServicePurchaseOrder {

    private final DbClient dbClient;

    @Inject
    public ServicePurchaseOrderImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public Optional<PurchaseOrder> findById(Long id) {
        return dbClient.execute()
                .createQuery("SELECT * FROM purchaseorder WHERE id = ?")
                .params(id)
                .execute()
                .map(this::parse)
                .findFirst();
    }

    @Override
    public List<PurchaseOrder> findAll() {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("SELECT * FROM purchaseorder")
                .execute();

        return rows.map(this::parse).toList();
    }

    @Override
    public void insert(PurchaseOrder entity) {
        dbClient.execute()
                .createInsert("""
                        INSERT INTO purchaseorder 
                        (id, placedon, deliveredon, status, total, customer_id) 
                        VALUES (?, ?, ?, ?, ?, ?)
                        """)
                .params(
                        entity.getId(),
                        entity.getPlacedOn(),
                        entity.getDeliveredOn(),
                        entity.getStatus(),
                        entity.getTotal(),
                        entity.getCustomerId()
                )
                .execute();
    }

    @Override
    public void update(PurchaseOrder entity) {
        dbClient.execute()
                .createUpdate("""
                        UPDATE purchaseorder 
                        SET placedon = ?, deliveredon = ?, status = ?, total = ?, customer_id = ?
                        WHERE id = ?
                        """)
                .params(
                        entity.getPlacedOn(),
                        entity.getDeliveredOn(),
                        entity.getStatus(),
                        entity.getTotal(),
                        entity.getCustomerId(),
                        entity.getId()
                )
                .execute();
    }

    @Override
    public void delete(Long id) {
        dbClient.execute()
                .createDelete("DELETE FROM purchaseorder WHERE id = ?")
                .params(id)
                .execute();
    }

    private PurchaseOrder parse(DbRow row) {
        return PurchaseOrder.builder()
                .id(row.column("id").getLong())
                .placedOn(row.column("placedon").as(LocalDateTime.class).orElse(null))
                .deliveredOn(row.column("deliveredon").as(LocalDateTime.class).orElse(null))
                .status(row.column("status").getInt())
                .total(row.column("total").getInt())
                .customerId(row.column("customer_id").getLong())
                .build();
    }
}
