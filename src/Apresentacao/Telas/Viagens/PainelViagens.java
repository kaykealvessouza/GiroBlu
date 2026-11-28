package Apresentacao.Telas.Viagens;

import javax.swing.JPanel;

import java.awt.event.MouseEvent;

import Aplicacao.Sistema;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;

public class PainelViagens extends JPanel {

    // Botões responsáveis pela navegação interna
    private JButton btnIniciarViagem;
    private JButton btnEncerrarViagem;
    private JButton btnConsultarViagem;

    // Guarda a referência do botão atualmente selecionado
    private JButton botaoSelecionado;

    // Painéis que organizam a navegação e o conteúdo
    private JPanel painelNavegacao;
    private JPanel painelConteudo;

    // Controla a alternância entre os conteúdos
    private CardLayout cardLayout;

    private Sistema sistema;

    public PainelViagens(Sistema sistema) {

        this.sistema = sistema;

        // Define a organização principal do painel
        setLayout(new BorderLayout());
        setBackground(new Color(237, 241, 244));

        // Monta a navegação e a área de conteúdo
        configurarNavegacao();
        configurarConteudo();
    }

    // Configura a barra de navegação com as abas
    private void configurarNavegacao() {

        painelNavegacao = new JPanel();

        // Organiza os botões horizontalmente
        painelNavegacao.setLayout(
            new BoxLayout(painelNavegacao, BoxLayout.X_AXIS)
        );

        painelNavegacao.setBackground(new Color(237, 241, 244));

        // Define o espaçamento interno da barra
        painelNavegacao.setBorder(
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );

        // Cria os botões utilizando o mesmo padrão visual da estrutura principal
        btnIniciarViagem = criarBotaoAba("Iniciar Viagem");
        btnEncerrarViagem = criarBotaoAba("Encerrar Viagem");
        btnConsultarViagem = criarBotaoAba("Consultar Viagem");

        painelNavegacao.add(btnIniciarViagem);
        painelNavegacao.add(Box.createHorizontalStrut(10));
        painelNavegacao.add(btnEncerrarViagem);
        painelNavegacao.add(Box.createHorizontalStrut(10));
        painelNavegacao.add(btnConsultarViagem);

        // Adiciona a navegação na parte superior do painel
        add(painelNavegacao, BorderLayout.NORTH);
    }

    // Cria um botão com aparência e comportamento de aba
    private JButton criarBotaoAba(String texto) {

        JButton botao = new JButton(texto);

        botao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        botao.setForeground(new Color(52, 73, 94));

        // Remove as aparências padrão do botão
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);

        // Define as dimensões do botão
        botao.setPreferredSize(new Dimension(160, 40));
        botao.setMaximumSize(new Dimension(160, 40));

        // Controla o mouse hover tbm
        botao.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                // Não altera a aparência da aba selecionada
                if (botao != botaoSelecionado) {
                    botao.setOpaque(true);
                    botao.setBackground(new Color(80, 160, 205));
                    botao.setForeground(Color.WHITE);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                // Remove o destaque de hover ao retirar o mouse
                if (botao != botaoSelecionado) {
                    botao.setOpaque(false);
                    botao.setForeground(new Color(52, 73, 94));
                }
            }
        });

        return botao;
    }

    // Define qual aba está selecionada visualmente
    private void selecionarAba(JButton novaAba) {

        // Remove o destaque da aba anteriormente selecionada
        if (botaoSelecionado != null) {
            botaoSelecionado.setOpaque(false);
            botaoSelecionado.setBackground(null);
            botaoSelecionado.setForeground(new Color(52, 73, 94));
        }

        // Atualiza a referência da aba selecionada
        botaoSelecionado = novaAba;

        // Aplica o destaque visual
        botaoSelecionado.setOpaque(true);
        botaoSelecionado.setBackground(new Color(45, 115, 155));
        botaoSelecionado.setForeground(Color.WHITE);
    }

    // Configura os conteúdos e a navegação entre as abas
    private void configurarConteudo() {

        cardLayout = new CardLayout();
        painelConteudo = new JPanel(cardLayout);

        painelConteudo.add(new PainelIniciarViagem(sistema), "iniciarViagem");
        painelConteudo.add(new PainelEncerrarViagem(sistema), "encerrarViagem");
        painelConteudo.add(new PainelConsultarViagem(sistema), "consultarViagem");

        add(painelConteudo, BorderLayout.CENTER);

        // Ao clicar, exibe a consulta e seleciona sua aba
        btnIniciarViagem.addActionListener(e -> {
            cardLayout.show(painelConteudo, "iniciarViagem");
            selecionarAba(btnIniciarViagem);
        });

        // Ao clicar, exibe o cadastro e seleciona sua aba
        btnEncerrarViagem.addActionListener(e -> {
            cardLayout.show(painelConteudo, "encerrarViagem");
            selecionarAba(btnEncerrarViagem);
        });

        // Ao clicar, exibe o gerenciamento e seleciona sua aba
        btnConsultarViagem.addActionListener(e -> {
            cardLayout.show(painelConteudo, "consultarViagem");
            selecionarAba(btnConsultarViagem);
        });

        // Define a consulta como conteúdo inicial
        cardLayout.show(painelConteudo, "iniciarViagem");
        selecionarAba(btnIniciarViagem);
    }
}