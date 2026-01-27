package com.project007;


import com.project007.model.*;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Inicializamos el contenedor CDI
        SeContainer container = SeContainerInitializer.newInstance().initialize();

        // Obtenemos EntityManagerFactory desde el contenedor
        EntityManagerFactory emf = container.select(EntityManagerFactory.class).get();
        EntityManager em = emf.createEntityManager();

        // Insertar datos iniciales seguros (solo si no existen)
        insertInitialData(em);

        Scanner scanner = new Scanner(System.in);
        for (;;) {
            System.out.print("Jakarta Persistence QL> ");
            String query = scanner.nextLine();
            while (!query.endsWith(";")) {
                query += " " + scanner.nextLine();
            }
            query = query.trim();
            if (query.equalsIgnoreCase("quit;")) {
                break;
            }
            if (query.length() == 0) {
                continue;
            }

            try {
                if (query.toLowerCase().startsWith("select")) {
                    List<?> result = em.createQuery(query.substring(0, query.length() - 1)).getResultList();
                    if (result.size() > 0) {
                        int count = 0;
                        for (Object o : result) {
                            System.out.print(++count + " ");
                            printResult(o);
                        }
                    } else {
                        System.out.println("0 results returned");
                    }
                } else {
                    int i = em.createQuery(query.substring(0, query.length() - 1)).executeUpdate();
                    System.out.println(i + " items altered");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        em.close();
        emf.close();
        container.close();
        scanner.close();
    }

    private static void insertInitialData(EntityManager em) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Check if data already exists (e.g., if there are any departments)
            if (!em.createQuery("SELECT d FROM Department d", Department.class).getResultList().isEmpty()) {
                System.out.println("Initial data already exists. Skipping insertion.");
                tx.commit();
                return;
            }

            // 1. Create Departments (at least 20)
            List<Department> departments = new java.util.ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                Department department = Department.builder()
                        .name("Department " + i)
                        .build();
                em.persist(department);
                departments.add(department);
            }

            // 2. Create Projects (10 DesignProject and 10 QualityProject)
            List<Project> projects = new java.util.ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                // Assuming Project, DesignProject and QualityProject don't have builders for fields as they are extended
                // We create a Project instance and then cast for specific types
                DesignProject designProject = new DesignProject();
                designProject.setName("Design Project " + i);
                em.persist(designProject);
                projects.add(designProject);

                QualityProject qualityProject = new QualityProject();
                qualityProject.setName("Quality Project " + i);
                em.persist(qualityProject);
                projects.add(qualityProject);
            }

            // 3. Create Employees (at least 20)
            List<Employee> employees = new java.util.ArrayList<>();
            List<Employee> managers = new java.util.ArrayList<>(); // To keep track of potential managers

            for (int i = 1; i <= 20; i++) {
                Department department = departments.get((i - 1) % departments.size()); // Assign department cyclically

                // Create a basic employee first
                Employee employee = Employee.builder()
                        .name("Employee " + i)
                        .salary(10000L + (i * 1000))
                        .department(department)
                        .build();
                em.persist(employee);
                employees.add(employee);

                // Assign managers if conditions are met
                if (i % 5 == 0 && i > 0) { // Every 5th employee is a manager
                    managers.add(employee);
                }
                if (!managers.isEmpty() && i % 2 != 0 && i > 1) { // Assign a manager to some employees
                    employee.setManager(managers.get((i - 1) % managers.size()));
                }
            }

            // 4. Create Addresses (one for each employee)
            for (int i = 0; i < employees.size(); i++) {
                Employee employee = employees.get(i);
                Address address = Address.builder()
                        .street("Street " + (i + 1))
                        .city("City " + (i + 1))
                        .state("State " + (i + 1))
                        .zip("ZIP" + (i + 1))
                        .employee(employee) // Link address to employee
                        .build();
                em.persist(address);
                employee.setAddress(address); // Also link employee to address
            }

            // 5. Create Phones (2 for each employee)
            for (int i = 0; i < employees.size(); i++) {
                Employee employee = employees.get(i);

                Phone phone1 = Phone.builder()
                        .number("111-222-00" + (i + 1))
                        .type("Mobile")
                        .employee(employee) // Link phone to employee
                        .build();
                em.persist(phone1);
                employee.getPhones().add(phone1); // Add to employee's phone list

                Phone phone2 = Phone.builder()
                        .number("333-444-00" + (i + 1))
                        .type("Work")
                        .employee(employee) // Link phone to employee
                        .build();
                em.persist(phone2);
                employee.getPhones().add(phone2); // Add to employee's phone list
            }

            // 6. Establish Employee-Project relationships (each employee works on 2-3 projects)
            for (int i = 0; i < employees.size(); i++) {
                Employee employee = employees.get(i);
                employee.getProjects().add(projects.get(i % projects.size()));
                employee.getProjects().add(projects.get((i + 1) % projects.size()));
                if (i % 2 == 0) { // Some employees work on a third project
                    employee.getProjects().add(projects.get((i + 2) % projects.size()));
                }
                // No need for em.merge(employee) as employee is already managed by the persistence context
                // and its collections are modified within the transaction.
            }


            tx.commit();
            System.out.println("✅ Initial data inserted");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void printResult(Object result) {
        if (result == null) {
            System.out.print("NULL");
        } else if (result instanceof Object[]) {
            Object[] row = (Object[]) result;
            System.out.print("[");
            for (int i = 0; i < row.length; i++) {
                printResult(row[i]);
            }
            System.out.print("]");
        } else if (result instanceof Long ||
                result instanceof Double ||
                result instanceof String) {
            System.out.print(result.getClass().getSimpleName() + ": " + result);
        } else {
            System.out.print(result.toString());
        }
        System.out.println();
    }
}
