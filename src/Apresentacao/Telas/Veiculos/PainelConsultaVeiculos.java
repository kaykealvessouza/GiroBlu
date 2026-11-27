package Apresentacao.Telas.Veiculos;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import javax.swing.table.DefaultTableModel;

import Aplicacao.Sistema;
import Modelo.Estacao;
import Modelo.Veiculo;
import Modelo.TipoVeiculo;
import Modelo.EstadoVeiculo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class PainelConsultaVeiculos extends JPanel {

    private JTextField txtPesquisa;
    private JTable tabelaVeiculos;
    private DefaultTableModel modeloTabela;

    private JComboBox<Estacao> cmbEstacao;
    private JComboBox<EstadoVeiculo> cmbEstado;
    private JComboBox<TipoVeiculo> cmbTipo;

    private JButton btnPesquisar;

    private JLabel lblMensagem;

    private Sistema sistema;

    public PainelConsultaVeiculos(Sistema sistema) {

        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(new Color(237, 241, 244));

        // Cria o campo de pesquisa
        txtPesquisa = criarCampoPesquisa();

        // Configura cada parte da interface
        configurarPesquisa();
        configurarTabela();

        // Carrega os veículos existentes cadastradas na tabela
        consultarVeiculos();
    }

    private JTextField criarCampoPesquisa() {

        JTextField campo = new JTextField();

        String placeholder = "Digite o modelo do veículo...";

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
                new Object[] {
                        "Código",
                        "Modelo",
                        "Tipo",
                        "Estado",
                        "Estação",
                        "Bateria",
                        "Tarifa/min."
                }, 0) {

            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabelaVeiculos = new JTable(modeloTabela);

        tabelaVeiculos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaVeiculos.setRowHeight(35);

        tabelaVeiculos.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tabelaVeiculos.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollTabela = new JScrollPane(tabelaVeiculos);

        add(scrollTabela, BorderLayout.CENTER);
    }

    private void configurarPesquisa() {

        JPanel painelPesquisa = new JPanel(new BorderLayout(5, 10));

        painelPesquisa.setBackground(new Color(237, 241, 244));
        painelPesquisa.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Configura os combos
        configurarFiltros();
        configurarRenderizadores();

        // Linha de pesquisa
        JPanel painelCampoPesquisa = new JPanel(new BorderLayout(5, 0));
        painelCampoPesquisa.setBackground(new Color(237, 241, 244));

        // Painel dos filtros
        JPanel painelFiltros = new JPanel(new GridLayout(1, 3, 10, 0));
        painelFiltros.setBackground(new Color(237, 241, 244));

        // Adiciona cada combo com seu respectivo título
        painelFiltros.add(criarPainelFiltro("Estação", cmbEstacao));
        painelFiltros.add(criarPainelFiltro("Estado", cmbEstado));
        painelFiltros.add(criarPainelFiltro("Tipo", cmbTipo));

        // Continua a configuração do botão e mensagem...

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

        btnPesquisar.addActionListener(e -> pesquisarVeiculo());

        txtPesquisa.addActionListener(e -> pesquisarVeiculo());

        // Mensagem de erro
        lblMensagem = new JLabel(" ");
        lblMensagem.setForeground(Color.RED);
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        painelCampoPesquisa.add(txtPesquisa, BorderLayout.CENTER);
        painelCampoPesquisa.add(btnPesquisar, BorderLayout.EAST);

        painelPesquisa.add(painelCampoPesquisa, BorderLayout.NORTH);
        painelPesquisa.add(painelFiltros, BorderLayout.CENTER);
        painelPesquisa.add(lblMensagem, BorderLayout.SOUTH);

        add(painelPesquisa, BorderLayout.NORTH);
    }

    private JPanel criarPainelFiltro(String titulo, JComboBox<?> combo) {

        JPanel painel = new JPanel(new BorderLayout(0, 5));
        painel.setBackground(new Color(237, 241, 244));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setPreferredSize(new Dimension(150, 30));

        painel.add(lblTitulo, BorderLayout.NORTH);
        painel.add(combo, BorderLayout.CENTER);

        return painel;
    }

    private void configurarFiltros() {

        // Filtro de estação
        cmbEstacao = new JComboBox<>();
        cmbEstacao.addItem(null);

        cmbEstacao.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                atualizarEstacoes();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        // Filtro de estado
        cmbEstado = new JComboBox<>();
        cmbEstado.addItem(null);

        cmbEstado.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                atualizarEstado();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        // Filtro de tipo
        cmbTipo = new JComboBox<>();
        cmbTipo.addItem(null);

        for (TipoVeiculo tipo : TipoVeiculo.values()) {
            cmbTipo.addItem(tipo);
        }
    }

    private void configurarRenderizadores() {
        // Transforma e arruma os dados que estão no cmb
        cmbEstacao.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor == null) {
                    setText("Todas as estações");
                } else {
                    setText(((Estacao) valor).consultarNome());
                }

                return this;
            }
        });

        cmbEstado.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                setText(valor == null ? "Todos os estados" : valor.toString());

                return this;
            }
        });

        cmbTipo.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                setText(valor == null ? "Todos os tipos" : valor.toString());

                return this;
            }
        });
    }

    private void consultarVeiculos() {

        List<Veiculo> veiculos = sistema.consultarVeiculos();

        exibirRegistrosTabela(veiculos);
    }

    private void exibirRegistrosTabela(List<Veiculo> veiculos) {

        modeloTabela.setRowCount(0);

        for (Veiculo veiculo : veiculos) {

            int bateria = veiculo.consultarNivelBateria();

            String estacao = veiculo.consultarEstacaoAtual() == null
                    ? "Em utilização"
                    : veiculo.consultarEstacaoAtual().consultarNome();

            String nivelBateria = bateria == -1 // -1 = Classe veículo = Sem Bateria
                    ? "N/A"
                    : bateria + "%";

            String tarifa = String.format(
                    "R$ %.2f",
                    veiculo.consultarTarifaPorMinuto());

            modeloTabela.addRow(new Object[] {
                    veiculo.consultarCodigo(),
                    veiculo.consultarModelo(),
                    veiculo.consultarTipo(),
                    veiculo.consultarEstadoAtual(),
                    estacao,
                    nivelBateria,
                    tarifa
            });
        }
    }

    private void pesquisarVeiculo() {

        lblMensagem.setText(" ");

        String modelo = txtPesquisa.getText().trim();

        if (modelo.equals("Digite o modelo do veículo...")) {
            modelo = "";
        }

        Estacao estacao = (Estacao) cmbEstacao.getSelectedItem();

        EstadoVeiculo estado = (EstadoVeiculo) cmbEstado.getSelectedItem();

        TipoVeiculo tipo = (TipoVeiculo) cmbTipo.getSelectedItem();

        List<Veiculo> veiculos = sistema.consultarVeiculos(
                modelo,
                estacao,
                estado,
                tipo);

        exibirRegistrosTabela(veiculos);

        if (veiculos.isEmpty()) {
            lblMensagem.setText("Nenhum veículo encontrado.");
            limparMensagem();
        }
    }

    public void filtrarPorEstacao(Estacao estacao) {
        atualizarEstacoes();
        cmbEstacao.setSelectedItem(estacao);
        pesquisarVeiculo();
    }

    private void atualizarEstacoes() {
        cmbEstacao.removeAllItems();
        cmbEstacao.addItem(null);

        for (Estacao estacao : sistema.consultarEstacoes()) {
            cmbEstacao.addItem(estacao);
        }
    }

    private void atualizarEstado() {
        cmbEstado.removeAllItems();
        cmbEstado.addItem(null);

        for (EstadoVeiculo estado : EstadoVeiculo.values()) {
            cmbEstado.addItem(estado);
        }
    }

    private void limparMensagem() {
        Timer timer = new Timer(3000, e -> lblMensagem.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }
}
