
package Apresentacao;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import Aplicacao.Sistema;
import Apresentacao.Telas.PainelInicio;
import Apresentacao.Telas.Carteiras.PainelCarteira;
import Apresentacao.Telas.Estacoes.PainelEstacoes;
import Apresentacao.Telas.Relatorio.PainelRelatorioFaturamento;
import Apresentacao.Telas.Usuarios.PainelUsuarios;
import Apresentacao.Telas.Veiculos.PainelVeiculos;
import Apresentacao.Telas.Viagens.PainelViagens;

public class TelaPrincipal extends JFrame {

    // ========================================
    // COMPONENTES PRINCIPAIS
    // ========================================

    private JPanel painelPrincipal;
    private JPanel painelMenu;
    private JPanel painelCabecalho;
    private JPanel painelConteudo;
    private JPanel painelDireita;

    private JLabel lblTituloTela = new JLabel("Tela Inicial");

    // User do windos - Inventando moda praticamente, só pra exibir bonitinho
    private JLabel lblUsuario = new JLabel(
            "Usuário do Windows: " + System.getProperty("user.name"));

    // Gerenciador de telas
    private CardLayout cardLayout = new CardLayout();

    // ========================================
    // BOTÕES DO MENU
    // ========================================

    private JButton btnInicio;
    private JButton btnEstacoes;
    private JButton btnVeiculos;
    private JButton btnUsuarios;
    private JButton btnViagens;
    private JButton btnCarteiras;
    private JButton btnRelatorios;

    // Referência ao botão atualmente selecionado
    private JButton botaoSelecionado;

    private PainelInicio painelInicio;

    Sistema sistema;

    // ========================================
    // CONSTRUTOR
    // ========================================

