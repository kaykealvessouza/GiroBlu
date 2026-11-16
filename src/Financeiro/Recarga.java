
package Financeiro;

import Interfaces.MovimentacaoFinanceira;
import Excecoes.ValorInvalidoException;

import java.time.LocalDateTime;

public class Recarga implements MovimentacaoFinanceira {

    // ==================== ATRIBUTOS ====================

    private double valor;
    private LocalDateTime dataHora;


    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public Recarga(double valor, LocalDateTime dataHora)
            throws ValorInvalidoException {

    }


    // ==================== CONSULTAR DATA E HORA DA MOVIMENTAÇÃO ====================
    // Status: NÃO INICIADO

    @Override
    public LocalDateTime consultarDataHoraMovimentacao() {
        return null;
    }


    // ==================== CONSULTAR VALOR DA MOVIMENTAÇÃO ====================
    // Status: NÃO INICIADO

    @Override
    public double consultarValorMovimentacao() {
        return 0;
    }


    // ==================== CONSULTAR TIPO DA MOVIMENTAÇÃO ====================
    // Status: NÃO INICIADO

    @Override
    public TipoMovimentacao consultarTipoMovimentacao() {
        return null;
    }

}
