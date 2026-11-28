
package Apresentacao.Telas.Usuarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Aplicacao.Sistema;
import Excecoes.OperacaoInvalidaException;
import Interfaces.Plano;
import Modelo.Usuario;

public class PainelGerenciarUsuarios extends JPanel {

    private JComboBox<Usuario> cmbUsuario;
    private JComboBox<Plano> cmbPlano;

    private JButton btnTrocarPlano;

    private JLabel lblMensagem;

    private Sistema sistema;

    private Timer timerMensagem;

    public PainelGerenciarUsuarios(Sistema sistema) {

        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(new Color(237, 241, 244));

        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(
                new javax.swing.BoxLayout(
                        painelFormulario,
                        javax.swing.BoxLayout.Y_AXIS));

        painelFormulario.setBackground(new Color(237, 241, 244));
        painelFormulario.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Campo de seleção do usuário
        JPanel painelUsuario = new JPanel(new BorderLayout(10, 0));
        painelUsuario.setBackground(new Color(237, 241, 244));
        painelUsuario.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35));

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setPreferredSize(new Dimension(150, 35));

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

        cmbUsuario.addActionListener(e -> atualizarPlanoSelecionado());

        painelUsuario.add(lblUsuario, BorderLayout.WEST);
        painelUsuario.add(cmbUsuario, BorderLayout.CENTER);

        painelFormulario.add(painelUsuario);

        painelFormulario.add(
                javax.swing.Box.createVerticalStrut(15));

        // Campo de seleção do plano
        JPanel painelPlano = new JPanel(new BorderLayout(10, 0));
        painelPlano.setBackground(new Color(237, 241, 244));
        painelPlano.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35));

        JLabel lblPlano = new JLabel("Novo plano:");
        lblPlano.setPreferredSize(new Dimension(150, 35));

        cmbPlano = new JComboBox<>();
        cmbPlano.setPreferredSize(new Dimension(300, 35));

        cmbPlano.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor == null) {
                    setText("Selecione um plano...");
                } else {
                    Plano plano = (Plano) valor;
                    setText(plano.consultarNome());
                }

                return this;
            }
        });

        for (Plano plano : sistema.consultarPlanos()) {
            cmbPlano.addItem(plano);
        }

        painelPlano.add(lblPlano, BorderLayout.WEST);
        painelPlano.add(cmbPlano, BorderLayout.CENTER);

        painelFormulario.add(painelPlano);

        painelFormulario.add(
                javax.swing.Box.createVerticalStrut(15));

        // Botão de gerenciamento
        btnTrocarPlano = criarBotao("Trocar plano");
        btnTrocarPlano.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnTrocarPlano.setPreferredSize(new Dimension(120, 35));
        btnTrocarPlano.setMinimumSize(new Dimension(120, 35));
        btnTrocarPlano.setMaximumSize(new Dimension(120, 35));

        btnTrocarPlano.addActionListener(e -> trocarPlano());

        painelFormulario.add(btnTrocarPlano);

        painelFormulario.add(
                javax.swing.Box.createVerticalStrut(10));

        // Mensagem de sucesso ou erro
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMensagem.setPreferredSize(new Dimension(500, 25));
        lblMensagem.setMinimumSize(new Dimension(500, 25));
        lblMensagem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        painelFormulario.add(lblMensagem);

        add(painelFormulario, BorderLayout.CENTER);

        atualizarUsuarios();
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);

        botao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(57, 143, 193));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(true);
        botao.setOpaque(true);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(120, 35));
        botao.setMaximumSize(new Dimension(120, 35));

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

        atualizarPlanoSelecionado();
    }

    private void atualizarPlanoSelecionado() {
        Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();

        if (usuario != null) {
            cmbPlano.setSelectedItem(
                    usuario.consultarPlanoContratado());
        }
    }

    private void trocarPlano() {
        Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();
        Plano plano = (Plano) cmbPlano.getSelectedItem();

        if (usuario == null) {
            exibirMensagem("Selecione um usuário!", false);
            return;
        }

        if (plano == null) {
            exibirMensagem("Selecione um plano!", false);
            return;
        }

        try {
            sistema.alterarPlanoUsuario(usuario, plano);

            exibirMensagem(
                    "Plano do usuário alterado com sucesso!", true);

        } catch (OperacaoInvalidaException | IllegalArgumentException e) {

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

        timerMensagem = new Timer(3000, e -> lblMensagem.setText(" "));
        timerMensagem.setRepeats(false);
        timerMensagem.start();
    }
}
