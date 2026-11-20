package Modelo;

import Excecoes.ValorInvalidoException;

public class PatineteEletrico extends VeiculoEletrico {

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    public PatineteEletrico(String modelo, Estacao estacaoAtual, int nivelBateria)
            throws ValorInvalidoException {
        super(modelo, estacaoAtual, nivelBateria);
    }

    // ==================== CONSULTAR TARIFA DE DESBLOQUEIO ====================
    // Status: IMPLEMENTADO

    @Override
    public double consultarTarifaDeDesbloqueio() {
        return 3.0;
    }

    // ==================== CONSULTAR TARIFA POR MINUTO ====================
    // Status: IMPLEMENTADO

    @Override
    public double consultarTarifaPorMinuto() {
        return 0.40;
    }

    // ==================== CONSULTAR TIPO ====================
    // Status: IMPLEMENTADO

    @Override
    public TipoVeiculo consultarTipo() {
        return TipoVeiculo.PATINETE_ELETRICO;
    }

    // ==================== CONSULTAR CONSUMO POR MINUTO ====================
    // Status: IMPLEMENTADO

    @Override
    public int consultarConsumoPorMinuto() {
        return 2;
    }

    // ==================== VERIFICAR DESCONTO UNIVERSITÁRIO ====================
    // Status: IMPLEMENTADO

    @Override
    public boolean aceitaDescontoUniversitario() {
        return false;
    }

}