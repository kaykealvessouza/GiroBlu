package Modelo;

public class BicicletaMecanica extends Veiculo {

    // ==================== ATRIBUTOS ====================
    
    private static final long serialVersionUID = 1L;

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    public BicicletaMecanica(String modelo, Estacao estacaoAtual) {
        super(modelo, estacaoAtual);
    }

    // ==================== CONSULTAR TARIFA DE DESBLOQUEIO ====================
    // Status: IMPLEMENTADO

    @Override
    public double consultarTarifaDeDesbloqueio() {
        return 1.0;
    }

    // ==================== CONSULTAR TARIFA POR MINUTO ====================
    // Status: IMPLEMENTADO

    @Override
    public double consultarTarifaPorMinuto() {
        return 0.15;
    }

    // ==================== CONSULTAR TIPO ====================
    // Status: IMPLEMENTADO

    @Override
    public TipoVeiculo consultarTipo() {
        return TipoVeiculo.BICICLETA_MECANICA;
    }

}