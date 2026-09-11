package br.senac.hotel.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Reserva {
    private int id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private SituacaoReserva situacao;
}
