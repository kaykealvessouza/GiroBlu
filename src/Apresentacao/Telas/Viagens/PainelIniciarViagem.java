
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
import Excecoes.SaldoInsuficienteException;
import Excecoes.VeiculoIndisponivelException;
import Excecoes.VeiculoNaoEncontradoException;
import Excecoes.ViagemEmAndamentoException;
import Modelo.Estacao;
import Modelo.EstadoVeiculo;
import Modelo.Usuario;
import Modelo.Veiculo;

public class PainelIniciarViagem extends JPanel {

    private JComboBox<Usuario> cmbUsuario;
    private JComboBox<Estacao> cmbEstacao;
    private JComboBox<Veiculo> cmbVeiculo;

    private JSpinner spnDataHora;

    private JButton btnIniciar;
    private JLabel lblMensagem;

    private Sistema sistema;

    private Timer timerMensagem;

    public PainelIniciarViagem(Sistema sistema) {

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

        // Combo de usuário
        JPanel painelUsuario = criarPainelCampo(
                "Usuário:", tamanhoLabel);

        cmbUsuario = new JComboBox<>();
        cmbUsuario.setPreferredSize(new Dimension(300, 35));

        cmbUsuario.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor != null) {
                    Usuario usuario = (Usuario) valor;

                    setText(usuario.consultarNome()
                            + " - " + usuario.consultarEmail());
                }

                return this;
            }
        });

        cmbUsuario.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                atualizarUsuarios();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        painelUsuario.add(cmbUsuario, BorderLayout.CENTER);
        painelFormulario.add(painelUsuario);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Combo de estação de origem
        JPanel painelEstacao = criarPainelCampo(
                "Estação de origem:", tamanhoLabel);

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

        cmbEstacao.addActionListener(e -> atualizarVeiculos());

        painelEstacao.add(cmbEstacao, BorderLayout.CENTER);
        painelFormulario.add(painelEstacao);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Combo de veículos disponíveis
        JPanel painelVeiculo = criarPainelCampo(
                "Veículo disponível:", tamanhoLabel);

        cmbVeiculo = new JComboBox<>();
        cmbVeiculo.setPreferredSize(new Dimension(300, 35));

        cmbVeiculo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor != null) {
                    Veiculo veiculo = (Veiculo) valor;
                    setText(veiculo.consultarDescricao());
                }

                return this;
            }
        });

        painelVeiculo.add(cmbVeiculo, BorderLayout.CENTER);
        painelFormulario.add(painelVeiculo);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Data e hora de início
        JPanel painelDataHora = criarPainelCampo(
                "Data e hora:", tamanhoLabel);

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

        // Botão de iniciar viagem
        btnIniciar = criarBotao("Iniciar viagem");
        btnIniciar.addActionListener(e -> iniciarViagem());

        painelFormulario.add(btnIniciar);

        painelFormulario.add(Box.createVerticalStrut(10));

        // Mensagem
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMensagem.setPreferredSize(new Dimension(500, 25));
        lblMensagem.setMinimumSize(new Dimension(500, 25));
        lblMensagem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        painelFormulario.add(lblMensagem);

        add(painelFormulario, BorderLayout.CENTER);

        // Carrega os dados iniciais
        atualizarUsuarios();
        atualizarEstacoes();
    }

    private JPanel criarPainelCampo(String titulo, Dimension tamanhoLabel) {

        JPanel painel = new JPanel(new BorderLayout(10, 0));
        painel.setBackground(new Color(237, 241, 244));

        painel.setPreferredSize(new Dimension(500, 35));
        painel.setMinimumSize(new Dimension(500, 35));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setPreferredSize(tamanhoLabel);

        painel.add(lblTitulo, BorderLayout.WEST);

        return painel;
    }

    private JButton criarBotao(String texto) {

        JButton botao = new JButton(texto);

        botao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(57, 143, 193));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(true);
        botao.setOpaque(true);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.setPreferredSize(new Dimension(130, 35));
        botao.setMinimumSize(new Dimension(130, 35));
        botao.setMaximumSize(new Dimension(130, 35));
        botao.setAlignmentX(Component.LEFT_ALIGNMENT);

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(new Color(45, 115, 155));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(new Color(57, 143, 193));
            }
        });

        return botao;
    }

    private void atualizarUsuarios() {

        Usuario selecionado = (Usuario) cmbUsuario.getSelectedItem();

        cmbUsuario.removeAllItems();

        for (Usuario usuario : sistema.consultarUsuarios()) {
            cmbUsuario.addItem(usuario);
        }

        if (selecionado != null) {
            cmbUsuario.setSelectedItem(selecionado);
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

    private void atualizarVeiculos() {

        Estacao estacao = (Estacao) cmbEstacao.getSelectedItem();

        Veiculo selecionado = (Veiculo) cmbVeiculo.getSelectedItem();

        cmbVeiculo.removeAllItems();

        if (estacao == null) {
            return;
        }

        for (Veiculo veiculo : sistema.consultarVeiculos(
                null,
                estacao,
                EstadoVeiculo.DISPONIVEL,
                null)) {

            cmbVeiculo.addItem(veiculo);
        }

        if (selecionado != null) {
            cmbVeiculo.setSelectedItem(selecionado);
        }
    }

    private void iniciarViagem() {

        try {

            Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();
            Veiculo veiculo = (Veiculo) cmbVeiculo.getSelectedItem();

            if (usuario == null) {
                throw new IllegalArgumentException(
                        "Selecione um usuário.");
            }

            if (veiculo == null) {
                throw new IllegalArgumentException(
                        "Não há veículo disponível nesta estação.");
            }

            Date dataSelecionada = (Date) spnDataHora.getValue();

            LocalDateTime dataHoraInicio = LocalDateTime.ofInstant(
                    dataSelecionada.toInstant(),
                    ZoneId.systemDefault());

            sistema.iniciarViagem(
                    usuario,
                    veiculo,
                    dataHoraInicio);

            exibirMensagem(
                    "Viagem iniciada com sucesso!",
                    new Color(39, 174, 96));

            Persistir.salvar(sistema, lblMensagem);

            atualizarVeiculos();

            Persistir.salvar(sistema, lblMensagem); // depois do sucesso

        } catch (ViagemEmAndamentoException
                | SaldoInsuficienteException
                | VeiculoIndisponivelException
                | VeiculoNaoEncontradoException e) {

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
