package Modelo;

import Excecoes.ValorInvalidoException;

public class BicicletaEletrica extends VeiculoEletrico {

    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public BicicletaEletrica(String modelo, Estacao estacaoAtual, int nivelBateria)
            throws ValorInvalidoException {
                super(modelo, estacaoAtual, nivelBateria);
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


    // ==================== CONSULTAR CONSUMO POR MINUTO ====================
    // Status: NÃO INICIADO

    @Override
    public int consultarConsumoPorMinuto() {
        return 0;
    }

}