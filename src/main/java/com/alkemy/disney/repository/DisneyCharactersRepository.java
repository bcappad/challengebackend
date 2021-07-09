package com.alkemy.disney.repository;

import com.alkemy.disney.model.DisneyCharacter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DisneyCharactersRepository extends CrudRepository<DisneyCharacter, Integer> {

    @Query("from DisneyCharacter where name like %:name%")
    List<DisneyCharacter> getDisneyCharacterByNameLike(@Param("name") String name);

    /*
    @Query("from DisneyCharacter where age like %:age%")
    Iterable<DisneyCharacter> getDisneyCharacterByAge(@Param("age") Integer name);

    @Query("from DisneyCharacter where name like %:name%")
    Iterable<DisneyCharacter> getDisneyCharacterByMovieId(@Param("name") String name);
    */
}
