package Modelo;

import Excecoes.OperacaoInvalidaException;
import Excecoes.VeiculoIndisponivelException;

public abstract class Veiculo {

    // ==================== ATRIBUTOS ====================

    private static int contadorCodigo = 1;
    private final int codigoIdentificador;

    private String modelo;
    private EstadoVeiculo estadoAtual;
    private Estacao estacaoAtual;

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    protected Veiculo(String modelo, Estacao estacaoAtual) {
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("O modelo não pode ser vazio.");
        }

        this.modelo = modelo;
        this.estacaoAtual = estacaoAtual;

        this.estadoAtual = EstadoVeiculo.DISPONIVEL;

        this.codigoIdentificador = contadorCodigo;
        contadorCodigo++;
    }

    // ==================== CONSULTAR DISPONIBILIDADE ====================
    // Status: IMPLEMENTADO

    public boolean podeSerLiberado() {
        return estadoAtual == EstadoVeiculo.DISPONIVEL;
        // Se for igual retorna true (Ou seja, se estiver disponivel)
    }

    // ==================== INICIAR UTILIZAÇÃO ====================
    // Status: IMPLEMENTADO

    public void iniciarUtilizacao() throws VeiculoIndisponivelException {
        if (!podeSerLiberado()) {
            throw new VeiculoIndisponivelException("Veículo indisponível no momento!");
        }

        this.estadoAtual = EstadoVeiculo.EM_USO;
        this.estacaoAtual = null;
    }

    // ==================== FINALIZAR UTILIZAÇÃO ====================
    // Status: IMPLEMENTADO

    public void finalizarUtilizacao(Estacao estacaoDestino, long minutos) {
        if (estacaoDestino == null) {
            throw new IllegalArgumentException("A estação de destino não pode ser nula.");
        }

        if (minutos <= 0) {
            throw new IllegalArgumentException("A duração deve ser maior que zero.");
        }

        this.estacaoAtual = estacaoDestino;
        this.estadoAtual = EstadoVeiculo.DISPONIVEL;
    }

    // ==================== ENVIAR PARA MANUTENÇÃO ====================
    // Status: IMPLEMENTADO

    public void enviarParaManutencao() throws OperacaoInvalidaException {
        if (estadoAtual == EstadoVeiculo.EM_USO) {
            throw new OperacaoInvalidaException("O veículo está em uso!");
        }

        estadoAtual = EstadoVeiculo.MANUTENCAO; // Se já estiver em manutenção não faz nada
    }

    // ==================== RETORNAR DA MANUTENÇÃO ====================
    // Status: IMPLEMENTADO

    public void retornarDaManutencao() throws OperacaoInvalidaException {
        if (estadoAtual != EstadoVeiculo.MANUTENCAO) {
            throw new OperacaoInvalidaException("Veículo não está em manutenção!");
        }

        estadoAtual = EstadoVeiculo.DISPONIVEL;
    }

    // ==================== RECARREGAR BATERIA ====================
    // Status: IMPLEMENTADO

    public void recarregarBateria() throws OperacaoInvalidaException {
        throw new OperacaoInvalidaException("Não possui bateria!");
    }

    // ==================== VERIFICAR DESCONTO UNIVERSITÁRIO ====================
    // Status: IMPLEMENTADO

    public boolean aceitaDescontoUniversitario() {
        return true;
    }

    // ==================== CONSULTAR DESCRIÇÃO ====================
    // Status: IMPLEMENTADO

    public String consultarDescricao() {
        return String.format("%d - %s | Modelo: %s",
                consultarCodigo(), consultarTipo(), consultarModelo());
    }

    // ==================== CONSULTAR ESTADO ATUAL ====================
    // Status: IMPLEMENTADO

    public EstadoVeiculo consultarEstadoAtual() {
        return estadoAtual;
    }

    // ==================== CONSULTAR ESTAÇÃO ATUAL ====================
    // Status: IMPLEMENTADO

    public Estacao consultarEstacaoAtual() {
        return estacaoAtual;
    }

    // ==================== CONSULTAR MODELO ====================
    // Status: IMPLEMENTADO

    public String consultarModelo() {
        return modelo;
    }

    // ==================== CONSULTAR CÓDIGO ====================
    // Status: IMPLEMENTADO

    public int consultarCodigo() {
        return codigoIdentificador;
    }

    // ==================== CONSULTAR TARIFA DE DESBLOQUEIO ====================
    // Status: DECLARADO

    public abstract double consultarTarifaDeDesbloqueio();

    // ==================== CONSULTAR TARIFA POR MINUTO ====================
    // Status: DECLARADO

    public abstract double consultarTarifaPorMinuto();

    // ==================== CONSULTAR TIPO ====================
    // Status: DECLARADO

    public abstract TipoVeiculo consultarTipo();

}