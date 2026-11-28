
package Apresentacao.Telas.Viagens;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.table.DefaultTableModel;

import Aplicacao.Sistema;
import Modelo.Estacao;
import Modelo.Usuario;
import Modelo.Veiculo;
import Modelo.Viagem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class PainelConsultarViagem extends JPanel {

    private JTextField txtPesquisa;
    private JTable tabelaViagens;
    private DefaultTableModel modeloTabela;

    private JComboBox<String> cmbSituacao;

    private JButton btnPesquisar;

    private JLabel lblMensagem;

    private Sistema sistema;

    private Timer timerMensagem;

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PainelConsultarViagem(Sistema sistema) {

        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(new Color(237, 241, 244));

        txtPesquisa = criarCampoPesquisa();

        configurarPesquisa();
        configurarTabela();

        consultarViagens();
    }

    private JTextField criarCampoPesquisa() {

        JTextField campo = new JTextField();

        String placeholder = "Digite usuário, veículo ou estação...";

        campo.setText(placeholder);
        campo.setForeground(Color.GRAY);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        campo.setPreferredSize(new Dimension(300, 35));

        campo.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

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
                        "Usuário",
                        "Veículo",
                        "Origem",
                        "Destino",
                        "Início",
                        "Término",
                        "Duração",
                        "Valor",
                        "Situação"
                }, 0) {

            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabelaViagens = new JTable(modeloTabela);

        tabelaViagens.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaViagens.setRowHeight(35);

        tabelaViagens.setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tabelaViagens.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollTabela = new JScrollPane(tabelaViagens);

        add(scrollTabela, BorderLayout.CENTER);
    }

    private void configurarPesquisa() {

        JPanel painelPesquisa = new JPanel(new BorderLayout(5, 10));

        painelPesquisa.setBackground(new Color(237, 241, 244));
        painelPesquisa.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Campo de pesquisa
        JPanel painelCampoPesquisa = new JPanel(new BorderLayout(5, 0));
        painelCampoPesquisa.setBackground(new Color(237, 241, 244));

        // Filtro de situação
        JPanel painelFiltros = new JPanel(new GridLayout(1, 1, 10, 0));
        painelFiltros.setBackground(new Color(237, 241, 244));

        cmbSituacao = new JComboBox<>(new String[] {
                "Todas as situações",
                "Em andamento",
                "Encerradas"
        });

        cmbSituacao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbSituacao.setPreferredSize(new Dimension(200, 30));

        painelFiltros.add(criarPainelFiltro("Situação", cmbSituacao));

        // Botão de pesquisa
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

        btnPesquisar.addActionListener(e -> pesquisarViagem());

        txtPesquisa.addActionListener(e -> pesquisarViagem());

        // Mensagem
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

        painel.add(lblTitulo, BorderLayout.NORTH);
        painel.add(combo, BorderLayout.CENTER);

        return painel;
    }

    private void consultarViagens() {

        List<Viagem> viagens = sistema.consultarViagens();

        exibirRegistrosTabela(viagens);
    }

    private void exibirRegistrosTabela(List<Viagem> viagens) {

        modeloTabela.setRowCount(0);

        for (Viagem viagem : viagens) {

            Usuario usuario = viagem.consultarUsuario();
            Veiculo veiculo = viagem.consultarVeiculo();
            Estacao origem = viagem.consultarEstacaoOrigem();
            Estacao destino = viagem.consultarEstacaoDestino();

            String nomeUsuario = usuario.consultarNome();

            String descricaoVeiculo = veiculo.consultarCodigo()
                    + " - " + veiculo.consultarModelo();

            String nomeOrigem = origem.consultarNome();

            String nomeDestino = destino == null
                    ? "—"
                    : destino.consultarNome();

            String dataInicio = formatarData(
                    viagem.consultarDataHoraInicio());

            String dataFim = viagem.estaEmAndamento()
                    ? "—"
                    : formatarData(viagem.consultarDataHoraFim());

            String duracao = viagem.estaEmAndamento()
                    ? "—"
                    : viagem.calcularDuracao() + " min";

            String valor = viagem.estaEmAndamento()
                    ? "—"
                    : String.format(
                            Locale.forLanguageTag("pt-BR"),
                            "R$ %.2f",
                            viagem.consultarValorCobrado());

            String situacao = viagem.estaEmAndamento()
                    ? "Em andamento"
                    : "Encerrada";

            modeloTabela.addRow(new Object[] {
                    nomeUsuario,
                    descricaoVeiculo,
                    nomeOrigem,
                    nomeDestino,
                    dataInicio,
                    dataFim,
                    duracao,
                    valor,
                    situacao
            });
        }
    }

    private String formatarData(LocalDateTime dataHora) {

        if (dataHora == null) {
            return "—";
        }

        return dataHora.format(FORMATO_DATA);
    }

    private void pesquisarViagem() {

        lblMensagem.setText(" ");

        String pesquisa = txtPesquisa.getText().trim();

        if (pesquisa.equals("Digite usuário, veículo ou estação...")) {
            pesquisa = "";
        }

        pesquisa = pesquisa.toLowerCase(Locale.ROOT);

        String situacaoSelecionada = (String) cmbSituacao.getSelectedItem();

        List<Viagem> viagens = sistema.consultarViagens();

        List<Viagem> resultado = new java.util.ArrayList<>();

        for (Viagem viagem : viagens) {

            Usuario usuario = viagem.consultarUsuario();
            Veiculo veiculo = viagem.consultarVeiculo();
            Estacao origem = viagem.consultarEstacaoOrigem();
            Estacao destino = viagem.consultarEstacaoDestino();

            boolean correspondePesquisa = usuario.consultarNome().toLowerCase(Locale.ROOT).contains(pesquisa)
                    || usuario.consultarEmail().toLowerCase(Locale.ROOT).contains(pesquisa)
                    || String.valueOf(veiculo.consultarCodigo()).contains(pesquisa)
                    || veiculo.consultarModelo().toLowerCase(Locale.ROOT).contains(pesquisa)
                    || origem.consultarNome().toLowerCase(Locale.ROOT).contains(pesquisa)
                    || (destino != null
                            && destino.consultarNome().toLowerCase(Locale.ROOT).contains(pesquisa));

            boolean correspondeSituacao = situacaoSelecionada.equals("Todas as situações")
                    || (situacaoSelecionada.equals("Em andamento")
                            && viagem.estaEmAndamento())
                    || (situacaoSelecionada.equals("Encerradas")
                            && !viagem.estaEmAndamento());

            if (correspondePesquisa && correspondeSituacao) {
                resultado.add(viagem);
            }
        }

        exibirRegistrosTabela(resultado);

        if (resultado.isEmpty()) {
            exibirMensagem("Nenhuma viagem encontrada.");
        }
    }

    private void exibirMensagem(String mensagem) {

        if (timerMensagem != null && timerMensagem.isRunning()) {
            timerMensagem.stop();
        }

        lblMensagem.setText(mensagem);

        timerMensagem = new Timer(
                3000,
                e -> lblMensagem.setText(" "));

        timerMensagem.setRepeats(false);
        timerMensagem.start();
    }
}
