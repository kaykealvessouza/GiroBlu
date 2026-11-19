package Modelo;

public enum TipoVeiculo {

    BICICLETA_MECANICA("Bicicleta Mecânica"),
    BICICLETA_ELETRICA("Bicicleta Elétrica"),
    PATINETE_ELETRICO("Patinete Elétrico");

    private final String descricao;

    TipoVeiculo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}