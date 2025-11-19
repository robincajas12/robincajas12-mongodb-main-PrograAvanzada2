package com.pavanzada.db.services.inter;

import java.util.List;
import java.util.Optional;

public interface Services<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void update(T item);
    void insert(T item);
    void delete(ID id);
}
