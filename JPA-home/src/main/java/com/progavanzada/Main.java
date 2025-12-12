package com.progavanzada;


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
       em.getTransaction().begin();
        em.persist(
            Student.builder()
            .name("Juan").build());
        em.getTransaction().commit();
        System.out.println(em.find(Student.class, 1));
    }
}