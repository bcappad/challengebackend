package com.alkemy.disney.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Genre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_genre;
    @JsonView(View.Summary3.class)
    private String name;
    private String image;
}
