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

    // 1. ADICIONE ESSE ATRIBUTO (caso não tenha)
    private Hospede hospede;

    // 2. AJUSTE O SEU CONSTRUTOR PARA RECEBER O HÓSPEDE
    public Reserva(int id, LocalDate checkIn, LocalDate checkOut, Hospede hospede) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.hospede = hospede;
        this.situacao = SituacaoReserva.PENDENTE; // Ou como estiver no seu padrão
    }

    // 3. ADICIONE ESSE MÉTODO QUE ESTÁ FALTANDO (Ele vai sumir com o erro vermelho!)
    public Hospede getHospede() {
        return this.hospede;
    }

    // Mantenha os seus outros getters e setters abaixo...
    public int getId() { return id; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
    public SituacaoReserva getSituacao() { return situacao; }
    public void setSituacao(SituacaoReserva situacao) { this.situacao = situacao; }
}
