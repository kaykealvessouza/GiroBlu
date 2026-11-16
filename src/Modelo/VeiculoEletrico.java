package Modelo;

import Excecoes.OperacaoInvalidaException;
import Excecoes.ValorInvalidoException;

public abstract class VeiculoEletrico extends Veiculo {

    // ==================== ATRIBUTOS ====================

    private int nivelBateria;


    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    protected VeiculoEletrico(String modelo, Estacao estacaoAtual, int nivelBateria)
            throws ValorInvalidoException {
                super(modelo, estacaoAtual);
    }


    // ==================== RECARREGAR BATERIA ====================
    // Status: NÃO INICIADO

    @Override
    public void recarregarBateria() throws OperacaoInvalidaException {

    }


    // ==================== VERIFICAR CARGA MÍNIMA PARA VIAGEM ====================
    // Status: NÃO INICIADO

    protected boolean possuiCargaMinimaParaViagem() {
        return false;
    }


    // ==================== CONSULTAR NÍVEL DA BATERIA ====================
    // Status: NÃO INICIADO

    public int consultarNivelBateria() {
        return 0;
    }


    // ==================== CONSUMIR BATERIA ====================
    // Status: NÃO INICIADO

    protected void consumirBateria(long minutos) {

    }


    // ==================== CONSULTAR CONSUMO POR MINUTO ====================
    // Status: NÃO INICIADO

    public abstract int consultarConsumoPorMinuto();


    // ==================== FINALIZAR UTILIZAÇÃO ====================
    // Status: NÃO INICIADO

    @Override
    public void finalizarUtilizacao(Estacao estacaoDestino, long minutos) {

    }


    // ==================== CONSULTAR DISPONIBILIDADE ====================
    // Status: NÃO INICIADO

    @Override
    public boolean podeSerLiberado() {
        return false;
    }


    // ==================== CONSULTAR DESCRIÇÃO ====================
    // Status: NÃO INICIADO

    @Override
    public String consultarDescricao() {
        return null;
    }

}