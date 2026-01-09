package com.progavanzada.repositories.inter;

import com.progavanzada.models.Author;

import com.progavanzada.repositories.ClassEntity;

@ClassEntity(Author.class)
public interface AuthorRepository extends CRUD<Author, Long> {
}
