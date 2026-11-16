package Modelo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Excecoes.EstacaoLotadaException;
import Excecoes.VeiculoNaoEncontradoException;

public class Estacao {

    // ==================== ATRIBUTOS ====================

    private String nome;
    private int capacidadeMaxima;
    private ArrayList<Veiculo> veiculosEstacionados;


    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public Estacao(String nome, int capacidadeMaxima) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.veiculosEstacionados = new ArrayList<>();
    }


    // ==================== ADICIONAR VEÍCULO ====================
    // Status: NÃO INICIADO

    public void adicionarVeiculo(Veiculo veiculo) throws EstacaoLotadaException {

    }


    // ==================== REMOVER VEÍCULO ====================
    // Status: NÃO INICIADO

    public void removerVeiculo(Veiculo veiculo) throws VeiculoNaoEncontradoException {

    }


    // ==================== CONSULTAR VAGAS DISPONÍVEIS ====================
    // Status: NÃO INICIADO

    public int consultarVagasDisponiveis() {
        return 0;
    }


    // ==================== CONSULTAR VEÍCULOS DISPONÍVEIS ====================
    // Status: NÃO INICIADO

    public List<Veiculo> consultarVeiculosDisponiveis() {
        return Collections.emptyList();
    }


    // ==================== CONSULTAR QUANTIDADE DE VEÍCULOS ESTACIONADOS ====================
    // Status: NÃO INICIADO

    public int consultarQuantidadeVeiculosEstacionados() {
        return 0;
    }

}