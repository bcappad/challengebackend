package com.alkemy.disney.repository;

import com.alkemy.disney.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface  GenreRepository extends CrudRepository<Genre, Integer> {
}
