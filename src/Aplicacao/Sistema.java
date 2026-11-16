package Aplicacao;

import java.time.*;
import java.util.*;

import Excecoes.*;
import Interfaces.*;
import Modelo.*;
import Planos.*;
import Relatorios.*;

public class Sistema {

    // ==================== ATRIBUTOS ====================

    private ArrayList<Estacao> estacoes;
    private ArrayList<Veiculo> veiculos;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Viagem> viagens;
    private ArrayList<Plano> planosDisponiveis;


    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public Sistema() {
        this.estacoes = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.viagens = new ArrayList<>();

        this.planosDisponiveis = new ArrayList<>();
        this.planosDisponiveis.add(new PlanoAvulso());
        this.planosDisponiveis.add(new PlanoMensal());
        this.planosDisponiveis.add(new PlanoUniversitario());
    }


    // ==================== CADASTRO DE ESTAÇÃO ====================
    // Status: NÃO INICIADO

    public void cadastrarEstacao(Estacao estacao) throws RegistroJaExistenteException {

    }


    // ==================== CADASTRO DE VEÍCULO ====================
    // Status: NÃO INICIADO

    public void cadastrarVeiculo(Veiculo veiculo) throws EstacaoLotadaException {

    }


    // ==================== CADASTRO DE USUÁRIO ====================
    // Status: NÃO INICIADO

    public void cadastrarUsuario(Usuario usuario) throws RegistroJaExistenteException {

    }


    // ==================== CONSULTA DE ESTAÇÃO ====================
    // Status: NÃO INICIADO

    public Estacao consultarEstacao(String nome) throws RegistroNaoEncontradoException {
        return null;
    }

    public List<Estacao> consultarEstacoes() {
        return Collections.unmodifiableList(estacoes);
    }


    // ==================== CONSULTA DE VEÍCULO ====================
    // Status: NÃO INICIADO

    public Veiculo consultarVeiculo(int codigo) throws RegistroNaoEncontradoException {
        return null;
    }

    public List<Veiculo> consultarVeiculos() {
        return Collections.unmodifiableList(veiculos);
    }


    // ==================== CONSULTA DE USUÁRIO ====================
    // Status: NÃO INICIADO

    public Usuario consultarUsuario(String email) throws RegistroNaoEncontradoException {
        return null;
    }

    public List<Usuario> consultarUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }


    // ==================== CONSULTA DE VIAGENS ====================
    // Status: NÃO INICIADO

    public List<Viagem> consultarViagensDoUsuario(Usuario usuario) {
        return new ArrayList<>();
    }

    public List<Viagem> consultarViagens() {
        return Collections.unmodifiableList(viagens);
    }


    // ==================== CONSULTA DE PLANOS ====================
    // Status: NÃO INICIADO

    public List<Plano> consultarPlanos() {
        return Collections.unmodifiableList(planosDisponiveis);
    }


    // ==================== INICIAR VIAGEM ====================
    // Status: NÃO INICIADO

    public void iniciarViagem(Usuario usuario, Veiculo veiculo, LocalDateTime dataHoraInicio)
            throws ViagemEmAndamentoException,
                   SaldoInsuficienteException,
                   VeiculoIndisponivelException,
                   VeiculoNaoEncontradoException {

    }


    // ==================== FINALIZAR VIAGEM ====================
    // Status: NÃO INICIADO

    public void finalizarViagem(Viagem viagem, Estacao destino, LocalDateTime dataHoraFim)
            throws SemViagemEmAndamentoException,
                   DataInvalidaException,
                   EstacaoLotadaException {

    }


    // ==================== CONSULTA DE FATURAMENTO ====================
    // Status: NÃO INICIADO

    public RelatorioFaturamento consultarFaturamento(LocalDate dataInicio, LocalDate dataFim)
            throws DataInvalidaException {
        return null;
    }
}