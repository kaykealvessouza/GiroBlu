
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import Aplicacao.Sistema;
import Modelo.Usuario;
import Apresentacao.Componentes.Persistir;
import Interfaces.Plano;
import Excecoes.RegistroJaExistenteException;

public class PainelCadastroUsuarios extends JPanel {

    private JTextField txtNome;
    private JTextField txtEmail;

    private JButton btnCadastrar;
    private JLabel lblMensagem;

    private JComboBox<Plano> cmbPlano;

    private Sistema sistema;

    public PainelCadastroUsuarios(Sistema sistema) {
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

        // Campo do nome
        JPanel painelNome = criarPainelCampo("Nome:", tamanhoLabel);

        txtNome = new JTextField();
        txtNome.setPreferredSize(new Dimension(300, 35));

        painelNome.add(txtNome, BorderLayout.CENTER);
        painelFormulario.add(painelNome);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Campo do e-mail
        JPanel painelEmail = criarPainelCampo("E-mail:", tamanhoLabel);

        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(300, 35));

        painelEmail.add(txtEmail, BorderLayout.CENTER);
        painelFormulario.add(painelEmail);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Combo de plano
        JPanel painelPlano = criarPainelCampo("Plano:", tamanhoLabel);

        cmbPlano = new JComboBox<>();

        configurarRenderizadorPlano();
        atualizarPlanos();

        cmbPlano.setPreferredSize(new Dimension(300, 35));

        cmbPlano.addPopupMenuListener(new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                atualizarPlanos();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        painelPlano.add(cmbPlano, BorderLayout.CENTER);
        painelFormulario.add(painelPlano);

        painelFormulario.add(Box.createVerticalStrut(15));

        // Botão de cadastro
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setBackground(new Color(57, 143, 193));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorderPainted(false);
        btnCadastrar.setContentAreaFilled(true);
        btnCadastrar.setOpaque(true);
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.setPreferredSize(new Dimension(110, 35));
        btnCadastrar.setMaximumSize(new Dimension(110, 35));
        btnCadastrar.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnCadastrar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btnCadastrar.setBackground(new Color(45, 115, 155));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCadastrar.setBackground(new Color(57, 143, 193));
            }
        });

        btnCadastrar.addActionListener(e -> cadastrarUsuario());

        painelFormulario.add(btnCadastrar);

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
    }

    private JPanel criarPainelCampo(String titulo, Dimension tamanhoLabel) {

        JPanel painel = new JPanel(new BorderLayout(10, 0));
        painel.setBackground(new Color(237, 241, 244));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setPreferredSize(tamanhoLabel);

        painel.add(lblTitulo, BorderLayout.WEST);

        return painel;
    }

    private void configurarRenderizadorPlano() {

        cmbPlano.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista,
                    Object valor,
                    int indice,
                    boolean selecionado,
                    boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor != null) {
                    setText(((Plano) valor).consultarNome());
                }

                return this;
            }
        });
    }

    private void atualizarPlanos() {

        Plano planoSelecionado = (Plano) cmbPlano.getSelectedItem();

        cmbPlano.removeAllItems();

        for (Plano plano : sistema.consultarPlanos()) {
            cmbPlano.addItem(plano);
        }

        if (planoSelecionado != null) {
            for (int i = 0; i < cmbPlano.getItemCount(); i++) {

                if (cmbPlano.getItemAt(i) == planoSelecionado) {
                    cmbPlano.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void cadastrarUsuario() {

        try {

            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();

            Plano plano = (Plano) cmbPlano.getSelectedItem();

            if (nome.isEmpty() || email.isEmpty()) {
                throw new IllegalArgumentException(
                        "Preencha todos os campos.");
            }

            if (plano == null) {
                throw new IllegalArgumentException(
                        "Selecione um plano.");
            }

            Usuario usuario = new Usuario(nome, email, plano);

            sistema.cadastrarUsuario(usuario);

            lblMensagem.setForeground(new Color(39, 174, 96));
            lblMensagem.setText("Usuário cadastrado com sucesso!");

            txtNome.setText("");
            txtEmail.setText("");

            Persistir.salvar(sistema, lblMensagem); // depois do sucesso

        } catch (RegistroJaExistenteException | IllegalArgumentException e) {

            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(e.getMessage());

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
