
package Apresentacao.Telas.Viagens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.Timer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Aplicacao.Sistema;
import Apresentacao.Componentes.Persistir;
import Excecoes.DataInvalidaException;
import Excecoes.EstacaoLotadaException;
import Excecoes.SemViagemEmAndamentoException;
import Modelo.Estacao;
import Modelo.Usuario;
import Modelo.Veiculo;
import Modelo.Viagem;

public class PainelEncerrarViagem extends JPanel {

    private JComboBox<Viagem> cmbViagem;
    private JComboBox<Estacao> cmbEstacao;

    private JSpinner spnDataHora;

    private JButton btnEncerrar;

    private JLabel lblMensagem;
    private JLabel lblDuracao;
    private JLabel lblValor;
    private JLabel lblSaldo;
    private JLabel lblBateria;

    private JPanel painelResultado;

    private Sistema sistema;

    private Timer timerMensagem;

    private final DateTimeFormatter formatoData =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PainelEncerrarViagem(Sistema sistema) {

        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(new Color(237, 241, 244));

        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(
                new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBackground(new Color(237, 241, 244));
        painelFormulario.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30));

        Dimension tamanhoLabel = new Dimension(150, 35);

        // Combo de viagens em andamento
        JPanel painelViagem = criarPainelCampo(
                "Viagem em andamento:", tamanhoLabel);

        cmbViagem = new JComboBox<>();
        cmbViagem.setPreferredSize(new Dimension(300, 35));

