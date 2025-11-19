package com.pavanzada.db.services.impl;

import com.pavanzada.db.models.Customer;
import com.pavanzada.db.services.inter.ServiceCustomer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class ServiceCustomerImpl implements ServiceCustomer {

    private final DbClient dbClient;

    @Inject
    public ServiceCustomerImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return dbClient.execute()
                .createQuery("SELECT * FROM customer WHERE id = ?")
                .params(id)
                .execute()
                .map(this::parse)
                .findFirst();
    }

    @Override
    public List<Customer> findAll() {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("SELECT * FROM customer")
                .execute();

        return rows.map(this::parse).toList();
    }

    @Override
    public void insert(Customer entity) {
        dbClient.execute()
                .createInsert("INSERT INTO customer (id, name, email, version) VALUES (?, ?, ?, ?)")
                .params(entity.getId(), entity.getName(), entity.getEmail(), entity.getVersion())
                .execute();
    }

    @Override
    public void update(Customer entity) {
        dbClient.execute()
                .createUpdate("UPDATE customer SET name = ?, email = ?, version = ? WHERE id = ?")
                .params(entity.getName(), entity.getEmail(), entity.getVersion(), entity.getId())
                .execute();
    }

    @Override
    public void delete(Long id) {
        dbClient.execute()
                .createDelete("DELETE FROM customer WHERE id = ?")
                .params(id)
                .execute();
    }

    private Customer parse(DbRow row) {
        return Customer.builder()
                .id(row.column("id").getLong())
                .name(row.column("name").getString())
                .email(row.column("email").getString())
                .version(row.column("version").getInt())
                .build();
    }
}
