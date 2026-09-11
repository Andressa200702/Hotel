package br.senac.hotel.models;

public enum TipoQuarto {
    QUARTO_CASAL(220.00),
    QUARTO_SOLTEIRO(150.00),
    QUARTO_TWIN(200.00),
    QUARTO_FAMILIA(430.00);

    private final double precoDiaria;

    TipoQuarto(double precoDiaria) {
        this.precoDiaria = precoDiaria;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }
}
