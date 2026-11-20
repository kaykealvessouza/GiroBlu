package Modelo;

import java.time.LocalDateTime;
import java.util.List;

import Excecoes.SemViagemEmAndamentoException;
import Excecoes.ValorInvalidoException;
import Excecoes.ViagemEmAndamentoException;

import Financeiro.LinhaExtrato;
import Financeiro.Recarga;
import Interfaces.Plano;

public class Usuario {

    // ==================== ATRIBUTOS ====================

    private String nome;
    private String email;
    private Viagem viagemAtiva;
    private Plano planoContratado;
    private Carteira carteira;

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    public Usuario(String nome, String email, Plano planoContratado) {
        this.nome = nome;
        this.email = email;
        this.planoContratado = planoContratado;
        this.carteira = new Carteira();
    }

    // ==================== CONSULTAR NOME ====================
    // Status: IMPLEMENTADO

    public String consultarNome() {
        return nome;
    }

    // ==================== CONSULTAR E-MAIL ====================
    // Status: IMPLEMENTADO

    public String consultarEmail() {
        return email;
    }

    // ==================== CONSULTAR PLANO CONTRATADO ====================
    // Status: IMPLEMENTADO

    public Plano consultarPlanoContratado() {
        return planoContratado;
    }

    // ==================== CONSULTAR VIAGEM ATIVA ====================
    // Status: IMPLEMENTADO

    public Viagem consultarViagemAtiva() {
        return viagemAtiva;
    }

    // ==================== VERIFICAR VIAGEM EM ANDAMENTO ====================
    // Status: IMPLEMENTADO
    public boolean possuiViagemEmAndamento() {
        return viagemAtiva != null;
    }

    // ==================== VERIFICAR SALDO MÍNIMO ====================
    // Status: IMPLEMENTADO
    public boolean temSaldoMinimo() {
        return carteira.temSaldoMinimo();
    }

    // ==================== REGISTRAR VIAGEM ATIVA ====================
    // Status: IMPLEMENTADO
    public void registrarViagemAtiva(Viagem viagem) throws ViagemEmAndamentoException {
        if(!possuiViagemEmAndamento()){
            throw new ViagemEmAndamentoException("O usuário " + nome + " já possui uma viagem em andamento");
        }
        this.viagemAtiva = viagem;
    }

    // ==================== FINALIZAR VIAGEM ATIVA ====================
    // Status: IMPLEMENTADO
    public void finalizarViagemAtiva(Viagem viagem) throws SemViagemEmAndamentoException {
        if(!possuiViagemEmAndamento() || viagemAtiva != viagem ){
            throw new SemViagemEmAndamentoException("O usuário " + nome + " não possui esta viagem em andamento.");
        }
        this.viagemAtiva = null;
    }

    // ==================== RECARREGAR CARTEIRA ====================
    // Status: IMPLEMENTADO
    public void recarregarCarteira(double valor, LocalDateTime dataHora)
            throws ValorInvalidoException {
        Recarga recarga = new Recarga(valor, dataHora);
        carteira.adicionarSaldo(recarga);
    }

    // ==================== PAGAR VIAGEM ====================
    // Status: IMPLEMENTADO
    public void pagarViagem(Viagem viagem) {
        carteira.debitarValor(viagem);
    }

    // ==================== CONSULTAR SALDO ====================
    // Status: IMPLEMENTADO
    public double consultarSaldo() {
        return carteira.consultarSaldoAtual();
    }

    // ==================== CONSULTAR EXTRATO ====================
    // Status: IMPLEMENTADO

    public List<LinhaExtrato> consultarExtrato() {
        return carteira.consultarExtrato();
    }

}