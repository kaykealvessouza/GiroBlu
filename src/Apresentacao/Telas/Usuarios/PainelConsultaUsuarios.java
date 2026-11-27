package Apresentacao.Telas.Usuarios;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.table.DefaultTableModel;

import Aplicacao.Sistema;
import Modelo.Estacao;
import Apresentacao.Telas.Estacoes.PainelEstacoes;
import Excecoes.RegistroNaoEncontradoException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class PainelConsultaUsuarios extends JPanel {

    private JTextField txtPesquisa;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;

    private JButton btnPesquisar;

    private JLabel lblMensagem;

    private Sistema sistema;
    private PainelUsuarios painelUsuarios;

    public PainelConsultaUsuarios(Sistema sistema, PainelUsuarios painelUsuarios) {

        this.sistema = sistema;
        this.painelUsuarios = painelUsuarios;

        setLayout(new BorderLayout());
        setBackground(new Color(237, 241, 244));

        // Cria o campo de pesquisa
        txtPesquisa = criarCampoPesquisa();

        // Configura cada parte da interface
        configurarPesquisa();
        configurarTabela();

        // Carrega as estações existentes cadastradas na tabela
        consultarEstacoes();
    }

    private JTextField criarCampoPesquisa() {

        JTextField campo = new JTextField();

        String placeholder = "Digite o nome da estação...";

        // Escrita que vai aparecer quando nada estiver digitado
        campo.setText(placeholder);
        campo.setForeground(Color.GRAY);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        campo.setPreferredSize(new Dimension(300, 35));

        campo.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                // Quando o campo recebe o foco, remove o placeholder
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                // Se o usuário não digitou nada, restaura o placeholder
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                }
            }
        });

        return campo;
    }

    private void configurarTabela() {

        modeloTabela = new DefaultTableModel(
                new Object[] { "Nome", "Capacidade", "Ocupação" }, 0) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabelaUsuarios = new JTable(modeloTabela);

        tabelaUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaUsuarios.setRowHeight(35);

        // Só da pra selecionar um registro
        tabelaUsuarios.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tabelaUsuarios.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        tabelaUsuarios.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int linhaSelecionada = tabelaUsuarios.getSelectedRow();

                    if (linhaSelecionada >= 0) {

                        int linhaModelo = tabelaUsuarios.convertRowIndexToModel(
                                linhaSelecionada);

                        Estacao estacao = sistema.consultarEstacoes().get(linhaModelo);

                        painelUsuarios.abrirConsultaVeiculos(estacao);
                    }
                }
            }
        });

        // O JScrollPane adiciona a rolagem à tabela
        JScrollPane scrollTabela = new JScrollPane(tabelaUsuarios);

        add(scrollTabela, BorderLayout.CENTER);
    }

    private void configurarPesquisa() {

        JPanel painelPesquisa = new JPanel(new BorderLayout(5, 0));

        painelPesquisa.setBackground(new Color(237, 241, 244));
        painelPesquisa.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));

        btnPesquisar = new JButton("Pesquisar");

        btnPesquisar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnPesquisar.setForeground(Color.WHITE);
        btnPesquisar.setBackground(new Color(57, 143, 193));

        btnPesquisar.setFocusPainted(false);
        btnPesquisar.setBorderPainted(false);
        btnPesquisar.setContentAreaFilled(true);
        btnPesquisar.setOpaque(true);

        btnPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisar.setPreferredSize(new Dimension(110, 35));

        // Mouse hover lindo
        btnPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnPesquisar.setBackground(new Color(45, 115, 155));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnPesquisar.setBackground(new Color(57, 143, 193));
            }
        });

        btnPesquisar.addActionListener(e -> pesquisarEstacao());

        txtPesquisa.addActionListener(e -> pesquisarEstacao());

        // Mensagem de erro
        lblMensagem = new JLabel(" ");
        lblMensagem.setForeground(Color.RED);
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMensagem.setVisible(false);

        painelPesquisa.add(txtPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(btnPesquisar, BorderLayout.EAST);
        painelPesquisa.add(lblMensagem, BorderLayout.SOUTH);

        add(painelPesquisa, BorderLayout.NORTH);
    }

    private void consultarEstacoes() {

        List<Estacao> estacoes = sistema.consultarEstacoes();

        exibirRegistrosTabela(estacoes);
    }

    private void exibirRegistrosTabela(List<Estacao> estacoes) {

        modeloTabela.setRowCount(0);

        for (Estacao estacao : estacoes) {

            modeloTabela.addRow(new Object[] {
                    estacao.consultarNome(),
                    estacao.consultarQuantidadeVeiculosEstacionados()
                            + " / "
                            + estacao.consultarCapacidadeMaxima(),
                    estacao.consultarQuantidadeVeiculosEstacionados()
                            + " veículos"
            });
        }
    }

    private void pesquisarEstacao() {

        lblMensagem.setVisible(false);

        String nome = txtPesquisa.getText().trim();

        // Se estiver vazio ou com o texto padrão, exibe todas
        if (nome.isEmpty() || nome.equals("Digite o nome da estação...")) {
            consultarEstacoes();
            return;
        }

        try {

            Estacao estacao = sistema.consultarEstacao(nome);

            // Exibe somente a estação encontrada
            exibirRegistrosTabela(java.util.Collections.singletonList(estacao));

        } catch (RegistroNaoEncontradoException e) {

            // Limpa a tabela quando não encontrar
            modeloTabela.setRowCount(0);

            // Exibe mensagem de erro
            lblMensagem.setText("Nenhuma estação encontrada.");
            lblMensagem.setVisible(true);
        } finally {
            limparMensagem();
        }
    }

    private void limparMensagem() {
        Timer timer = new Timer(3000, e -> lblMensagem.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }
}
