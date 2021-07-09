package com.alkemy.disney.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "filmcharacter")
public class FilmCharacter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_filmcharacter;

    @OneToOne
    @JoinColumn(name = "id_disneycharacter", referencedColumnName = "id_disneycharacter")
    @JsonView({View.Summary.class})
    private DisneyCharacter id_disneycharacter;

    @OneToOne
    @JsonView({View.Summary.class})
    @JoinColumn(name = "id_movie", referencedColumnName = "id_movie")
    private Movie id_movie;

}
