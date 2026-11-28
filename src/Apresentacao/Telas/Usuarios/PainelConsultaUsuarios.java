
package Apresentacao.Telas.Usuarios;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import javax.swing.table.DefaultTableModel;

import Aplicacao.Sistema;
import Modelo.Usuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class PainelConsultaUsuarios extends JPanel {

    private JTextField txtPesquisa;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;

    private JButton btnPesquisar;

    private JLabel lblMensagem;

    private Sistema sistema;
    private PainelUsuarios painelUsuarios;

    private List<Usuario> usuariosExibidos;

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

        // Carrega os usuários cadastrados
        consultarUsuarios();
    }

    private JTextField criarCampoPesquisa() {

        JTextField campo = new JTextField();

        String placeholder = "Digite o nome ou e-mail do usuário...";

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
                new Object[] { "Nome", "E-mail", "Plano", "Saldo", "Viagem ativa" }, 0) {

            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabelaUsuarios = new JTable(modeloTabela);

        tabelaUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaUsuarios.setRowHeight(35);

        // Só permite selecionar um registro
        tabelaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

                        Usuario usuario = usuariosExibidos.get(linhaModelo);

                        painelUsuarios.abrirExtratoCarteira(usuario);
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

        btnPesquisar.setCursor(
                new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnPesquisar.setPreferredSize(new Dimension(110, 35));

        // Mouse hover
        btnPesquisar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnPesquisar.setBackground(new Color(45, 115, 155));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnPesquisar.setBackground(new Color(57, 143, 193));
            }
        });

        btnPesquisar.addActionListener(e -> pesquisarUsuario());

        txtPesquisa.addActionListener(e -> pesquisarUsuario());

        // Mensagem de erro
        lblMensagem = new JLabel(" ");
        lblMensagem.setForeground(Color.RED);
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        painelPesquisa.add(txtPesquisa, BorderLayout.CENTER);
        painelPesquisa.add(btnPesquisar, BorderLayout.EAST);
        painelPesquisa.add(lblMensagem, BorderLayout.SOUTH);

        add(painelPesquisa, BorderLayout.NORTH);
    }

    // ==================== CONSULTAR USUÁRIOS ====================

    private void consultarUsuarios() {

        usuariosExibidos = sistema.consultarUsuarios();

        exibirRegistrosTabela(usuariosExibidos);
    }

    // ==================== EXIBIR REGISTROS NA TABELA ====================

    private void exibirRegistrosTabela(List<Usuario> usuarios) {

        modeloTabela.setRowCount(0);

        usuariosExibidos = new ArrayList<>(usuarios);

        for (Usuario usuario : usuariosExibidos) {

            String plano = usuario.consultarPlanoContratado().consultarNome();

            String saldo = String.format("R$ %.2f", usuario.consultarSaldo());

            String viagemAtiva = usuario.possuiViagemEmAndamento()
                    ? "Sim"
                    : "Não";

            modeloTabela.addRow(new Object[] {
                    usuario.consultarNome(),
                    usuario.consultarEmail(),
                    plano,
                    saldo,
                    viagemAtiva
            });
        }
    }

    // ==================== PESQUISAR USUÁRIO ====================

    private void pesquisarUsuario() {

        lblMensagem.setText(" ");

        String pesquisa = txtPesquisa.getText().trim();

        // Se estiver vazio ou com o texto padrão, exibe todos
        if (pesquisa.isEmpty()
                || pesquisa.equals("Digite o nome ou e-mail do usuário...")) {

            consultarUsuarios();
            return;
        }

        List<Usuario> usuarios = sistema.consultarUsuarios(pesquisa);

        exibirRegistrosTabela(usuarios);

        if (usuarios.isEmpty()) {
            lblMensagem.setText("Nenhum usuário encontrado.");

            limparMensagem();
        }
    }

    // ==================== LIMPAR MENSAGEM ====================

    private void limparMensagem() {

        Timer timer = new Timer(3000, e -> lblMensagem.setText(" "));

        timer.setRepeats(false);
        timer.start();
    }
}
