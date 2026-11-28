package Modelo;

import Excecoes.OperacaoInvalidaException;
import Excecoes.ValorInvalidoException;

public abstract class VeiculoEletrico extends Veiculo{

    // ==================== ATRIBUTOS ====================

    private int nivelBateria;
    private static final long serialVersionUID = 1L;

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    protected VeiculoEletrico(String modelo, Estacao estacaoAtual, int nivelBateria)
            throws ValorInvalidoException {
        super(modelo, estacaoAtual);

        if (nivelBateria < 0 || nivelBateria > 100) {
            throw new ValorInvalidoException("O nível da bateria deve estar entre 0 e 100");
        }

        this.nivelBateria = nivelBateria;
    }

    // ==================== CONSULTAR NÍVEL DA BATERIA ====================
    // Status: IMPLEMENTADO

    @Override 
    public int consultarNivelBateria() {
        return nivelBateria;
    }   

    // ==================== VERIFICAR CARGA MÍNIMA PARA VIAGEM ====================
    // Status: IMPLEMENTADO

    protected boolean possuiCargaMinimaParaViagem() {
        return nivelBateria >= 20; // True se for 20% ou mais
    }

    // ==================== CONSULTAR DISPONIBILIDADE ====================
    // Status: IMPLEMENTADO

    @Override
    public boolean podeSerLiberado() {
        return super.podeSerLiberado() && possuiCargaMinimaParaViagem();
        // Retorna true se tiver carga e se poder ser liberado da classe Veiculo (Se
        // tiver disponível)
    }

    // ==================== CONSUMIR BATERIA ====================
    // Status: IMPLEMENTADO

    protected void consumirBateria(long minutos) {
        if (minutos <= 0) {
            throw new IllegalArgumentException("A duração deve ser maior que zero.");
        }

        int bateriaConsumida = (int) (minutos * consultarConsumoPorMinuto());

        nivelBateria = Math.max(0, nivelBateria - bateriaConsumida);
        // Retorna o maior entre 0 e a subtração, assim a carga nunca fica negativa
    }

    // ==================== CONSULTAR CONSUMO POR MINUTO ====================
    // Status: DECLARADO

    public abstract int consultarConsumoPorMinuto();

    // ==================== FINALIZAR UTILIZAÇÃO ====================
    // Status: IMPLEMENTADO

    @Override
    public void finalizarUtilizacao(Estacao estacaoDestino, long minutos) {
        super.finalizarUtilizacao(estacaoDestino, minutos);

        consumirBateria(minutos);
    }

    // ==================== RECARREGAR BATERIA ====================
    // Status: IMPLEMENTADO

    @Override
    public void recarregarBateria() throws OperacaoInvalidaException {
        if (consultarEstadoAtual() == EstadoVeiculo.EM_USO) {
            throw new OperacaoInvalidaException("Não é possível recarregar um veículo em uso!");
        }

        nivelBateria = 100;
    }

    // ==================== CONSULTAR DESCRIÇÃO ====================
    // Status: IMPLEMENTADO

    @Override
    public String consultarDescricao() {
        return super.consultarDescricao() + " | Bateria: " +
                consultarNivelBateria() + "%";
    }
}