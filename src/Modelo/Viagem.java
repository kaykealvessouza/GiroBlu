package Modelo;

import Interfaces.MovimentacaoFinanceira;
import Financeiro.TipoMovimentacao;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

import Excecoes.DataInvalidaException;
import Excecoes.SemViagemEmAndamentoException;

public class Viagem implements MovimentacaoFinanceira, Serializable {

    // ==================== ATRIBUTOS ====================

    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private Veiculo veiculo;
    private Estacao estacaoOrigem;
    private Estacao estacaoDestino;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    private double valorCobrado;

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    public Viagem(Usuario usuario, Veiculo veiculo, Estacao estacaoOrigem, LocalDateTime dataHoraInicio) {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo!");
        }

        if (veiculo == null) {
            throw new IllegalArgumentException("O veículo não pode ser nulo!");
        }

        if (estacaoOrigem == null) {
            throw new IllegalArgumentException("A estação inicial não pode ser nula!");
        }

        if (dataHoraInicio == null) {
            throw new IllegalArgumentException("A data e hora de início não podem ser nulas!");
        }

        this.usuario = usuario;
        this.veiculo = veiculo;
        this.estacaoOrigem = estacaoOrigem;
        this.dataHoraInicio = dataHoraInicio;
    }

    // ==================== FINALIZAR VIAGEM ====================
    // Status: IMPLEMENTADO

    public void finalizarViagem(Estacao estacaoDestino, LocalDateTime dataHoraFim)
            throws SemViagemEmAndamentoException, DataInvalidaException {

        if (dataHoraFim == null) {
            throw new IllegalArgumentException("Data e hora de fim não devem ser vazias");
        }

        if (estacaoDestino == null) {
            throw new IllegalArgumentException("Estação de destino não deve ser vazia");
        }

        if (!estaEmAndamento()) {
            throw new SemViagemEmAndamentoException("Esta viagem já foi encerrada.");
        }

        if (dataHoraFim.isAfter(dataHoraInicio) == false) {
            throw new DataInvalidaException("A data e hora de término precisa ser posterior ao início da viagem.");
        }

        this.estacaoDestino = estacaoDestino;
        this.dataHoraFim = dataHoraFim;
        this.valorCobrado = Math.round(calcularValor() * 100) / 100.0;
    }

    // ==================== CALCULAR DURAÇÃO ====================
    // Status: IMPLEMENTADO

    /**
     * Calcula a duração da viagem em minutos, arredondando para cima.
     * Qualquer fração de minuto conta como um minuto inteiro
     * (ex.: 12 min 40 s = 13 minutos; 40 s = 1 minuto).
     * <p>
     * Pré-condição: só deve ser chamado depois que a viagem for encerrada.
     *
     * @return duração da viagem em minutos, sempre maior ou igual a 1
     */
    public long calcularDuracao() {
        Duration intervalo = Duration.between(dataHoraInicio, dataHoraFim);
        long minutos = intervalo.toMinutes();

        if (!intervalo.minusMinutes(minutos).isZero()) {
            minutos++;
        }
        return minutos;
    }

    // ==================== CALCULAR VALOR ====================
    // Status: IMPLEMENTADO

    public double calcularValor() {
        return usuario.consultarPlanoContratado().calcularValor(veiculo, calcularDuracao());
    }

    // ==================== CONSULTAR SITUAÇÃO ====================
    // Status: IMPLEMENTADO

    public boolean estaEmAndamento() {
        if (dataHoraFim == null) {
            return true;
        }
        return false;
    }

    // ==================== CONSULTAR USUÁRIO ====================
    // Status: IMPLEMENTADO

    public Usuario consultarUsuario() {
        return usuario;
    }

    // ==================== CONSULTAR VEÍCULO ====================
    // Status: IMPLEMENTADO

    public Veiculo consultarVeiculo() {
        return veiculo;
    }

    // ==================== CONSULTAR ESTAÇÃO DE ORIGEM ====================
    // Status: IMPLEMENTADO

    public Estacao consultarEstacaoOrigem() {
        return estacaoOrigem;
    }

    // ==================== CONSULTAR ESTAÇÃO DE DESTINO ====================
    // Status: IMPLEMENTADO

    public Estacao consultarEstacaoDestino() {
        return estacaoDestino;
    }

    // ==================== CONSULTAR DATA E HORA DE INÍCIO ====================
    // Status: IMPLEMENTADO

    public LocalDateTime consultarDataHoraInicio() {
        return dataHoraInicio;
    }

    // ==================== CONSULTAR DATA E HORA DE ENCERRAMENTO
    // ====================
    // Status: IMPLEMENTADO

    public LocalDateTime consultarDataHoraFim() {
        return dataHoraFim;
    }

    // ==================== CONSULTAR VALOR COBRADO ====================
    // Status: IMPLEMENTADO

    public double consultarValorCobrado() {
        return valorCobrado;
    }

    // ==================== CONSULTAR DATA E HORA DA MOVIMENTAÇÃO
    // ====================
    // Status: IMPLEMENTADO

    @Override
    public LocalDateTime consultarDataHoraMovimentacao() {
        return dataHoraFim;
    }

    // ==================== CONSULTAR VALOR DA MOVIMENTAÇÃO ====================
    // Status: IMPLEMENTADO

    @Override
    public double consultarValorMovimentacao() {
        return valorCobrado;
    }

    // ==================== CONSULTAR TIPO DA MOVIMENTAÇÃO ====================
    // Status: IMPLEMENTADO

    @Override
    public TipoMovimentacao consultarTipoMovimentacao() {
        return TipoMovimentacao.DEBITO;
    }

}
