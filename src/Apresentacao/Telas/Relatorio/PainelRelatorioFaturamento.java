package Apresentacao.Telas.Relatorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Aplicacao.Sistema;
import Excecoes.DataInvalidaException;
import Modelo.TipoVeiculo;
import Relatorios.RelatorioFaturamento;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PainelRelatorioFaturamento extends JPanel {

    private Sistema sistema;

    private JSpinner spnDataInicial;
    private JSpinner spnDataFinal;

    private JTable tabelaRelatorio;
    private DefaultTableModel modeloTabela;

    private JButton btnGerarRelatorio;
    private JLabel lblMensagem;

    private Timer timerMensagem;

    public PainelRelatorioFaturamento(Sistema sistema) {

        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(new Color(237, 241, 244));

        configurarFiltros();
        configurarTabela();

    }

    // ==================== CONFIGURAR FILTROS ====================

    private void configurarFiltros() {

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));

        painelFiltros.setBackground(new Color(237, 241, 244));
        painelFiltros.setBorder(
                BorderFactory.createEmptyBorder(0, 5, 0, 5));

        spnDataInicial = criarCampoData();
        spnDataFinal = criarCampoData();

        btnGerarRelatorio = new JButton("Gerar relatório");

        btnGerarRelatorio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnGerarRelatorio.setForeground(Color.WHITE);
        btnGerarRelatorio.setBackground(new Color(57, 143, 193));

        btnGerarRelatorio.setFocusPainted(false);
        btnGerarRelatorio.setBorderPainted(false);
        btnGerarRelatorio.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        btnGerarRelatorio.setPreferredSize(new Dimension(140, 35));

        btnGerarRelatorio.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnGerarRelatorio.setBackground(new Color(45, 115, 155));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnGerarRelatorio.setBackground(new Color(57, 143, 193));
            }
        });

        btnGerarRelatorio.addActionListener(e -> gerarRelatorio());

        painelFiltros.add(new JLabel("Data inicial:"));
        painelFiltros.add(spnDataInicial);

        painelFiltros.add(new JLabel("Data final:"));
        painelFiltros.add(spnDataFinal);

        painelFiltros.add(btnGerarRelatorio);

        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMensagem.setForeground(Color.RED);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(new Color(237, 241, 244));

        painelSuperior.add(painelFiltros, BorderLayout.CENTER);

        JPanel painelMensagem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelMensagem.setBackground(new Color(237, 241, 244));
        painelMensagem.add(lblMensagem);

        painelSuperior.add(painelMensagem, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.NORTH);
    }

    // ==================== CRIAR CAMPO DE DATA ====================

    private JSpinner criarCampoData() {

        JSpinner campo = new JSpinner(
                new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));

        JSpinner.DateEditor editor = new JSpinner.DateEditor(campo, "dd/MM/yyyy");

        campo.setEditor(editor);
        campo.setPreferredSize(new Dimension(130, 35));

        return campo;
    }

    // ==================== CONFIGURAR TABELA ====================

    private void configurarTabela() {

        modeloTabela = new DefaultTableModel(
                new Object[] { "Tipo de veículo", "Viagens", "Faturamento" }, 0) {

            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabelaRelatorio = new JTable(modeloTabela);

        tabelaRelatorio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaRelatorio.setRowHeight(35);

        tabelaRelatorio.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        tabelaRelatorio.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollTabela = new JScrollPane(tabelaRelatorio);

        add(scrollTabela, BorderLayout.CENTER);
    }

    // ==================== GERAR RELATÓRIO ====================

    private void gerarRelatorio() {

        lblMensagem.setText(" ");

        try {

            Date dataInicialDate = (Date) spnDataInicial.getValue();
            Date dataFinalDate = (Date) spnDataFinal.getValue();

            LocalDate dataInicial = dataInicialDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalDate dataFinal = dataFinalDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            RelatorioFaturamento relatorio =
                    sistema.consultarFaturamento(dataInicial, dataFinal);

            exibirRelatorio(relatorio);

        } catch (DataInvalidaException e) {

            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(e.getMessage());

            limparMensagem();

        } catch (IllegalArgumentException e) {

            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(e.getMessage());

            limparMensagem();
        }
    }

    // ==================== EXIBIR RELATÓRIO ====================

    private void exibirRelatorio(RelatorioFaturamento relatorio) {

        modeloTabela.setRowCount(0);

        for (TipoVeiculo tipo : TipoVeiculo.values()) {

            int quantidade = relatorio.consultarQuantidadeViagens(tipo);

            double faturamento = relatorio.consultarFaturamento(tipo);

            modeloTabela.addRow(new Object[] {
                    tipo,
                    quantidade,
                    String.format("R$ %.2f", faturamento)
            });
        }

        modeloTabela.addRow(new Object[] {
                "TOTAL",
                relatorio.consultarQuantidadeTotalViagens(),
                String.format("R$ %.2f", relatorio.consultarFaturamentoTotal())
        });

        lblMensagem.setForeground(new Color(40, 130, 70));
        lblMensagem.setText("Relatório gerado com sucesso.");

        limparMensagem();
    }

    // ==================== LIMPAR MENSAGEM ====================

    private void limparMensagem() {

        if (timerMensagem != null && timerMensagem.isRunning()) {
            timerMensagem.stop();
        }

        timerMensagem = new Timer(3000, e -> lblMensagem.setText(" "));
        timerMensagem.setRepeats(false);
        timerMensagem.start();
    }
}
