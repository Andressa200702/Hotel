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


    private Hospede hospede;


    public Reserva(int id, LocalDate checkIn, LocalDate checkOut, Hospede hospede) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.hospede = hospede;
        this.situacao = SituacaoReserva.PENDENTE; // Ou como estiver no seu padrão
    }


    public Hospede getHospede() {
        return this.hospede;
    }


    public int getId() { return id; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public SituacaoReserva getSituacao() { return situacao; }
    public void setSituacao(SituacaoReserva situacao) { this.situacao = situacao; }
}
