package br.senac.hotel.controller;

import br.senac.hotel.models.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit; // IMPORTANTE: Importar para calcular os dia
import java.util.List;
import java.util.Scanner;

public class    MenuHospede {

    private Scanner sc = new Scanner(System.in);
    private SistemaCadastro sistemaCadastro;
    private Hospede hospedeLogado;

    public MenuHospede(SistemaCadastro sistemaCadastro, Hospede hospedeLogado) {
        this.sistemaCadastro = sistemaCadastro;
        this.hospedeLogado = hospedeLogado;
    }

    public void menu() {
        int opcao = -1;

        while (opcao != 6) { // Ajustado para 6, que agora é a opção de sair
            System.out.println("\n=== Menu Hóspede ===");
            System.out.println("1 - Fazer reserva");
            System.out.println("2 - Ver minhas reservas / status");
            System.out.println("3 - Fazer check-in");
            System.out.println("4 - Fazer check-out");
            System.out.println("5 - Cancelar reserva"); // <-- NOVA OPÇÃO
            System.out.println("6 - Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    fazerReserva();
                    break;
                case 2:
                    verReservas();
                    break;
                case 3:
                    fazerCheckIn();
                    break;
                case 4:
                    fazerCheckOut();
                    break;
                case 5:
                    cancelarReserva(); // <-- CHAMADA DO NOVO MÉTODO
                    break;
                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    private void fazerReserva() {
        System.out.print("Data de check-in (aaaa-mm-dd): ");
        LocalDate checkIn = LocalDate.parse(sc.nextLine());

        System.out.print("Data de check-out (aaaa-mm-dd): ");
        LocalDate checkOut = LocalDate.parse(sc.nextLine());

        // 1. Escolha do tipo de quarto
        System.out.println("\n--- Escolha o Tipo de Quarto ---");
        System.out.println("1 - Quarto Casal (R$ 220.00)");
        System.out.println("2 - Quarto Solteiro (R$ 150.00)");
        System.out.println("3 - Quarto Twin (R$ 200.00)");
        System.out.println("4 - Quarto Família (R$ 430.00)");
        System.out.print("Opção de quarto: ");
        int opcaoQuarto = Integer.parseInt(sc.nextLine());

        TipoQuarto quartoEscolhido;
        switch (opcaoQuarto) {
            case 1: quartoEscolhido = TipoQuarto.QUARTO_CASAL; break;
            case 2: quartoEscolhido = TipoQuarto.QUARTO_SOLTEIRO; break;
            case 3: quartoEscolhido = TipoQuarto.QUARTO_TWIN; break;
            case 4: quartoEscolhido = TipoQuarto.QUARTO_FAMILIA; break;
            default:
                System.out.println("Opção inválida! Usando Quarto Solteiro por padrão.");
                quartoEscolhido = TipoQuarto.QUARTO_SOLTEIRO;
                break;
        }


        double valorTotal = calcularValorHospedagem(checkIn, checkOut, quartoEscolhido);
        System.out.printf("Valor total estimado da hospedagem: R$ %.2f%n", valorTotal);



        int novoId = (int) (Math.random() * 1000); // Gera um ID temporário/aleatório para testes

        Reserva reserva = new Reserva(novoId, checkIn, checkOut, hospedeLogado);

        // 4. Salva no sistema de cadastro
        sistemaCadastro.adicionarReserva(reserva);

        System.out.println("Reserva criada! ID: " + reserva.getId() + " | Situação: " + reserva.getSituacao());
    }

    private double calcularValorHospedagem(LocalDate checkIn, LocalDate checkOut, TipoQuarto quarto) {
        long dias = ChronoUnit.DAYS.between(checkIn, checkOut);

        if (dias <= 0) {
            dias = 1;
        }

        System.out.println("Total de diárias: " + dias);
        return dias * quarto.getPrecoDiaria();
    }

    private void verReservas() {
        List<Reserva> reservas = sistemaCadastro.listarReservasPorHospede(hospedeLogado);

        if (reservas.isEmpty()) {
            System.out.println("Você não tem reservas.");
            return;
        }

        for (Reserva r : reservas) {
            System.out.printf("ID: %d | Check-in: %s | Check-out: %s | Situação: %s%n",
                    r.getId(), r.getCheckIn(), r.getCheckOut(), r.getSituacao());
        }
    }

    private void fazerCheckIn() {
        System.out.print("Informe o ID da reserva: ");
        int id = Integer.parseInt(sc.nextLine());

        Reserva reserva = sistemaCadastro.buscarReservaPorId(id);

        if (reserva == null || !reserva.getHospede().equals(hospedeLogado)) {
            System.out.println("Reserva não encontrada.");
            return;
        }

        if (reserva.getSituacao() != SituacaoReserva.PENDENTE) {
            System.out.println("Só é possível fazer check-in de reservas pendentes. Situação atual: " + reserva.getSituacao());
            return;
        }

        reserva.setSituacao(SituacaoReserva.CONFIRMADA);
        System.out.println("Check-in realizado! Situação: " + reserva.getSituacao());
    }

    private void fazerCheckOut() {
        System.out.print("Informe o ID da reserva: ");
        int id = Integer.parseInt(sc.nextLine());

        Reserva reserva = sistemaCadastro.buscarReservaPorId(id);

        if (reserva == null || !reserva.getHospede().equals(hospedeLogado)) {
            System.out.println("Reserva não encontrada.");
            return;
        }

        if (reserva.getSituacao() != SituacaoReserva.CONFIRMADA) {
            System.out.println("Só é possível fazer check-out após o check-in. Situação atual: " + reserva.getSituacao());
            return;
        }

        reserva.setSituacao(SituacaoReserva.CONCLUIDA);
        System.out.println("Check-out realizado! Situação: " + reserva.getSituacao());
    }


    private void cancelarReserva() {
        System.out.print("Informe o ID da reserva que deseja cancelar: ");
        int id = Integer.parseInt(sc.nextLine());

        Reserva reserva = sistemaCadastro.buscarReservaPorId(id);

        // Valida se a reserva existe e pertence ao usuário logado
        if (reserva == null || !reserva.getHospede().equals(hospedeLogado)) {
            System.out.println("Reserva não encontrada.");
            return;
        }

        // Só permite o cancelamento se ela ainda não tiver sido concluída ou já cancelada
        if (reserva.getSituacao() == SituacaoReserva.CONCLUIDA) {
            System.out.println("Não é possível cancelar uma reserva já CONCLUÍDA.");
            return;
        }

        if (reserva.getSituacao() == SituacaoReserva.CANCELADA) {
            System.out.println("Esta reserva já está CANCELADA.");
            return;
        }

        // Muda a situação para cancelada
        reserva.setSituacao(SituacaoReserva.CANCELADA);
        System.out.println("Reserva cancelada com sucesso! Situação: " + reserva.getSituacao());
    }
}
