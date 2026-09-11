package br.senac.hotel.controller;

import br.senac.hotel.models.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit; // IMPORTANTE: Importar para calcular os dias
import java.util.List;
import java.util.Scanner;

public class MenuHospede {

    private Scanner sc = new Scanner(System.in);
    private SistemaCadastro sistemaCadastro;
    private Hospede hospedeLogado;

    public MenuHospede(SistemaCadastro sistemaCadastro, Hospede hospedeLogado) {
        this.sistemaCadastro = sistemaCadastro;
        this.hospedeLogado = hospedeLogado;
    }

    public void menu() {
        int opcao = -1;

        while (opcao != 5) { // Ajustado para 5, que é a sua opção de sair
            System.out.println("\n=== Menu Hóspede ===");
            System.out.println("1 - Fazer reserva");
            System.out.println("2 - Ver minhas reservas / status");
            System.out.println("3 - Fazer check-in");
            System.out.println("4 - Fazer check-out");
            System.out.println("5 - Sair");
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

        // 2. Calcula o valor total usando a função que criamos antes
        double valorTotal = calcularValorHospedagem(checkIn, checkOut, quartoEscolhido);
        System.out.printf("Valor total estimado da hospedagem: R$ %.2f%n", valorTotal);

        // 3. CORREÇÃO DO ERRO VERMELHO:
        // Em vez de chamar o 'hospedeLogado.criarReserva', vamos criar a reserva diretamente aqui
        // passando todas as informações necessárias, incluindo o hóspede logado (dono da reserva).
        int novoId = (int) (Math.random() * 1000); // Gera um ID temporário/aleatório para testes

        Reserva reserva = new Reserva(novoId, checkIn, checkOut, hospedeLogado);

        // Se a sua classe Reserva tiver um campo para salvar o preço ou o quarto, você faz:
        // reserva.setValorTotal(valorTotal);
        // reserva.setTipoQuarto(quartoEscolhido);

        // 4. Salva no sistema de cadastro
        sistemaCadastro.adicionarReserva(reserva);

        System.out.println("Reserva criada! ID: " + reserva.getId() + " | Situação: " + reserva.getSituacao());
    }


    // --- NOVA FUNÇÃO PARA CALCULAR O TEMPO E PREÇO ---
    private double calcularValorHospedagem(LocalDate checkIn, LocalDate checkOut, TipoQuarto quarto) {
        // ChronoUnit.DAYS.between calcula a diferença exata de dias entre as duas datas
        long dias = ChronoUnit.DAYS.between(checkIn, checkOut);

        // Evita que dê erro ou valor zero se a pessoa reservar entrada e saída no mesmo dia
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
}
