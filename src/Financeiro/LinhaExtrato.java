
package Financeiro;

import Interfaces.MovimentacaoFinanceira;

public class LinhaExtrato {

    // ==================== ATRIBUTOS ====================

    private final MovimentacaoFinanceira movimentacao;
    private final double saldoApos;


    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public LinhaExtrato(MovimentacaoFinanceira movimentacao, double saldoApos) {
        this.movimentacao = movimentacao;
        this.saldoApos = saldoApos;
    }


    // ==================== CONSULTAR MOVIMENTAÇÃO ====================
    // Status: NÃO INICIADO

    public MovimentacaoFinanceira consultarMovimentacao() {
        return null;
    }


    // ==================== CONSULTAR SALDO APÓS MOVIMENTAÇÃO ====================
    // Status: NÃO INICIADO

    public double consultarSaldoApos() {
        return 0;
    }

}
