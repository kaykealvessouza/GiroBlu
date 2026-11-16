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
    // Status: NÃO INICIADO

    protected Veiculo(String modelo, Estacao estacaoAtual) {
        this.modelo = modelo;
        this.estacaoAtual = estacaoAtual;

        this.codigoIdentificador = contadorCodigo;
        contadorCodigo++;
    }


    // ==================== INICIAR UTILIZAÇÃO ====================
    // Status: NÃO INICIADO

    public void iniciarUtilizacao() throws VeiculoIndisponivelException {

    }


    // ==================== FINALIZAR UTILIZAÇÃO ====================
    // Status: NÃO INICIADO

    public void finalizarUtilizacao(Estacao estacaoDestino, long minutos) {

    }


    // ==================== ENVIAR PARA MANUTENÇÃO ====================
    // Status: NÃO INICIADO

    public void enviarParaManutencao() throws OperacaoInvalidaException {

    }


    // ==================== RETORNAR DA MANUTENÇÃO ====================
    // Status: NÃO INICIADO

    public void retornarDaManutencao() throws OperacaoInvalidaException {

    }


    // ==================== CONSULTAR DISPONIBILIDADE ====================
    // Status: NÃO INICIADO

    public boolean podeSerLiberado() {
        return false;
    }


    // ==================== CONSULTAR ESTADO ATUAL ====================
    // Status: NÃO INICIADO

    public EstadoVeiculo consultarEstadoAtual() {
        return null;
    }


    // ==================== CONSULTAR ESTAÇÃO ATUAL ====================
    // Status: NÃO INICIADO

    public Estacao consultarEstacaoAtual() {
        return null;
    }


    // ==================== CONSULTAR MODELO ====================
    // Status: NÃO INICIADO

    public String consultarModelo() {
        return null;
    }


    // ==================== CONSULTAR CÓDIGO ====================
    // Status: NÃO INICIADO

    public int consultarCodigo() {
        return 0;
    }


    // ==================== CONSULTAR TARIFA DE DESBLOQUEIO ====================
    // Status: NÃO INICIADO

    public abstract double consultarTarifaDeDesbloqueio();


    // ==================== CONSULTAR TARIFA POR MINUTO ====================
    // Status: NÃO INICIADO

    public abstract double consultarTarifaPorMinuto();


    // ==================== CONSULTAR TIPO ====================
    // Status: NÃO INICIADO

    public abstract TipoVeiculo consultarTipo();


    // ==================== CONSULTAR DESCRIÇÃO ====================
    // Status: NÃO INICIADO

    public String consultarDescricao() {
        return null;
    }


    // ==================== VERIFICAR DESCONTO UNIVERSITÁRIO ====================
    // Status: NÃO INICIADO

    public boolean aceitaDescontoUniversitario() {
        return false;
    }


    // ==================== RECARREGAR BATERIA ====================
    // Status: NÃO INICIADO

    public void recarregarBateria() throws OperacaoInvalidaException {

    }
}