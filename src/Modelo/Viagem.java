package Modelo;

import Interfaces.MovimentacaoFinanceira;
import Financeiro.TipoMovimentacao;

import java.time.LocalDateTime;

import Excecoes.DataInvalidaException;
import Excecoes.SemViagemEmAndamentoException;

public class Viagem implements MovimentacaoFinanceira {

    // ==================== ATRIBUTOS ====================

    private Usuario usuario;
    private Veiculo veiculo;
    private Estacao estacaoOrigem;
    private Estacao estacaoDestino;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    private double valorCobrado;


    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public Viagem(Usuario usuario, Veiculo veiculo, Estacao estacaoOrigem,
                  LocalDateTime dataHoraInicio) {

    }


    // ==================== FINALIZAR VIAGEM ====================
    // Status: NÃO INICIADO

    public void finalizarViagem(Estacao estacaoDestino, LocalDateTime dataHoraFim)
            throws SemViagemEmAndamentoException, DataInvalidaException {

    }


    // ==================== CALCULAR DURAÇÃO ====================
    // Status: NÃO INICIADO

    public long calcularDuracao() {
        return 0;
    }


    // ==================== CALCULAR VALOR ====================
    // Status: NÃO INICIADO

    public double calcularValor() {
        return 0;
    }


    // ==================== CONSULTAR SITUAÇÃO ====================
    // Status: NÃO INICIADO

    public boolean estaEmAndamento() {
        return false;
    }


    // ==================== CONSULTAR USUÁRIO ====================
    // Status: NÃO INICIADO

    public Usuario consultarUsuario() {
        return null;
    }


    // ==================== CONSULTAR VEÍCULO ====================
    // Status: NÃO INICIADO

    public Veiculo consultarVeiculo() {
        return null;
    }


    // ==================== CONSULTAR ESTAÇÃO DE ORIGEM ====================
    // Status: NÃO INICIADO

    public Estacao consultarEstacaoOrigem() {
        return null;
    }


    // ==================== CONSULTAR ESTAÇÃO DE DESTINO ====================
    // Status: NÃO INICIADO

    public Estacao consultarEstacaoDestino() {
        return null;
    }


    // ==================== CONSULTAR DATA E HORA DE INÍCIO ====================
    // Status: NÃO INICIADO

    public LocalDateTime consultarDataHoraInicio() {
        return null;
    }


    // ==================== CONSULTAR DATA E HORA DE ENCERRAMENTO ====================
    // Status: NÃO INICIADO

    public LocalDateTime consultarDataHoraFim() {
        return null;
    }


    // ==================== CONSULTAR VALOR COBRADO ====================
    // Status: NÃO INICIADO

    public double consultarValorCobrado() {
        return 0;
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
