package com.singular.renting.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "RENTAL")
public class Rental {

    private @Id @GeneratedValue Long id;

    private int days;
    private LocalDate initialDate;
    private Float price;
    private int daysDelayed;
    private Float surcharges = 0f;

    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Film film;

}
