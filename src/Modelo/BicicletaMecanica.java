package Modelo;

public class BicicletaMecanica extends Veiculo {

    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public BicicletaMecanica(String modelo, Estacao estacaoAtual) {
        super(modelo, estacaoAtual);
    }


    // ==================== CONSULTAR TARIFA DE DESBLOQUEIO ====================
    // Status: NÃO INICIADO

    @Override
    public double consultarTarifaDeDesbloqueio() {
        return 0;
    }


    // ==================== CONSULTAR TARIFA POR MINUTO ====================
    // Status: NÃO INICIADO

    @Override
    public double consultarTarifaPorMinuto() {
        return 0;
    }


    // ==================== CONSULTAR TIPO ====================
    // Status: NÃO INICIADO

    @Override
    public TipoVeiculo consultarTipo() {
        return null;
    }

}