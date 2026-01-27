package com.project007;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class Config {
    @Produces
    @ApplicationScoped
    public EntityManagerFactory getEmf()
    {
        return Persistence.createEntityManagerFactory("default");
    }
}
