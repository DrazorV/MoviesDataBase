package com.main.mdb;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    List<User> findByEmail(String email);
}