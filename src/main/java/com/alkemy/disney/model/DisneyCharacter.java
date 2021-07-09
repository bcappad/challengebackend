package com.alkemy.disney.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Table(name = "disneycharacter")
public class DisneyCharacter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_disneycharacter;
    @JsonView(View.Summary.class)
    private String image;
    @JsonView({View.Summary.class, View.Summary3.class })
    private String name;
    @JsonView(View.Summary2.class)
    private int age;
    @JsonView(View.Summary2.class)
    private int weight;
    @JsonView(View.Summary2.class)
    private String history;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "filmcharacter",
            joinColumns = @JoinColumn(name = "id_disneycharacter", referencedColumnName = "id_disneycharacter"),
            inverseJoinColumns = @JoinColumn(name = "id_movie"))
    @JsonView(View.Summary2.class)
    private List<Movie> movies;

}
