
package Financeiro;

import Interfaces.MovimentacaoFinanceira;

public class LinhaExtrato {

    // ==================== ATRIBUTOS ====================

    private final MovimentacaoFinanceira movimentacao;
    private final double saldoApos;

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    public LinhaExtrato(MovimentacaoFinanceira movimentacao, double saldoApos) {
        if (movimentacao == null) {
            throw new IllegalArgumentException("A movimentação não pode ser nula.");
        }

        this.movimentacao = movimentacao;
        this.saldoApos = saldoApos;
    }

    // ==================== CONSULTAR MOVIMENTAÇÃO ====================
    // Status: IMPLEMENTADO

    public MovimentacaoFinanceira consultarMovimentacao() {
        return this.movimentacao;
    }

    // ==================== CONSULTAR SALDO APÓS MOVIMENTAÇÃO ====================
    // Status: IMPLEMENTADO

    public double consultarSaldoApos() {
        return this.saldoApos;
    }
}
