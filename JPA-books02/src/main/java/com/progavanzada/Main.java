package com.progavanzada;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import com.progavanzada.models.Author;
import com.progavanzada.models.Book;
import com.progavanzada.models.PurchaseOrder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

class Main
{
    public static void main(String args[])
    {
        EntityManagerFactory entityManagerFactory = Persistence
        .createEntityManagerFactory("default");
        entityManagerFactory.runInTransaction(em -> {
            var author =  Author.builder().name("juan").build();
            em.persist(author);
        });
        EntityManager em = entityManagerFactory.createEntityManager();
         Book book1 = Book.builder()
            .isbn("123-XYZ" + System.currentTimeMillis())  // Generar un ISBN único
            .title("Introducción a la Programación")
            .value(40)
            .build();

        Book book2 = Book.builder()
            .isbn("123-XYZ" + System.currentTimeMillis())  // Generar un ISBN único
            .title("Java Avanzado")
            .value(50)
            .build();

        // Persistir libros
        em.getTransaction().begin();
        em.persist(book1);
        em.persist(book2);
        em.getTransaction().commit();
         TypedQuery<Book> qry = em.createQuery("select o from Book o where o.value > :price", Book.class);
        qry.setParameter("price", 40);
        List<Book> books = qry.getResultList();
        Stream<Book> books2 = qry.getResultStream();

        /*TypedQuery<BookTitlePriceDTO> qry2 = em.createQuery("select o.title, o.value from Book o where o.value > :price", BookTitlePriceDTO.class);
        qry2.setParameter("price", 40);
                qry2.getResultStream().forEach(System.out::println);*/

        TypedQuery<BookTitlePriceDTO> qry3 = em.createQuery("select o.title, o.value, o.inventory.sold from Book o where o.value > :price", BookTitlePriceDTO.class);
        qry3.setParameter("price", -50);
        qry3.getResultStream().forEach(System.out::println);
    }
}

