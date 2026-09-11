package br.senac.hotel.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hospede {
    private Usuario usuario;
    private String documento;

    public Reserva criarReserva(){
        return new Reserva();
    }
}
