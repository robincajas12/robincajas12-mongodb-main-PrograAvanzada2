package com.progavanzada.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.jboss.logging.annotations.Producer;

@ApplicationScoped
public class Config {
    @Produces
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("default");
    }
}
