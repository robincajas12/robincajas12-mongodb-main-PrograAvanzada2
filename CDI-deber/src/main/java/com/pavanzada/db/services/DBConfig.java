package com.pavanzada.db.services;

import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class DBConfig {
    @Produces
    DbClient getClient()
    {
        Config config = Config.create();
        Config dbConfig = config.get("db");
        DbClient dbClient = DbClient
                .builder()
                .config(dbConfig)
                .build();
        return dbClient;
    }
}
