package com.recipes.mobile.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="foods")
@Data
public class Foods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String name;

}
