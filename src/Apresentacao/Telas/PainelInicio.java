
package Apresentacao.Telas;

import javax.swing.*;

import java.awt.*;
import java.util.List;

import Aplicacao.Sistema;
import Apresentacao.TelaPrincipal;
import Modelo.EstadoVeiculo;
import Modelo.Viagem;

public class PainelInicio extends JPanel {

    private Sistema sistema;
    private TelaPrincipal telaPrincipal;

    private JLabel lblEstacoes;
    private JLabel lblUsuarios;
    private JLabel lblVeiculosDisponiveis;
    private JLabel lblVeiculosEmUso;
    private JLabel lblVeiculosManutencao;
    private JLabel lblViagens;

    public PainelInicio(Sistema sistema, TelaPrincipal telaPrincipal) {

        this.sistema = sistema;
        this.telaPrincipal = telaPrincipal;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(237, 241, 244));

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        configurarPainel();
        atualizarResumo();
    }

    // ==================== CONFIGURAR PAINEL ====================

    private void configurarPainel() {

        JLabel lblTitulo = new JLabel("Visão geral");

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(52, 73, 94));

        JLabel lblSubtitulo = new JLabel("Resumo do sistema GiroBlu");

        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(100, 116, 139));

        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new BoxLayout(painelTitulo, BoxLayout.Y_AXIS));
        painelTitulo.setBackground(new Color(237, 241, 244));

        painelTitulo.add(lblTitulo);
        painelTitulo.add(Box.createVerticalStrut(5));
        painelTitulo.add(lblSubtitulo);

        add(painelTitulo, BorderLayout.NORTH);

        JPanel painelCartoes = new JPanel(new GridLayout(2, 2, 15, 15));
        painelCartoes.setBackground(new Color(237, 241, 244));

        painelCartoes.add(criarCartaoEstacoes());
        painelCartoes.add(criarCartaoVeiculos());
        painelCartoes.add(criarCartaoUsuarios());
        painelCartoes.add(criarCartaoViagens());

        add(painelCartoes, BorderLayout.CENTER);
    }

    // ==================== CARTÃO DE ESTAÇÕES ====================

    private JPanel criarCartaoEstacoes() {

        JPanel painel = criarCartao("Estações");

        lblEstacoes = criarValor();

        painel.add(lblEstacoes);
        painel.add(Box.createVerticalStrut(10));

        adicionarBotao(painel, "Acessar", "estacoes", "Estações");

        return painel;
    }

    // ==================== CARTÃO DE VEÍCULOS ====================

    private JPanel criarCartaoVeiculos() {

        JPanel painel = criarCartao("Veículos por estado");

        lblVeiculosDisponiveis = criarDetalhe();
        lblVeiculosEmUso = criarDetalhe();
        lblVeiculosManutencao = criarDetalhe();

        painel.add(lblVeiculosDisponiveis);
        painel.add(lblVeiculosEmUso);
        painel.add(lblVeiculosManutencao);

        painel.add(Box.createVerticalStrut(10));

        adicionarBotao(painel, "Acessar", "veiculos", "Veículos");

        return painel;
    }

    // ==================== CARTÃO DE USUÁRIOS ====================

    private JPanel criarCartaoUsuarios() {

        JPanel painel = criarCartao("Usuários");

        lblUsuarios = criarValor();

        painel.add(lblUsuarios);
        painel.add(Box.createVerticalStrut(10));

        adicionarBotao(painel, "Acessar", "usuarios", "Usuários");

        return painel;
    }

    // ==================== CARTÃO DE VIAGENS ====================

    private JPanel criarCartaoViagens() {

        JPanel painel = criarCartao("Viagens em andamento");

        lblViagens = criarValor();

        painel.add(lblViagens);
        painel.add(Box.createVerticalStrut(10));

        adicionarBotao(painel, "Acessar", "viagens", "Viagens");

        return painel;
    }

    // ==================== CRIAR CARTÃO ====================

    private JPanel criarCartao(String titulo) {

        JPanel painel = new JPanel();

        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);

        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 231)),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)));

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(52, 73, 94));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(lblTitulo);
        painel.add(Box.createVerticalStrut(15));

        return painel;
    }

    // ==================== CRIAR VALOR ====================

    private JLabel criarValor() {

        JLabel label = new JLabel("0");

        label.setFont(new Font("Segoe UI", Font.BOLD, 32));
        label.setForeground(new Color(57, 143, 193));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    // ==================== CRIAR DETALHE ====================

    private JLabel criarDetalhe() {

        JLabel label = new JLabel();

        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(80, 95, 110));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    // ==================== ADICIONAR BOTÃO ====================

    private void adicionarBotao(
            JPanel painel,
            String texto,
            String nomePainel,
            String titulo) {

        JButton botao = new JButton(texto);

        botao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(57, 143, 193));

        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.setPreferredSize(new Dimension(100, 32));
        botao.setMaximumSize(new Dimension(100, 32));
        botao.setAlignmentX(Component.LEFT_ALIGNMENT);

        botao.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(45, 115, 155));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(new Color(57, 143, 193));
            }
        });

        botao.addActionListener(e -> telaPrincipal.navegarPara(nomePainel, titulo));

        painel.add(botao);
    }

    // ==================== ATUALIZAR RESUMO ====================

    public void atualizarResumo() {

        lblEstacoes.setText(
                String.valueOf(sistema.consultarEstacoes().size()));

        lblUsuarios.setText(
                String.valueOf(sistema.consultarUsuarios().size()));

        int disponiveis = sistema.consultarVeiculos(
                null, null, EstadoVeiculo.DISPONIVEL, null).size();

        int emUso = sistema.consultarVeiculos(
                null, null, EstadoVeiculo.EM_USO, null).size();

        int manutencao = sistema.consultarVeiculos(
                null, null, EstadoVeiculo.MANUTENCAO, null).size();

        lblVeiculosDisponiveis.setText("Disponíveis: " + disponiveis);
        lblVeiculosEmUso.setText("Em uso: " + emUso);
        lblVeiculosManutencao.setText("Em manutenção: " + manutencao);

        List<Viagem> viagens = sistema.consultarViagens();

        int viagensEmAndamento = 0;

        for (Viagem viagem : viagens) {

            if (viagem.estaEmAndamento()) {
                viagensEmAndamento++;
            }
        }

        lblViagens.setText(String.valueOf(viagensEmAndamento));
    }
}
