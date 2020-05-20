package com.main.mdb;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface userRepo extends CrudRepository<user, Long> {
    List<user> findByEmail(String email);
}