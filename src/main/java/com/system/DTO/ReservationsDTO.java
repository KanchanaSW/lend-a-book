package com.system.DTO;

public class ReservationsDTO {
    private int reserveIdDTO;
    private String reserveDateDTO;
    private long id;

    public ReservationsDTO() {
    }

    public ReservationsDTO(int reserveIdDTO, String reserveDateDTO, long id) {
        this.reserveIdDTO = reserveIdDTO;
        this.reserveDateDTO = reserveDateDTO;
        this.id = id;
    }

    public int getReserveIdDTO() {
        return reserveIdDTO;
    }

    public void setReserveIdDTO(int reserveIdDTO) {
        this.reserveIdDTO = reserveIdDTO;
    }

    public String getReserveDateDTO() {
        return reserveDateDTO;
    }

    public void setReserveDateDTO(String reserveDateDTO) {
        this.reserveDateDTO = reserveDateDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
