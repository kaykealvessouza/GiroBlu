package Modelo;

public enum EstadoVeiculo {

    DISPONIVEL("Disponível"),
    EM_USO("Em uso"),
    MANUTENCAO("Manutenção");
    
    private final String descricao;

    EstadoVeiculo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
