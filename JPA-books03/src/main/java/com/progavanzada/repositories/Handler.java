package com.progavanzada.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class Handler implements InvocationHandler {
    public Class<?> clazz;
    public EntityManagerFactory entityManagerFactory;
    @Inject
    public Handler(Class<?> clazz, EntityManagerFactory entityManagerFactory) {
        this.clazz = clazz;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.setAccessible(true);
        System.out.println(method.getName());
        String name = method.getName();

        if(name.equals("findAll")) return findAll(proxy, method, args);
        if(name.equals("findById")) return findById(proxy, method, args);
        if(name.equals("create")) return create(proxy, method, args);
        if(name.equals("update")) return update(proxy, method, args);
        if(name.equals("delete")) return delete(proxy, method, args);

        return null;

    }
    public Object delete(Object proxy, Method method, Object[] args)
    {
        try(EntityManager em = entityManagerFactory.createEntityManager())
        {
            em.getTransaction().begin();
            var author = em.find(Class.class, args[0]);
            if (author != null) {
                em.remove(author);
            }
            em.getTransaction().commit();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public Object update(Object proxy, Method method, Object[] args)
    {
        try(EntityManager em = entityManagerFactory.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(args[0]);
            em.getTransaction().commit();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public Object create(Object proxy, Method method, Object[] args)
    {
        try(EntityManager em = entityManagerFactory.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(args[0]);
            em.getTransaction().commit();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public Object findById(Object proxy, Method method, Object[] args)
    {
        try(EntityManager em = entityManagerFactory.createEntityManager())
        {
            em.getTransaction().begin();
            System.out.println("encontrando por id");
            var res = em.find(clazz, args[0]);
            return res;
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public Object findAll(Object proxy, Method method, Object[] args) throws Throwable {
        try (EntityManager em = entityManagerFactory.createEntityManager()) {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<?> cq = cb.createQuery(clazz);
            Root<?> root = cq.from(clazz);
            //cq.select(root);

            return em.createQuery(cq).getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
