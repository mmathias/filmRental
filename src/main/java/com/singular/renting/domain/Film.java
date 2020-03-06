package com.singular.renting.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "FILM")
public class Film {

    private @Id @GeneratedValue Long id;

    private String title;
    private int quantity;
    private FilmType filmType;
    private PriceType priceType;

}
