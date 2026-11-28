
package Financeiro;

import Interfaces.MovimentacaoFinanceira;
import Excecoes.ValorInvalidoException;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Recarga implements MovimentacaoFinanceira, Serializable {

    // ==================== ATRIBUTOS ====================

    private double valor;
    private LocalDateTime dataHora;


    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO / NÃO TESTADO

    public Recarga(double valor, LocalDateTime dataHora)
            throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("Valor inválido, precisa ser maior que 0!");
        }
        if (dataHora == null){
            throw new IllegalArgumentException("A data e hora não podem ser vazias.");
        }

        this.valor = valor;
        this.dataHora = dataHora;
    }


    // ==================== CONSULTAR DATA E HORA DA MOVIMENTAÇÃO ====================
    // Status: IMPLEMENTADO

    @Override
    public LocalDateTime consultarDataHoraMovimentacao() {
        return this.dataHora;
    }


    // ==================== CONSULTAR VALOR DA MOVIMENTAÇÃO ====================
    // Status: IMPLEMENTADO

    @Override
    public double consultarValorMovimentacao() {
        return this.valor;
    }


    // ==================== CONSULTAR TIPO DA MOVIMENTAÇÃO ====================
    // Status: IMPLEMENTADO

    @Override
    public TipoMovimentacao consultarTipoMovimentacao() {
        return TipoMovimentacao.CREDITO;
    }
}
