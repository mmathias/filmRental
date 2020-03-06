package com.singular.renting.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CUSTOMER")
public class Customer {

    private @Id @GeneratedValue Long id;

    private String name;
    private int bonusPoints;

}
