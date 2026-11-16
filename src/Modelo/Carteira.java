
package Modelo;

import Interfaces.MovimentacaoFinanceira;
import Financeiro.Recarga;
import Financeiro.LinhaExtrato;

import java.util.Collections;
import java.util.List;

public class Carteira {

    // ==================== CONSTANTES ====================

    private static final double SALDO_MINIMO = 5.0;


    // ==================== ATRIBUTOS ====================

    private double saldoAtual;
    private List<MovimentacaoFinanceira> historicoMovimentacoes;


    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public Carteira() {

    }


    // ==================== ADICIONAR SALDO ====================
    // Status: NÃO INICIADO

    public void adicionarSaldo(Recarga recarga) {

    }


    // ==================== DEBITAR VALOR ====================
    // Status: NÃO INICIADO

    public void debitarValor(Viagem viagem) {

    }


    // ==================== CONSULTAR SALDO ATUAL ====================
    // Status: NÃO INICIADO

    public double consultarSaldoAtual() {
        return 0;
    }


    // ==================== CONSULTAR HISTÓRICO DE MOVIMENTAÇÕES ====================
    // Status: NÃO INICIADO

    public List<MovimentacaoFinanceira> consultarHistoricoMovimentacoes() {
        return Collections.emptyList();
    }


    // ==================== VERIFICAR SALDO MÍNIMO ====================
    // Status: NÃO INICIADO

    public boolean temSaldoMinimo() {
        return false;
    }


    // ==================== CONSULTAR EXTRATO ====================
    // Status: NÃO INICIADO

    public List<LinhaExtrato> consultarExtrato() {
        return Collections.emptyList();
    }

}
