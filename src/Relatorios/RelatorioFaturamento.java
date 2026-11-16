
package Relatorios;

import Modelo.TipoVeiculo;

import java.time.LocalDate;
import java.util.Map;

public class RelatorioFaturamento {

    // ==================== ATRIBUTOS ====================

    private LocalDate dataInicial;
    private LocalDate dataFinal;

    private Map<TipoVeiculo, Integer> quantidadeViagens;
    private Map<TipoVeiculo, Double> faturamentoPorTipo;


    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public RelatorioFaturamento(LocalDate dataInicial, LocalDate dataFinal,
                                Map<TipoVeiculo, Integer> quantidadeViagens,
                                Map<TipoVeiculo, Double> faturamentoPorTipo) {

    }


    // ==================== CONSULTAR QUANTIDADE DE VIAGENS POR TIPO ====================
    // Status: NÃO INICIADO

    public int consultarQuantidadeViagens(TipoVeiculo tipo) {
        return 0;
    }


    // ==================== CONSULTAR FATURAMENTO POR TIPO ====================
    // Status: NÃO INICIADO

    public double consultarFaturamento(TipoVeiculo tipo) {
        return 0;
    }


    // ==================== CONSULTAR QUANTIDADE TOTAL DE VIAGENS ====================
    // Status: NÃO INICIADO

    public int consultarQuantidadeTotalViagens() {
        return 0;
    }


    // ==================== CONSULTAR FATURAMENTO TOTAL ====================
    // Status: NÃO INICIADO

    public double consultarFaturamentoTotal() {
        return 0;
    }


    // ==================== CONSULTAR DATA INICIAL ====================
    // Status: NÃO INICIADO

    public LocalDate consultarDataInicial() {
        return null;
    }


    // ==================== CONSULTAR DATA FINAL ====================
    // Status: NÃO INICIADO

    public LocalDate consultarDataFinal() {
        return null;
    }

}
