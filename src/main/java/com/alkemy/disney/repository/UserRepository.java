package com.alkemy.disney.repository;

import com.alkemy.disney.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
