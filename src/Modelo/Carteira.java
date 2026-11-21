package Modelo;

import Interfaces.MovimentacaoFinanceira;
import Financeiro.Recarga;
import Financeiro.TipoMovimentacao;
import Financeiro.LinhaExtrato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Carteira {

    // ==================== CONSTANTES ====================

    private static final double SALDO_MINIMO = 5.0;

    // ==================== ATRIBUTOS ====================

    private double saldoAtual;
    private List<MovimentacaoFinanceira> historicoMovimentacoes;

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO
    public Carteira() {
        this.saldoAtual = 0;
        this.historicoMovimentacoes = new ArrayList<>();
    }

    // ==================== ADICIONAR SALDO ====================
    // Status: IMPLEMENTADO

    public void adicionarSaldo(Recarga recarga) {
        if (recarga == null) {
            throw new IllegalArgumentException("A recarga não pode ser nula.");
        }

        historicoMovimentacoes.add(recarga);
        saldoAtual += recarga.consultarValorMovimentacao();
    }

    // ==================== DEBITAR VALOR ====================
    // Status: IMPLEMENTADO

    public void debitarValor(Viagem viagem) {
        if (viagem == null) {
            throw new IllegalArgumentException("A viagem não pode ser nula.");
        }

        historicoMovimentacoes.add(viagem);
        saldoAtual -= viagem.consultarValorMovimentacao();
    }

    // ==================== CONSULTAR SALDO ATUAL ====================
    // Status: IMPLEMENTADO

    public double consultarSaldoAtual() {
        return saldoAtual;
    }

    // ==================== CONSULTAR HISTÓRICO DE MOVIMENTAÇÕES
    // ====================
    // Status: IMPLEMENTADO

    public List<MovimentacaoFinanceira> consultarHistoricoMovimentacoes() {
        return Collections.unmodifiableList(new ArrayList<>(historicoMovimentacoes));
    }

    // ==================== VERIFICAR SALDO MÍNIMO ====================
    // Status: IMPLEMENTADO

    public boolean temSaldoMinimo() {
        return saldoAtual >= SALDO_MINIMO;
    }

    // ==================== CONSULTAR EXTRATO ====================
    // Status: IMPLEMENTADO

    public List<LinhaExtrato> consultarExtrato() {
        List<MovimentacaoFinanceira> ordenadas = new ArrayList<>(historicoMovimentacoes);

        ordenadas.sort(Comparator.comparing(MovimentacaoFinanceira::consultarDataHoraMovimentacao));

        double saldo = 0;
        List<LinhaExtrato> extrato = new ArrayList<>();

        for (MovimentacaoFinanceira movimentacao : ordenadas) {
            if (movimentacao.consultarTipoMovimentacao() == TipoMovimentacao.CREDITO) {
                saldo += movimentacao.consultarValorMovimentacao();
            } else {
                saldo -= movimentacao.consultarValorMovimentacao();
            }

            extrato.add(new LinhaExtrato(movimentacao, saldo));
        }

        return Collections.unmodifiableList(extrato);

        /**
         * Consulta o extrato financeiro da carteira, contendo as movimentações
         * ordenadas cronologicamente e o saldo resultante após cada movimentação.
         *
         * O extrato é reconstruído a partir do histórico, iniciando com saldo zero
         * e aplicando os créditos e débitos na ordem de suas datas e horários.
         *
         * @return lista imutável de linhas do extrato, contendo a movimentação
         *         e o saldo após sua aplicação.
         */
    }
}
