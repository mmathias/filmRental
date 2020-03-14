package com.singular.renting.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "RENTAL")
public class Rental {

    private @Id @GeneratedValue Long id;

    private int days;
    private LocalDate initialDate;
    private BigDecimal price;
    private int daysDelayed;
    private BigDecimal surcharges = BigDecimal.ZERO;

    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Film film;

}
