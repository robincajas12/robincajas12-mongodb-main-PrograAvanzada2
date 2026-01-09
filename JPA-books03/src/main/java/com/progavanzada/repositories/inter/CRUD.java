package com.progavanzada.repositories.inter;

public interface CRUD<T,ID> {
    public T findById(ID id);
    public T findAll();
    public T create(T entity);
    public T update(T entity);
    public void delete(T entity);
}
