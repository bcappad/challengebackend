package com.alkemy.disney.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_movie;
    @JsonView({View.Summary.class, View.Summary3.class})
    private String image;
    @JsonView({View.Summary.class, View.Summary3.class})
    //@Column(name = "title", unique = true)
    private String title;
    @JsonView({View.Summary.class, View.Summary3.class})
    private Date creationdate;
    @JsonView(View.Summary3.class)
    private int stars;

    @OneToOne
    @JsonView(View.Summary3.class)
    @JoinColumn(name = "id_genre", referencedColumnName = "id_genre")
    private Genre id_genre;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "filmcharacter",
            joinColumns = @JoinColumn(name = "id_movie", referencedColumnName = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_disneycharacter"))
    @JsonView(View.Summary3.class)
    private List<DisneyCharacter> disneyCharacters;

}