    public TelaPrincipal(Sistema sistema) {
        super("GiroBlu | Painel de Operação");

        this.sistema = sistema;

        // ========================================
        // INICIALIZAÇÃO DOS PAINÉIS
        // ========================================

        painelPrincipal = new JPanel(new BorderLayout());

        painelMenu = new JPanel();
        painelCabecalho = new JPanel(new BorderLayout());
        painelConteudo = new JPanel(cardLayout);
        painelDireita = new JPanel(new BorderLayout());

        painelInicio = new PainelInicio(sistema, this);
        painelConteudo.add(painelInicio, "inicio");

        // ========================================
        // CONFIGURAÇÃO DO CABEÇALHO
        // ========================================

        // Título da tela
        painelCabecalho.add(lblTituloTela, BorderLayout.WEST);

        lblTituloTela.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTituloTela.setForeground(new Color(52, 73, 94));

        // Usuário do sistema
        painelCabecalho.add(lblUsuario, BorderLayout.EAST);

        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(new Color(100, 116, 139));

        // Aparência do cabeçalho
        painelCabecalho.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20));

        painelCabecalho.setBackground(new Color(232, 239, 244));

        // ========================================
        // CONFIGURAÇÃO DO CONTEÚDO
        // ========================================

        painelConteudo.setBackground(new Color(237, 241, 244));

        // ========================================
        // CONFIGURAÇÃO DO MENU LATERAL
        // ========================================

        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));

        painelMenu.setPreferredSize(new Dimension(150, 600));

        painelMenu.setBorder(
                BorderFactory.createEmptyBorder(20, 12, 20, 12));

        painelMenu.setBackground(new Color(57, 143, 193));

        // ========================================
        // CRIAÇÃO DOS BOTÕES DO MENU
        // ========================================

        btnInicio = criarBotaoMenu("Início");
        btnEstacoes = criarBotaoMenu("Estações");
        btnVeiculos = criarBotaoMenu("Veículos");
        btnUsuarios = criarBotaoMenu("Usuários");
        btnViagens = criarBotaoMenu("Viagens");
        btnCarteiras = criarBotaoMenu("Carteiras");
        btnRelatorios = criarBotaoMenu("Relatórios");

        // ========================================
        // ADICIONA OS BOTÕES AO MENU
        // ========================================

        painelMenu.add(btnInicio);
        painelMenu.add(btnEstacoes);
        painelMenu.add(btnVeiculos);
        painelMenu.add(btnUsuarios);
        painelMenu.add(btnViagens);
        painelMenu.add(btnCarteiras);
        painelMenu.add(btnRelatorios);

        // ========================================
        // MONTAGEM DA ESTRUTURA PRINCIPAL
        // ========================================

        // Área direita recebe cabeçalho e conteúdo
        painelDireita.add(painelCabecalho, BorderLayout.NORTH);
        painelDireita.add(painelConteudo, BorderLayout.CENTER);

        // Painel principal recebe menu e área direita
        painelPrincipal.add(painelMenu, BorderLayout.WEST);
        painelPrincipal.add(painelDireita, BorderLayout.CENTER);

        // JFrame recebe o painel principal
        add(painelPrincipal);

        // ========================================
        // INICIALIZAÇÃO DAS TELAS
        // ========================================

        painelConteudo.add(new PainelEstacoes(sistema), "estacoes");
        painelConteudo.add(new PainelVeiculos(sistema), "veiculos");
        painelConteudo.add(new PainelUsuarios(sistema), "usuarios");
        painelConteudo.add(new PainelViagens(sistema), "viagens");
        painelConteudo.add(new PainelCarteira(sistema), "carteiras");
        painelConteudo.add(new PainelRelatorioFaturamento(sistema), "relatorios");

        // Exibe a tela inicial
        cardLayout.show(painelConteudo, "inicio");

        // Define o botão Início como selecionado
        selecionarBotao(btnInicio);

        // ========================================
        // CONFIGURAÇÃO DA NAVEGAÇÃO
        // ========================================

        configurarNavegacao(btnInicio, "inicio", "Tela Inicial");
        configurarNavegacao(btnEstacoes, "estacoes", "Estações");
        configurarNavegacao(btnVeiculos, "veiculos", "Veículos");
        configurarNavegacao(btnUsuarios, "usuarios", "Usuários");
        configurarNavegacao(btnViagens, "viagens", "Viagens");
        configurarNavegacao(btnCarteiras, "carteiras", "Carteiras");
        configurarNavegacao(btnRelatorios, "relatorios", "Relatórios");

        // ========================================
        // CONFIGURAÇÕES DA JANELA
        // ========================================

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ========================================
    // MÉTODOS AUXILIARES
    // ========================================

    // Cria os botões do menu com aparência padronizada
    private JButton criarBotaoMenu(String texto) {

        JButton botao = new JButton(texto);

        botao.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        botao.setForeground(Color.WHITE);
        botao.setHorizontalAlignment(JButton.LEFT);

        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);

        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        // Mouse hover: destaca o botão quando o cursor está sobre ele
        botao.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {

                if (botao != botaoSelecionado) {
                    botao.setOpaque(true);
                    botao.setBackground(new Color(80, 160, 205));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {

                if (botao != botaoSelecionado) {
                    botao.setOpaque(false);
                }
            }
        });

        return botao;
    }

    // Controla a seleção visual dos botões do menu
    private void selecionarBotao(JButton novoBotao) {

        // Remove o destaque do botão anteriormente selecionado
        if (botaoSelecionado != null) {
            botaoSelecionado.setOpaque(false);
            botaoSelecionado.setBackground(null);
        }

        // Atualiza a referência do botão selecionado
        botaoSelecionado = novoBotao;

        // Aplica o destaque ao novo botão
        botaoSelecionado.setOpaque(true);
        botaoSelecionado.setBackground(new Color(45, 115, 155));

        // Remove a borda visual de foco
        botaoSelecionado.setFocusPainted(false);
    }

    // Configura a navegação e a seleção visual de um botão
    private void configurarNavegacao(JButton botao, String nomePainel, String titulo) {

        botao.addActionListener(e -> {

            if (nomePainel.equals("inicio")) {
                painelInicio.atualizarResumo();
            }

            // Troca a tela exibida
            cardLayout.show(painelConteudo, nomePainel);

            // Atualiza o título do cabeçalho
            lblTituloTela.setText(titulo);

            // Atualiza o botão selecionado
            selecionarBotao(botao);
        });
    }

    public void navegarPara(String nomePainel, String titulo) {

        if (nomePainel.equals("inicio")) {
            painelInicio.atualizarResumo();
        }

        cardLayout.show(painelConteudo, nomePainel);

        lblTituloTela.setText(titulo);

        switch (nomePainel) {
            case "inicio":
                selecionarBotao(btnInicio);
                break;

            case "estacoes":
                selecionarBotao(btnEstacoes);
                break;

            case "veiculos":
                selecionarBotao(btnVeiculos);
                break;

            case "usuarios":
                selecionarBotao(btnUsuarios);
                break;

            case "viagens":
                selecionarBotao(btnViagens);
                break;

            case "carteiras":
                selecionarBotao(btnCarteiras);
                break;

            case "relatorios":
                selecionarBotao(btnRelatorios);
                break;
        }
    }
}
