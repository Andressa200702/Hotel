package br.senac.hotel.controller;

import br.senac.hotel.models.Hospede;
import br.senac.hotel.models.Reserva;
import br.senac.hotel.models.Usuario;
import java.util.ArrayList;
import java.util.List;

public class SistemaCadastro {
    private List<Usuario> usuarios = new ArrayList<>();

    public void cadastro(Usuario u) {
        usuarios.add(u);
    }

    public void editar(Usuario u) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(u.getEmail())) {
                usuarios.set(i, u);
                return;
            }
        }
    }

    public void remover(Usuario u) {
        Usuario encontrado = null;
        for (Usuario x : usuarios) {
            if (x.getEmail().equals(u.getEmail())) {
                encontrado = x;
                break;
            }
        }
        if (encontrado != null) {
            usuarios.remove(encontrado);
        }
    }

    public void cadastrarHospede(Hospede hospede) {
    }

    public Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    ArrayList<Reserva> r = new ArrayList<>();

    public void adicionarReserva(Reserva reserva) {
        r.add(reserva);
    }

    public List<Reserva> listarReservasPorHospede(Hospede hospedeLogado) {
        List<Reserva> reservasFiltradas = new ArrayList<>();

        for (Reserva reserva : r) {
            if (reserva.getHospede() != null && reserva.getHospede().equals(hospedeLogado)) {
                reservasFiltradas.add(reserva);
            }
        }
        return reservasFiltradas;
    }

    // --- COLE A NOVA FUNÇÃO EXATAMENTE AQUI ---
    public Reserva buscarReservaPorId(int id) {
        for (Reserva reserva : r) {
            if (reserva.getId() == id) {
                return reserva; // Se achar o ID, retorna a reserva encontrada
            }
        }
        return null; // Se vasculhar toda a lista e não achar nada, retorna null
    }
}
