package com.alkemy.disney.controller;

import com.alkemy.disney.model.DisneyCharacter;
import com.alkemy.disney.model.GeneralResponse;
import com.alkemy.disney.model.View;
import com.alkemy.disney.repository.DisneyCharactersRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class CharacterController {
    @Autowired
    private DisneyCharactersRepository disneyCharactersRepository;

    // 3. Listado de Personajes
    @GetMapping(path = "/characters") @JsonView(View.Summary.class)
    public ResponseEntity<?> getallcharacters(){
      try{
          Iterable<DisneyCharacter> disneyCharacters = disneyCharactersRepository.findAll();
          return ResponseEntity.ok().body(disneyCharacters);
      }catch (Exception e){
          return ResponseEntity.notFound().build();
      }
    }

    // 4. Creación, Edición y Eliminación de Personajes (CRUD)

    @PostMapping(path = "/character", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createCharacter(@RequestBody DisneyCharacter newCharacter) {
        try {
            DisneyCharacter disneyCharacter = new DisneyCharacter();
            disneyCharacter.setHistory(newCharacter.getHistory());
            disneyCharacter.setName(newCharacter.getName());
            disneyCharacter.setWeight(newCharacter.getWeight());
            disneyCharacter.setAge(newCharacter.getAge());
            disneyCharactersRepository.save(newCharacter);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCharacter);
        }catch (Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "character/{id_disneycharacter}") @JsonView(View.Summary2.class)
    public ResponseEntity<DisneyCharacter> updateDisneyCharacter(@RequestBody DisneyCharacter updatedCharacter, @PathVariable Integer id_disneycharacter) {
        return disneyCharactersRepository.findById(id_disneycharacter).map(p -> {
            p.setImage(updatedCharacter.getImage());
            p.setWeight(updatedCharacter.getWeight());
            p.setHistory(updatedCharacter.getHistory());
            p.setName(updatedCharacter.getName());
            p.setAge(updatedCharacter.getAge());
            return ResponseEntity.ok(disneyCharactersRepository.save(p));
        }).orElseGet(() ->{
            return ResponseEntity.notFound().build();
        });
    }

    @DeleteMapping(path = "/character/{id_disneycharacter}")
    public @ResponseBody
    ResponseEntity<GeneralResponse> deleteCharacterById2(@PathVariable("id_disneycharacter") int id_disneycharacter) {
        GeneralResponse response = new GeneralResponse();
        try{
            disneyCharactersRepository.deleteById(id_disneycharacter);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setMessage((HttpStatus.NOT_FOUND.getReasonPhrase()+ " - "+e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //5. Detalle de Personaje
    @GetMapping(path = "/charactersdetailed") @JsonView(View.Summary2.class)
    public ResponseEntity<?> getallcharactersdetailed(){
      try{
        Iterable<DisneyCharacter> disneyCharacters = disneyCharactersRepository.findAll();
        return ResponseEntity.ok().body(disneyCharacters);
    }catch (Exception e){
        return ResponseEntity.notFound().build();
    }
    }

    // 6. Búsqueda de Personajes
    @GetMapping(path = "/characters2/{name}") @JsonView(View.Summary2.class)
    ResponseEntity getCharacterByNameLike(@PathVariable("name") String name) {
        List<DisneyCharacter> disneyCharacters = disneyCharactersRepository.getDisneyCharacterByNameLike(name);
        if (disneyCharacters.isEmpty() != true){
            return new ResponseEntity(disneyCharacters, HttpStatus.FOUND);
        }else{
            return new ResponseEntity("No se encuentran resultados para su búsqueda, intente nuevamente.", HttpStatus.NOT_FOUND);
        }
        }
}
