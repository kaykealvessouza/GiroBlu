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
    // Status: IMPLEMENTADO

    public Estacao(String nome, int capacidadeMaxima) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da estação não pode ser vazio.");
        }

        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade máxima deve ser maior que zero.");
        }

        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
        this.veiculosEstacionados = new ArrayList<>();
    }

    // ==================== ADICIONAR VEÍCULO ====================
    // Status: IMPLEMENTADO

    public void adicionarVeiculo(Veiculo veiculo) throws EstacaoLotadaException {

        if (veiculo == null) {
            throw new IllegalArgumentException("O veículo não pode ser nulo.");
        }

        if (consultarVagasDisponiveis() <= 0) {
            throw new EstacaoLotadaException("A estação " + nome + " está lotada");
        }
        if (veiculosEstacionados.contains(veiculo)) {
            throw new IllegalArgumentException("O veículo " + veiculo.consultarModelo() + " já está nesta estação.");
        }

        veiculosEstacionados.add(veiculo);
    }

    // ==================== REMOVER VEÍCULO ====================
    // Status: IMPLEMENTADO

    public void removerVeiculo(Veiculo veiculo) throws VeiculoNaoEncontradoException {
        if (veiculo == null) {
            throw new IllegalArgumentException("O veículo não pode ser nulo.");
        }

        boolean removido = veiculosEstacionados.remove(veiculo);

        if (!removido) {
            throw new VeiculoNaoEncontradoException(
                    "O veículo " + veiculo.consultarCodigo() + " não está na estação " + nome + ".");
        }
    }

    // ==================== CONSULTAR VAGAS DISPONÍVEIS ====================
    // Status: IMPLEMENTADO

    public int consultarVagasDisponiveis() {
        return capacidadeMaxima - consultarQuantidadeVeiculosEstacionados();
    }

    // ==================== CONSULTAR VEÍCULOS DISPONÍVEIS ====================
    // Status: IMPLEMENTADO

    public List<Veiculo> consultarVeiculosDisponiveis() {
        List<Veiculo> disponiveis = new ArrayList<>();

        for (Veiculo veiculo : veiculosEstacionados) {
            if (veiculo.consultarEstadoAtual() == EstadoVeiculo.DISPONIVEL) {
                disponiveis.add(veiculo);
            }
        }

        return Collections.unmodifiableList(disponiveis);
    }

    // ==================== CONSULTAR QUANTIDADE DE VEÍCULOS ESTACIONADOS
    // ====================
    // Status: IMPLEMENTADO

    public int consultarQuantidadeVeiculosEstacionados() {
        return veiculosEstacionados.size();
    }

    public String consultarNome() {
        return nome;
    }

    public int consultarCapacidadeMaxima(){
        return capacidadeMaxima;
    }
}