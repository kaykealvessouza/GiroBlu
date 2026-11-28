package Relatorios;

import Modelo.TipoVeiculo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RelatorioFaturamento {

    // ==================== ATRIBUTOS ====================

    private LocalDate dataInicial;
    private LocalDate dataFinal;

    private Map<TipoVeiculo, Integer> quantidadeViagens;
    private Map<TipoVeiculo, Double> faturamentoPorTipo;


    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    public RelatorioFaturamento(LocalDate dataInicial, LocalDate dataFinal,
                                Map<TipoVeiculo, Integer> quantidadeViagens,
                                Map<TipoVeiculo, Double> faturamentoPorTipo) {
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.quantidadeViagens = new HashMap<>(quantidadeViagens);
        this.faturamentoPorTipo = new HashMap<>(faturamentoPorTipo);
    }


    // ==================== CONSULTAR QUANTIDADE DE VIAGENS POR TIPO ====================
    // Status: IMPLEMENTADO

    public int consultarQuantidadeViagens(TipoVeiculo tipo) {
        return quantidadeViagens.getOrDefault(tipo, 0);
    }


    // ==================== CONSULTAR FATURAMENTO POR TIPO ====================
    // Status: IMPLEMENTADO

    public double consultarFaturamento(TipoVeiculo tipo) {
        return faturamentoPorTipo.getOrDefault(tipo, 0.0);
    }


    // ==================== CONSULTAR QUANTIDADE TOTAL DE VIAGENS ====================
    // Status: IMPLEMENTADO

    public int consultarQuantidadeTotalViagens() {             
        int total = 0;
        for (Integer quantidade : quantidadeViagens.values()) {
            total += quantidade;
        }
        return total;
    }


    // ==================== CONSULTAR FATURAMENTO TOTAL ====================
    // Status: IMPLEMENTADO

    public double consultarFaturamentoTotal() {                
        double total = 0;
        for (Double valor : faturamentoPorTipo.values()) {
            total += valor;
        }
        return total;
    }


    // ==================== CONSULTAR DATA INICIAL ====================
    // Status: IMPLEMENTADO

    public LocalDate consultarDataInicial() {
        return dataInicial;
    }


    // ==================== CONSULTAR DATA FINAL ====================
    // Status: IMPLEMENTADO

    public LocalDate consultarDataFinal() {
        return dataFinal;
    }

}