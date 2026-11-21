package Modelo;

import Excecoes.ValorInvalidoException;

public class BicicletaEletrica extends VeiculoEletrico {

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    public BicicletaEletrica(String modelo, Estacao estacaoAtual, int nivelBateria)
            throws ValorInvalidoException {
        super(modelo, estacaoAtual, nivelBateria);
    }

    // ==================== CONSULTAR TARIFA DE DESBLOQUEIO ====================
    // Status: IMPLEMENTADO

    @Override
    public double consultarTarifaDeDesbloqueio() {
        return 2.0;
    }

    // ==================== CONSULTAR TARIFA POR MINUTO ====================
    // Status: IMPLEMENTADO

    @Override
    public double consultarTarifaPorMinuto() {
        return 0.30;
    }

    // ==================== CONSULTAR TIPO ====================
    // Status: IMPLEMENTADO

    @Override
    public TipoVeiculo consultarTipo() {
        return TipoVeiculo.BICICLETA_ELETRICA;
    }

    // ==================== CONSULTAR CONSUMO POR MINUTO ====================
    // Status: IMPLEMENTADO

    @Override
    public int consultarConsumoPorMinuto() {
        return 1;
    }

}