package com.pavanzada;

import com.pavanzada.db.models.Book;
import com.pavanzada.db.models.PurchaseOrder;

import com.pavanzada.db.services.inter.ServiceBook;
import com.pavanzada.db.services.inter.ServicePurchaseOrder;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO CDI ===");

        try (SeContainer container = SeContainerInitializer.newInstance()
                .addPackages(Main.class)
                .initialize()) {

            // ============================
            //  BOOK SERVICE
            // ============================
            System.out.println("\n### === TEST ServiceBook === ###");

            ServiceBook serviceBook = container.select(ServiceBook.class).get();

            Book book = Book.builder()
                    .isbn("999-TEST-01")
                    .title("Libro de Prueba")
                    .price(19.99)
                    .version(1)
                    .build();

            serviceBook.insert(book);
            System.out.println("Insertado: " + book);

            serviceBook.findById("999-TEST-01")
                    .ifPresentOrElse(
                            b -> System.out.println("Encontrado: " + b),
                            () -> System.out.println("NO ENCONTRADO")
                    );

            serviceBook.findAll().forEach(System.out::println);

            Book updated = Book.builder()
                    .isbn("999-TEST-01")
                    .title("Libro Actualizado")
                    .price(25.50)
                    .version(2)
                    .build();
            serviceBook.update(updated);

            serviceBook.delete("999-TEST-01");


            // ============================
            //  PURCHASE ORDER SERVICE
            // ============================
            System.out.println("\n### === TEST ServicePurchaseOrder === ###");

            ServicePurchaseOrder poService = container.select(ServicePurchaseOrder.class).get();


            poService.findAll().forEach(System.out::println);


        } catch (Exception e) {
            System.out.println("ERROR EN LA EJECUCIÓN:");
            e.printStackTrace();
        }
    }
}
