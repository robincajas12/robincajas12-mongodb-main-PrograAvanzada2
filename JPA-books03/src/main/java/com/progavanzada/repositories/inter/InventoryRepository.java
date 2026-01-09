package com.progavanzada.repositories.inter;

import com.progavanzada.models.Author;
import com.progavanzada.models.Book;
import com.progavanzada.models.Inventory;
import com.progavanzada.repositories.ClassEntity;

@ClassEntity(Inventory.class)
public interface InventoryRepository extends CRUD<Inventory, Book> {
}
