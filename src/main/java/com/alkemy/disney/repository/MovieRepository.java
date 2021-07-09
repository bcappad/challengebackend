package com.alkemy.disney.repository;

import com.alkemy.disney.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie,Integer> {

    @Query("from Movie where title like %:title%")
    List<Movie> getMovieByTitleLike(@Param("title") String title);
}
