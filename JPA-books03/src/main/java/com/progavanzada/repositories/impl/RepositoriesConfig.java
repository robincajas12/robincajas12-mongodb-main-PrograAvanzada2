package com.progavanzada.repositories.impl;

import com.progavanzada.models.*;
import com.progavanzada.repositories.Handler;
import com.progavanzada.repositories.inter.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.lang.reflect.Proxy;

@ApplicationScoped
public class RepositoriesConfig {
    @Produces
    @ApplicationScoped
    public AuthorRepository createAuthorRepository(EntityManagerFactory emf) {
        return (AuthorRepository) Proxy.newProxyInstance(
                RepositoriesConfig.class.getClassLoader(),
                new Class[]{AuthorRepository.class},
                new Handler(Author.class, emf)
        );
    }

    @Produces
    @ApplicationScoped
    public BookRepository createBookRepository(EntityManagerFactory emf) {
        return (BookRepository) Proxy.newProxyInstance(
                RepositoriesConfig.class.getClassLoader(),
                new Class[]{BookRepository.class},
                new Handler(Book.class, emf)
        );
    }

    @Produces
    @ApplicationScoped
    public CustomerRepository createCustomerRepository(EntityManagerFactory emf) {
        return (CustomerRepository) Proxy.newProxyInstance(
                RepositoriesConfig.class.getClassLoader(),
                new Class[]{CustomerRepository.class},
                new Handler(Customer.class, emf)
        );
    }

    @Produces
    @ApplicationScoped
    public InventoryRepository createInventoryRepository(EntityManagerFactory emf) {
        return (InventoryRepository) Proxy.newProxyInstance(
                RepositoriesConfig.class.getClassLoader(),
                new Class[]{InventoryRepository.class},
                new Handler(Inventory.class, emf)
        );
    }

    @Produces
    @ApplicationScoped
    public LineItemRepository createLineItemRepository(EntityManagerFactory emf) {
        return (LineItemRepository) Proxy.newProxyInstance(
                RepositoriesConfig.class.getClassLoader(),
                new Class[]{LineItemRepository.class},
                new Handler(LineItem.class, emf)
        );
    }

    @Produces
    @ApplicationScoped
    public PurchaseOrderRepository createPurchaseOrderRepository(EntityManagerFactory emf) {
        return (PurchaseOrderRepository) Proxy.newProxyInstance(
                RepositoriesConfig.class.getClassLoader(),
                new Class[]{PurchaseOrderRepository.class},
                new Handler(PurchaseOrder.class, emf)
        );
    }
}
