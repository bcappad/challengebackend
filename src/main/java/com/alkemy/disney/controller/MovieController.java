package com.alkemy.disney.controller;

import com.alkemy.disney.model.GeneralResponse;
import com.alkemy.disney.model.Movie;
import com.alkemy.disney.model.View;
import com.alkemy.disney.repository.MovieRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    // 7. Listado de Películas
    @GetMapping(path = "/movies") @JsonView(View.Summary.class)
    public ResponseEntity<?> getallmovies() {
        try{
            Iterable<Movie> movies = movieRepository.findAll();
            return ResponseEntity.ok().body(movies);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    // 8. Detalle de Película / Serie con sus personajes
    @GetMapping(path = "/moviesdetailed") @JsonView(View.Summary3.class)
    public ResponseEntity<?> getallmoviesdetailed() {
        try{
            Iterable<Movie> movies = movieRepository.findAll();
            return ResponseEntity.ok().body(movies);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    // 9. Creación, Edición y Eliminación de Película / Serie

    @PostMapping(path = "/movie", consumes = "application/json", produces = "application/json")
    ResponseEntity<?> createMovies (@RequestBody Movie newMovie) {
        try{
            GeneralResponse response = new GeneralResponse();
            Movie movie = new Movie();
            movie.setStars(newMovie.getStars());
            movie.setCreationdate(newMovie.getCreationdate());
            movie.setImage(newMovie.getImage());
            if (newMovie.getStars() < 6){
                movieRepository.save(newMovie);
                return new ResponseEntity(newMovie, HttpStatus.CREATED);
            }else{
                return new ResponseEntity ("La puntuación debe ser entre 1 y 5, intente nuevamente", HttpStatus.EXPECTATION_FAILED);
            }
        }catch (Exception e){
            GeneralResponse response = new GeneralResponse();
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase()+ " - "+e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "movie/{id_movie}") @JsonView(View.Summary.class)
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie updatedMovie, @PathVariable Integer id_movie) {
        return movieRepository.findById(id_movie).map(p -> {
            p.setImage(updatedMovie.getImage());
            p.setTitle(updatedMovie.getTitle());
            p.setCreationdate(updatedMovie.getCreationdate());
            p.setStars(updatedMovie.getStars());
            return ResponseEntity.ok(movieRepository.save(p));
        }).orElseGet(() ->{
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping(path = "/movie/{id_movie}")
    public @ResponseBody
    ResponseEntity<GeneralResponse> deleteMovieById(@PathVariable("id_movie") int id_movie) {
        GeneralResponse response = new GeneralResponse();
        try{
            movieRepository.deleteById(id_movie);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMessage((HttpStatus.NOT_FOUND.getReasonPhrase()+ " - "+e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 10.Búsqueda de Películas o Series
    @GetMapping(path = "/movie/{title}") @JsonView(View.Summary3.class)
    ResponseEntity getMovieByTitleLike (@PathVariable("title") String title){
        List<Movie> movies = movieRepository.getMovieByTitleLike(title);
        try{
            return new ResponseEntity(movies, HttpStatus.FOUND);
        }catch (Exception e){
            GeneralResponse response = new GeneralResponse();
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMessage((HttpStatus.NOT_FOUND.getReasonPhrase()+ " - "+e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
