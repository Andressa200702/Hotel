package br.senac.hotel.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private TipoCadastro tipo;

    public boolean login(String emailDigitado, String senhaDigitado) {
        return emailDigitado.equals(this.email) && senhaDigitado.equals(this.senha);
    }
}