        cmbViagem.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor != null) {
                    Viagem viagem = (Viagem) valor;

                    Usuario usuario = viagem.consultarUsuario();
                    Veiculo veiculo = viagem.consultarVeiculo();

                    setText(usuario.consultarNome()
                            + " | " + veiculo.consultarDescricao()
                            + " | Início: "
                            + viagem.consultarDataHoraInicio().format(formatoData));
                }

                return this;
            }
        });

        cmbViagem.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                atualizarViagens();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        painelViagem.add(cmbViagem, BorderLayout.CENTER);
        painelFormulario.add(painelViagem);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Combo de estação de destino
        JPanel painelEstacao = criarPainelCampo(
                "Estação de destino:", tamanhoLabel);

        cmbEstacao = new JComboBox<>();
        cmbEstacao.setPreferredSize(new Dimension(300, 35));

        cmbEstacao.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor != null) {
                    Estacao estacao = (Estacao) valor;
                    setText(estacao.consultarNome());
                }

                return this;
            }
        });

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

        painelEstacao.add(cmbEstacao, BorderLayout.CENTER);
        painelFormulario.add(painelEstacao);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Data e hora de encerramento
        JPanel painelDataHora = criarPainelCampo(
                "Data e hora de fim:", tamanhoLabel);

        spnDataHora = new JSpinner(
                new SpinnerDateModel(
                        new Date(), null, null, Calendar.MINUTE));

        JSpinner.DateEditor editor = new JSpinner.DateEditor(
                spnDataHora, "dd/MM/yyyy HH:mm");

        spnDataHora.setEditor(editor);
        spnDataHora.setPreferredSize(new Dimension(300, 35));

        painelDataHora.add(spnDataHora, BorderLayout.CENTER);
        painelFormulario.add(painelDataHora);

        painelFormulario.add(Box.createVerticalStrut(15));

        // Botão de encerramento
        btnEncerrar = new JButton("Encerrar viagem");
        btnEncerrar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnEncerrar.setForeground(Color.WHITE);
        btnEncerrar.setBackground(new Color(57, 143, 193));
        btnEncerrar.setFocusPainted(false);
        btnEncerrar.setBorderPainted(false);
        btnEncerrar.setContentAreaFilled(true);
        btnEncerrar.setOpaque(true);
        btnEncerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEncerrar.setPreferredSize(new Dimension(140, 35));
        btnEncerrar.setMaximumSize(new Dimension(140, 35));
        btnEncerrar.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnEncerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnEncerrar.setBackground(new Color(45, 115, 155));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnEncerrar.setBackground(new Color(57, 143, 193));
            }
        });

        btnEncerrar.addActionListener(e -> encerrarViagem());

        painelFormulario.add(btnEncerrar);

        painelFormulario.add(Box.createVerticalStrut(10));

        // Mensagem
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMensagem.setPreferredSize(new Dimension(500, 25));
        lblMensagem.setMinimumSize(new Dimension(500, 25));
        lblMensagem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        painelFormulario.add(lblMensagem);

        painelFormulario.add(Box.createVerticalStrut(15));

        // Resultado do encerramento
        painelResultado = new JPanel();
        painelResultado.setLayout(
                new BoxLayout(painelResultado, BoxLayout.Y_AXIS));
        painelResultado.setBackground(new Color(237, 241, 244));
        painelResultado.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelResultado.setVisible(false);

        JLabel lblTituloResultado = new JLabel("Resumo da viagem");
        lblTituloResultado.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTituloResultado.setForeground(new Color(52, 73, 94));

        painelResultado.add(lblTituloResultado);
        painelResultado.add(Box.createVerticalStrut(8));

        lblDuracao = criarLabelResultado();
        lblValor = criarLabelResultado();
        lblSaldo = criarLabelResultado();
        lblBateria = criarLabelResultado();

        painelResultado.add(lblDuracao);
        painelResultado.add(lblValor);
        painelResultado.add(lblSaldo);
        painelResultado.add(lblBateria);

        painelFormulario.add(painelResultado);

        add(painelFormulario, BorderLayout.CENTER);

        // Carrega os dados iniciais
        atualizarViagens();
        atualizarEstacoes();
    }

    private JPanel criarPainelCampo(
            String titulo, Dimension tamanhoLabel) {

        JPanel painel = new JPanel(new BorderLayout(10, 0));
        painel.setBackground(new Color(237, 241, 244));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setPreferredSize(tamanhoLabel);

        painel.add(lblTitulo, BorderLayout.WEST);

        return painel;
    }

    private JLabel criarLabelResultado() {

        JLabel label = new JLabel(" ");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(52, 73, 94));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    private void atualizarViagens() {

        Viagem selecionada = (Viagem) cmbViagem.getSelectedItem();

        cmbViagem.removeAllItems();

        for (Viagem viagem : sistema.consultarViagens()) {

            if (viagem.estaEmAndamento()) {
                cmbViagem.addItem(viagem);
            }
        }

        // Preserva a seleção se a viagem ainda estiver em andamento
        if (selecionada != null && selecionada.estaEmAndamento()) {
            cmbViagem.setSelectedItem(selecionada);
        }
    }

    private void atualizarEstacoes() {

        Estacao selecionada = (Estacao) cmbEstacao.getSelectedItem();

        cmbEstacao.removeAllItems();

        for (Estacao estacao : sistema.consultarEstacoes()) {
            cmbEstacao.addItem(estacao);
        }

        if (selecionada != null) {
            cmbEstacao.setSelectedItem(selecionada);
        }
    }

    private void encerrarViagem() {

        try {

            Viagem viagem = (Viagem) cmbViagem.getSelectedItem();
            Estacao estacaoDestino =
                    (Estacao) cmbEstacao.getSelectedItem();

            if (viagem == null) {
                throw new IllegalArgumentException(
                        "Selecione uma viagem em andamento.");
            }

            if (estacaoDestino == null) {
                throw new IllegalArgumentException(
                        "Selecione uma estação de destino.");
            }

            // Guarda as referências antes do encerramento
            Usuario usuario = viagem.consultarUsuario();
            Veiculo veiculo = viagem.consultarVeiculo();

            Date dataSelecionada = (Date) spnDataHora.getValue();

            LocalDateTime dataHoraFim = LocalDateTime.ofInstant(
                    dataSelecionada.toInstant(),
                    ZoneId.systemDefault());

            // Encerra a viagem no sistema
            sistema.finalizarViagem(
                    viagem,
                    estacaoDestino,
                    dataHoraFim);

            // Consulta os dados atualizados
            long duracao = viagem.calcularDuracao();
            double valor = viagem.consultarValorCobrado();
            double saldo = usuario.consultarSaldo();
            int bateria = veiculo.consultarNivelBateria();

            // Exibe o resultado
            lblDuracao.setText("Duração: " + duracao + " minuto(s)");

            lblValor.setText("Valor cobrado: R$ "
                    + String.format("%.2f", valor).replace(".", ","));

            lblSaldo.setText("Novo saldo: R$ "
                    + String.format("%.2f", saldo).replace(".", ","));

            if (bateria >= 0) {
                lblBateria.setText("Bateria final: " + bateria + "%");
            } else {
                lblBateria.setText("Bateria final: Não se aplica");
            }

            painelResultado.setVisible(true);

            exibirMensagem(
                    "Viagem encerrada com sucesso!",
                    new Color(39, 174, 96));

            Persistir.salvar(sistema, lblMensagem);

            // Remove a viagem encerrada da lista
            atualizarViagens();

        } catch (SemViagemEmAndamentoException
                | DataInvalidaException
                | EstacaoLotadaException e) {

            exibirMensagem(e.getMessage(), Color.RED);

        } catch (IllegalArgumentException e) {

            exibirMensagem(e.getMessage(), Color.RED);
        }
    }

    private void exibirMensagem(String mensagem, Color cor) {

        if (timerMensagem != null && timerMensagem.isRunning()) {
            timerMensagem.stop();
        }

        lblMensagem.setForeground(cor);
        lblMensagem.setText(mensagem);

        timerMensagem = new Timer(3000, e -> lblMensagem.setText(" "));
        timerMensagem.setRepeats(false);
        timerMensagem.start();
    }
}
