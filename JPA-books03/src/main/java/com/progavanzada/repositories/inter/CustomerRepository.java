package com.progavanzada.repositories.inter;

import com.progavanzada.models.Author;
import com.progavanzada.models.Customer;
import com.progavanzada.repositories.ClassEntity;

@ClassEntity(Customer.class)
public interface CustomerRepository extends CRUD<Customer, Long> {
}
