
package Apresentacao.Telas.Carteiras;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import Aplicacao.Sistema;
import Financeiro.LinhaExtrato;
import Financeiro.TipoMovimentacao;
import Modelo.Usuario;

public class PainelExtratoCarteira extends JPanel {

    private JComboBox<Usuario> cmbUsuario;
    private JComboBox<String> cmbFiltro;

    private JTable tabelaExtrato;
    private DefaultTableModel modeloTabela;

    private JLabel lblSaldo;
    private JLabel lblMensagem;

    private Sistema sistema;
    private Usuario usuario;

    private List<LinhaExtrato> movimentacoesExibidas;

    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PainelExtratoCarteira(Sistema sistema) {

        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(new Color(237, 241, 244));

        configurarCabecalho();
        configurarTabela();

        carregarUsuarios();
    }

    public PainelExtratoCarteira(Sistema sistema, Usuario usuario) {
        this.sistema = sistema;
        this.usuario = usuario;

        configurarCabecalho();
        configurarTabela();

        carregarUsuarios();
        cmbUsuario.setSelectedItem(usuario);

        carregarExtrato();
    }

    private void configurarCabecalho() {

        JPanel painelCabecalho = new JPanel(
                new BorderLayout(10, 10));

        painelCabecalho.setBackground(new Color(237, 241, 244));
        painelCabecalho.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 10, 25));

        JPanel painelSuperior = new JPanel(
                new BorderLayout(10, 0));

        painelSuperior.setBackground(new Color(237, 241, 244));

        cmbUsuario = new JComboBox<>();
        cmbUsuario.setPreferredSize(new Dimension(300, 35));

        cmbUsuario.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor == null) {
                    setText("Selecione um usuário...");
                } else {
                    Usuario usuario = (Usuario) valor;

                    setText(usuario.consultarNome()
                            + " - " + usuario.consultarEmail());
                }

                cmbUsuario.addPopupMenuListener(new PopupMenuListener() {
                    @Override
                    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                        carregarUsuarios();
                    }

                    @Override
                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    }

                    @Override
                    public void popupMenuCanceled(PopupMenuEvent e) {
                    }
                });

                return this;
            }
        });

        cmbUsuario.addActionListener(e -> {
            usuario = (Usuario) cmbUsuario.getSelectedItem();
            carregarExtrato();
        });

        painelSuperior.add(cmbUsuario, BorderLayout.WEST);

        lblSaldo = new JLabel("Saldo: R$ 0,00");
        lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSaldo.setForeground(new Color(52, 73, 94));

        painelSuperior.add(lblSaldo, BorderLayout.EAST);

        JPanel painelFiltro = new JPanel(
                new BorderLayout(10, 0));

        painelFiltro.setBackground(new Color(237, 241, 244));

        JLabel lblFiltro = new JLabel("Tipo de movimentação:");

        cmbFiltro = new JComboBox<>(
                new String[] { "Todas", "Crédito", "Débito" });

        cmbFiltro.setPreferredSize(new Dimension(180, 30));

        cmbFiltro.addActionListener(e -> exibirMovimentacoes());

        painelFiltro.add(lblFiltro, BorderLayout.WEST);
        painelFiltro.add(cmbFiltro, BorderLayout.CENTER);

        painelCabecalho.add(painelSuperior, BorderLayout.NORTH);
        painelCabecalho.add(painelFiltro, BorderLayout.SOUTH);

        add(painelCabecalho, BorderLayout.NORTH);
    }

    private void configurarTabela() {

        modeloTabela = new DefaultTableModel(
                new Object[] {
                        "Data/Hora",
                        "Tipo",
                        "Valor",
                        "Saldo após"
                }, 0) {

            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabelaExtrato = new JTable(modeloTabela);

        tabelaExtrato.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaExtrato.setRowHeight(35);
        tabelaExtrato.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tabelaExtrato.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(tabelaExtrato);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 25, 15, 25));

        add(scroll, BorderLayout.CENTER);

        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensagem.setForeground(Color.RED);
        lblMensagem.setBorder(
                BorderFactory.createEmptyBorder(0, 25, 10, 25));
        lblMensagem.setPreferredSize(new Dimension(500, 30));

        add(lblMensagem, BorderLayout.SOUTH);
    }

    private void carregarUsuarios() {
        Usuario usuarioSelecionado = usuario;

        cmbUsuario.removeAllItems();

        for (Usuario u : sistema.consultarUsuarios()) {
            cmbUsuario.addItem(u);
        }

        if (usuarioSelecionado != null) {
            cmbUsuario.setSelectedItem(usuarioSelecionado);
        }
    }

    private void carregarExtrato() {

        modeloTabela.setRowCount(0);
        movimentacoesExibidas = new ArrayList<>();

        if (usuario == null) {
            lblSaldo.setText("Saldo: R$ 0,00");
            lblMensagem.setText("Selecione um usuário.");
            return;
        }

        lblSaldo.setText(
                "Saldo: R$ "
                        + String.format("%.2f", usuario.consultarSaldo())
                                .replace(".", ","));

        movimentacoesExibidas = usuario.consultarExtrato();

        exibirMovimentacoes();
    }

    private void exibirMovimentacoes() {

        modeloTabela.setRowCount(0);

        if (usuario == null) {
            return;
        }

        String filtro = (String) cmbFiltro.getSelectedItem();

        for (LinhaExtrato linha : movimentacoesExibidas) {

            TipoMovimentacao tipo = linha.consultarMovimentacao()
                    .consultarTipoMovimentacao();

            if ("Crédito".equals(filtro)
                    && tipo != TipoMovimentacao.CREDITO) {
                continue;
            }

            if ("Débito".equals(filtro)
                    && tipo != TipoMovimentacao.DEBITO) {
                continue;
            }

            modeloTabela.addRow(new Object[] {
                    linha.consultarMovimentacao()
                            .consultarDataHoraMovimentacao()
                            .format(formatoData),

                    tipo,

                    "R$ " + String.format("%.2f",
                            linha.consultarMovimentacao()
                                    .consultarValorMovimentacao())
                            .replace(".", ","),

                    "R$ " + String.format("%.2f",
                            linha.consultarSaldoApos())
                            .replace(".", ",")
            });
        }

        if (modeloTabela.getRowCount() == 0) {
            lblMensagem.setText("Nenhuma movimentação encontrada.");
        } else {
            lblMensagem.setText(" ");
        }
    }
}
