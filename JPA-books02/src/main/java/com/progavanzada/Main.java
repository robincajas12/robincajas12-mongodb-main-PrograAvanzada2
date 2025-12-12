package com.progavanzada;


import java.util.List;
import java.util.Random;

import com.progavanzada.models.Author;
import com.progavanzada.models.Book;
import com.progavanzada.models.PurchaseOrder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class Main
{
    public static void main(String args[])
    {
        EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("default");

        EntityManager em = entityManagerFactory.createEntityManager();
       Book book = Book.builder()
    .isbn("123-XYZ" + new Random().nextInt())
    .title("xd xd Programming")
    .authors(

        List.of(
            Author.builder()

        .name("Juan")
        
        .build())


    ).value(50)
    .build();

        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        System.out.println(em.find(Book.class, "123-XYZ"));
        List<Book> books = em.createQuery(
        "SELECT b FROM Book b", Book.class)
        .getResultList();
        books.forEach(System.out::println);
    }

    PurchaseOrder purchaseOrder = PurchaseOrder.builder()
    .id(1L)
    .status(PurchaseOrder.Status.DELIVERED)
    
    .build();
}