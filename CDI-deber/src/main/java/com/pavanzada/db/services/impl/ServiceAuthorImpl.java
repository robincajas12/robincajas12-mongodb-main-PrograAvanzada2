package com.pavanzada.db.services.impl;

import com.pavanzada.db.models.Author;
import com.pavanzada.db.services.inter.ServiceAuthor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class ServiceAuthorImpl implements ServiceAuthor {

    private final DbClient dbClient;

    @Inject
    ServiceAuthorImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public Optional<Author> findById(Long id) {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("select * from author where id = ?")
                .params(id)
                .execute();

        return rows.findFirst().map(this::parse);
    }

    @Override
    public List<Author> findAll() {
        Stream<DbRow> rows = dbClient.execute()
                .createQuery("select * from author")
                .execute();

        return rows.map(this::parse).toList();
    }

    @Override
    public void insert(Author entity) {
        dbClient.execute()
                .createInsert("insert into author (id, name, version) values (?,?,?)")
                .params(entity.getId(), entity.getName(), entity.getVersion())
                .execute();
    }

    @Override
    public void update(Author entity) {
        dbClient.execute()
                .createUpdate("update author set name = ?, version = ? where id = ?")
                .params(entity.getName(), entity.getVersion(), entity.getId())
                .execute();
    }

    @Override
    public void delete(Long id) {
        dbClient.execute()
                .createDelete("delete from author where id = ?")
                .params(id)
                .execute();
    }

    private Author parse(DbRow row) {
        return Author.builder()
                .id(row.column("id").as(Long.class).get())
                .name(row.column("name").getString())
                .version(row.column("version").getInt())
                .build();
    }
}
