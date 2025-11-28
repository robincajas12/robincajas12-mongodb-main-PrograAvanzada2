package com.pavanzada;

import com.pavanzada.db.Author;
import com.pavanzada.db.Book;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola");
        System.out.println("Hello World");
        
        SessionFactory sessionFactory = null;
        Session session = null;
        
        try {
            // Method 1: Using Configuration (simpler)
            sessionFactory = buildSessionFactoryWithConfiguration();
            
            // Method 2: Alternatively, use MetadataSources (more modern)
            // sessionFactory = buildSessionFactoryWithMetadataSources();
            
            session = sessionFactory.openSession();
            
            // Start transaction
            session.beginTransaction();
            
            // Create and persist a book
            Book book = Book.builder()
                .isbn("111562")
                .title("Effective Java")
                .author("Joshua Bloch")
                .build();
            
            session.persist(book);
            session.getTransaction().commit();
            
            System.out.println("Book inserted successfully!");
            
            // Find the book
            Book foundBook = session.find(Book.class, "1111");
            System.out.println("Found book: " + foundBook);
            
        } catch (Exception ex) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (session != null) session.close();
            if (sessionFactory != null) sessionFactory.close();
        }
    }
    
    // Method 1: Using Configuration (recommended for simplicity)
    private static SessionFactory buildSessionFactoryWithConfiguration() {
        try {
            return new Configuration()
                // Database connection
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/books")
                .setProperty("hibernate.connection.username", "postgres")
                .setProperty("hibernate.connection.password", "postgres")
                
                // Hibernate properties
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.format_sql", "true")
                
                // Entity classes
                .addAnnotatedClass(Book.class)
              
                 .addAnnotatedClass(Author.class) // Add if you have Author entity
                
                .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create SessionFactory", e);
        }
    }
    
    // Method 2: Using MetadataSources (more modern approach)
    private static SessionFactory buildSessionFactoryWithMetadataSources() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                .applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5432/books")
                .applySetting("hibernate.connection.username", "postgres")
                .applySetting("hibernate.connection.password", "postgres")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .applySetting("hibernate.hbm2ddl.auto", "create")
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .build();
            
            return new MetadataSources(registry)
                .addAnnotatedClass(Book.class)
                // .addAnnotatedClass(Author.class) // Add if you have Author entity
                .buildMetadata()
                .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create SessionFactory", e);
        }
    }
}