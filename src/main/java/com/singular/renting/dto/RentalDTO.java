package com.singular.renting.dto;

// pq nao usar @Data do Lombok aqui? ta inconsistente com outros POJOS
public class RentalDTO {

    private Long filmId;
    private Long customerId;
    private int days;

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
