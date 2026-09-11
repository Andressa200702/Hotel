package br.senac.hotel;

import br.senac.hotel.controller.MenuHospede;
import br.senac.hotel.controller.SistemaCadastro;
import br.senac.hotel.models.Hospede;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaCadastro sistema = new SistemaCadastro();


        Hospede teste = new Hospede("Admin", "admin@email.com", "123");
        sistema.cadastro(teste.getUsuario());

        int opcao = -1;

        while (opcao != 3) {
            System.out.println("\n=== BEM-VINDO AO HOTEL SENAC ===");
            System.out.println("1 - Criar Conta (Cadastrar)");
            System.out.println("2 - Entrar (Login)");
            System.out.println("3 - Fechar Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    fazerCadastro(sistema, sc);
                    break;
                case 2:
                    fazerLogin(sistema, sc);
                    break;
                case 3:
                    System.out.println("Encerrando o sistema do Hotel. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
        sc.close();
    }

    private static void fazerCadastro(SistemaCadastro sistema, Scanner sc) {
        System.out.println("\n--- TELA DE CADASTRO ---");
        System.out.print("Nome completo: ");
        String nome = sc.nextLine();

        System.out.print("E-mail: ");
        String email = sc.nextLine();

        if (sistema.buscarPorEmail(email) != null) {
            System.out.println("Erro: Este e-mail já está cadastrado no sistema!");
            return;
        }

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        Hospede novoHospede = new Hospede(nome, email, senha);


        sistema.cadastro(novoHospede.getUsuario());

        System.out.println("Cadastro realizado com sucesso! Agora você pode fazer login.");
    }

    private static void fazerLogin(SistemaCadastro sistema, Scanner sc) {
        System.out.println("\n--- TELA DE LOGIN ---");
        System.out.print("Digite seu e-mail: ");
        String email = sc.nextLine();

        System.out.print("Digite sua senha: ");
        String senha = sc.nextLine();

        br.senac.hotel.models.Usuario usuarioEncontrado = sistema.buscarPorEmail(email);

        if (usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(senha)) {
            System.out.println("\nLogin efetuado com sucesso! Bem-vindo, " + usuarioEncontrado.getNome() + ".");

            // Recria o objeto Hospede vinculando o usuário logado para o menu funcionar
            Hospede hospedeLogado = new Hospede(usuarioEncontrado.getNome(), usuarioEncontrado.getEmail(), usuarioEncontrado.getSenha());

            MenuHospede menuHospede = new MenuHospede(sistema, hospedeLogado);
            menuHospede.menu();

        } else {
            System.out.println("E-mail ou senha incorretos. Tente novamente.");
        }
    }
}
