package com.singular.renting.dto;

import lombok.Data;

@Data
public class RentalDTO {
    private Long filmId;
    private Long customerId;
    private int days;
}
