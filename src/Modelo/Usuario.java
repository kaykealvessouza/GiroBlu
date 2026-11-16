package Modelo;

import java.time.LocalDateTime;
import java.util.List;

import Excecoes.SemViagemEmAndamentoException;
import Excecoes.ValorInvalidoException;
import Excecoes.ViagemEmAndamentoException;

import Financeiro.LinhaExtrato;

import Interfaces.MovimentacaoFinanceira;
import Interfaces.Plano;

public class Usuario {

    // ==================== ATRIBUTOS ====================

    private String nome;
    private String email;
    private Viagem viagemAtiva;
    private Plano planoContratado;
    private Carteira carteira;


    // ==================== CONSTRUTOR ====================
    // Status: NÃO INICIADO

    public Usuario(String nome, String email, Plano planoContratado) {

    }


    // ==================== CONSULTAR NOME ====================
    // Status: NÃO INICIADO

    public String consultarNome() {
        return null;
    }


    // ==================== CONSULTAR E-MAIL ====================
    // Status: NÃO INICIADO

    public String consultarEmail() {
        return null;
    }


    // ==================== CONSULTAR PLANO CONTRATADO ====================
    // Status: NÃO INICIADO

    public Plano consultarPlanoContratado() {
        return null;
    }


    // ==================== CONSULTAR VIAGEM ATIVA ====================
    // Status: NÃO INICIADO

    public Viagem consultarViagemAtiva() {
        return null;
    }


    // ==================== VERIFICAR VIAGEM EM ANDAMENTO ====================
    // Status: NÃO INICIADO

    public boolean possuiViagemEmAndamento() {
        return false;
    }


    // ==================== VERIFICAR SALDO MÍNIMO ====================
    // Status: NÃO INICIADO

    public boolean temSaldoMinimo() {
        return false;
    }


    // ==================== REGISTRAR VIAGEM ATIVA ====================
    // Status: NÃO INICIADO

    public void registrarViagemAtiva(Viagem viagem) throws ViagemEmAndamentoException {

    }


    // ==================== FINALIZAR VIAGEM ATIVA ====================
    // Status: NÃO INICIADO

    public void finalizarViagemAtiva(Viagem viagem) throws SemViagemEmAndamentoException {

    }


    // ==================== RECARREGAR CARTEIRA ====================
    // Status: NÃO INICIADO

    public void recarregarCarteira(double valor, LocalDateTime dataHora)
            throws ValorInvalidoException {

    }


    // ==================== PAGAR VIAGEM ====================
    // Status: NÃO INICIADO

    public void pagarViagem(Viagem viagem) {

    }


    // ==================== CONSULTAR SALDO ====================
    // Status: NÃO INICIADO

    public double consultarSaldo() {
        return 0;
    }


    // ==================== CONSULTAR HISTÓRICO DE MOVIMENTAÇÕES ====================
    // Status: NÃO INICIADO

    public List<MovimentacaoFinanceira> consultarHistoricoMovimentacoes() {
        return null;
    }


    // ==================== CONSULTAR EXTRATO ====================
    // Status: NÃO INICIADO

    public List<LinhaExtrato> consultarExtrato() {
        return null;
    }

}