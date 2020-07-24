package com.main.mdb;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveRepository extends CrudRepository<UserSave, Long> {
    Iterable<UserSave> findAllByUser(User user);

    UserSave findByUserAndMovieId(User user,String movieId);

    void deleteByUserAndMovieId(User user, String movieId);

}