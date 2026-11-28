
package Apresentacao.Telas.Carteiras;

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
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.Timer;
import javax.swing.DefaultListCellRenderer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Aplicacao.Sistema;
import Excecoes.ValorInvalidoException;
import Modelo.Usuario;
import Apresentacao.Componentes.Persistir;

public class PainelRecarregarCarteira extends JPanel {

    private JComboBox<Usuario> cmbUsuario;

    private JTextField txtValor;
    private JSpinner spnDataHora;

    private JButton btnRecarregar;

    private JLabel lblMensagem;

    private Sistema sistema;

    private Timer timerMensagem;

    public PainelRecarregarCarteira(Sistema sistema) {

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

        // Seleção do usuário
        JPanel painelUsuario = criarPainelCampo("Usuário:", tamanhoLabel);

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

        // Valor da recarga
        JPanel painelValor = criarPainelCampo("Valor (R$):", tamanhoLabel);

        txtValor = new JTextField();
        txtValor.setPreferredSize(new Dimension(300, 35));

        painelValor.add(txtValor, BorderLayout.CENTER);
        painelFormulario.add(painelValor);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Data e hora da recarga
        JPanel painelDataHora = criarPainelCampo("Data e hora:", tamanhoLabel);

        spnDataHora = new JSpinner(
                new SpinnerDateModel(new Date(), null, null,
                        java.util.Calendar.MINUTE));

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spnDataHora, "dd/MM/yyyy HH:mm");

        spnDataHora.setEditor(editor);
        spnDataHora.setPreferredSize(new Dimension(300, 35));

        painelDataHora.add(spnDataHora, BorderLayout.CENTER);
        painelFormulario.add(painelDataHora);

        painelFormulario.add(Box.createVerticalStrut(15));

        // Botão de recarga
        btnRecarregar = criarBotao("Recarregar");
        btnRecarregar.addActionListener(e -> recarregarCarteira());

        painelFormulario.add(btnRecarregar);

        painelFormulario.add(Box.createVerticalStrut(10));

        // Mensagem
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMensagem.setPreferredSize(new Dimension(650, 25));
        lblMensagem.setMinimumSize(new Dimension(650, 25));
        lblMensagem.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 25));

        painelFormulario.add(lblMensagem);

        add(painelFormulario, BorderLayout.CENTER);

        atualizarUsuarios();
    }

    private JPanel criarPainelCampo(
            String titulo, Dimension tamanhoLabel) {

        JPanel painel = new JPanel(new BorderLayout(10, 0));
        painel.setBackground(new Color(237, 241, 244));
        painel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35));

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

        botao.setPreferredSize(new Dimension(120, 35));
        botao.setMinimumSize(new Dimension(120, 35));
        botao.setMaximumSize(new Dimension(120, 35));
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

        Usuario usuarioSelecionado = (Usuario) cmbUsuario.getSelectedItem();

        cmbUsuario.removeAllItems();

        for (Usuario usuario : sistema.consultarUsuarios()) {
            cmbUsuario.addItem(usuario);
        }

        if (usuarioSelecionado != null) {
            cmbUsuario.setSelectedItem(usuarioSelecionado);
        }
    }

    private void recarregarCarteira() {

        Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();

        if (usuario == null) {
            exibirMensagem("Selecione um usuário!", false);
            return;
        }

        try {
            String valorTexto = txtValor.getText().trim()
                    .replace(",", ".");

            if (valorTexto.isEmpty()) {
                throw new IllegalArgumentException(
                        "Informe o valor da recarga.");
            }

            double valor = Double.parseDouble(valorTexto);

            Date dataSelecionada = (Date) spnDataHora.getValue();

            LocalDateTime dataHora = LocalDateTime.ofInstant(
                    dataSelecionada.toInstant(),
                    ZoneId.systemDefault());

            usuario.recarregarCarteira(valor, dataHora);

            double saldo = usuario.consultarSaldo();

            String situacao = usuario.temSaldoMinimo()
                    ? "Saldo mínimo atingido."
                    : "Saldo mínimo não atingido.";

            exibirMensagem(
                    "Recarga realizada! Saldo: R$ "
                            + String.format("%.2f", saldo).replace(".", ",")
                            + " - " + situacao,
                    true);

            txtValor.setText("");

            Persistir.salvar(sistema, lblMensagem); // depois do sucesso

        } catch (ValorInvalidoException | IllegalArgumentException e) {

            exibirMensagem(e.getMessage(), false);
        }
    }

    private void exibirMensagem(String mensagem, boolean sucesso) {

        if (timerMensagem != null && timerMensagem.isRunning()) {
            timerMensagem.stop();
        }

        lblMensagem.setForeground(
                sucesso ? new Color(0, 128, 0) : Color.RED);

        lblMensagem.setText(mensagem);

        timerMensagem = new Timer(4000, e -> lblMensagem.setText(" "));
        timerMensagem.setRepeats(false);
        timerMensagem.start();
    }
}
