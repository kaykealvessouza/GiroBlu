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
import Modelo.Estacao;
import Modelo.TipoVeiculo;
import Modelo.Veiculo;
import Modelo.BicicletaMecanica;
import Modelo.BicicletaEletrica;
import Modelo.PatineteEletrico;
import Excecoes.ValorInvalidoException;
import Excecoes.EstacaoLotadaException;

public class PainelCadastroUsuarios extends JPanel {

    private JTextField txtModelo;
    private JTextField txtNivelBateria;

    private JButton btnCadastrar;
    private JLabel lblMensagem;

    private JComboBox<Estacao> cmbEstacao;
    private JComboBox<TipoVeiculo> cmbTipoVeiculo;

    private JPanel painelBateria;

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

        // Campo do modelo
        JPanel painelModelo = criarPainelCampo("Modelo:", tamanhoLabel);

        txtModelo = new JTextField();
        txtModelo.setPreferredSize(new Dimension(300, 35));

        painelModelo.add(txtModelo, BorderLayout.CENTER);
        painelFormulario.add(painelModelo);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Combo de tipo
        JPanel painelTipo = criarPainelCampo("Tipo de veículo:", tamanhoLabel);

        cmbTipoVeiculo = new JComboBox<>(TipoVeiculo.values());
        cmbTipoVeiculo.setPreferredSize(new Dimension(300, 35));

        painelTipo.add(cmbTipoVeiculo, BorderLayout.CENTER);
        painelFormulario.add(painelTipo);

        painelFormulario.add(Box.createVerticalStrut(12));

        // Combo de estação
        JPanel painelEstacao = criarPainelCampo("Estação inicial:", tamanhoLabel);

        cmbEstacao = new JComboBox<>();

        for (Estacao estacao : sistema.consultarEstacoes()) {
            cmbEstacao.addItem(estacao);
        }

        cmbEstacao.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor instanceof Estacao) {
                    setText(((Estacao) valor).consultarNome());
                }

                return this;
            }
        });

        cmbEstacao.setPreferredSize(new Dimension(300, 35));

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

        // Campo de bateria
        painelBateria = criarPainelCampo(
                "Nível da bateria:", tamanhoLabel);

        txtNivelBateria = new JTextField();
        txtNivelBateria.setPreferredSize(new Dimension(300, 35));

        painelBateria.add(txtNivelBateria, BorderLayout.CENTER);

        painelFormulario.add(painelBateria);

        // Configura a visibilidade inicial da bateria
        cmbTipoVeiculo.addActionListener(e -> atualizarCampoBateria());
        atualizarCampoBateria();

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

        btnCadastrar.addActionListener(e -> cadastrarVeiculo());

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

    private void atualizarCampoBateria() {
        TipoVeiculo tipo = (TipoVeiculo) cmbTipoVeiculo.getSelectedItem();

        boolean eletrico = tipo == TipoVeiculo.BICICLETA_ELETRICA
                || tipo == TipoVeiculo.PATINETE_ELETRICO;

        painelBateria.setVisible(eletrico);

        if (!eletrico) {
            txtNivelBateria.setText("");
        }

        painelBateria.revalidate();
        painelBateria.repaint();
    }

    private void atualizarEstacoes() {
        cmbEstacao.removeAllItems();

        for (Estacao estacao : sistema.consultarEstacoes()) {
            cmbEstacao.addItem(estacao);
        }
    }

    private void cadastrarVeiculo() {
        try {
            String modelo = txtModelo.getText();
            Estacao estacao = (Estacao) cmbEstacao.getSelectedItem();
            TipoVeiculo tipo = (TipoVeiculo) cmbTipoVeiculo.getSelectedItem();

            Veiculo veiculo;

            switch (tipo) {

                case BICICLETA_MECANICA:
                    veiculo = new BicicletaMecanica(modelo, estacao);
                    break;

                case BICICLETA_ELETRICA:
                    int bateriaBicicleta = Integer.parseInt(
                            txtNivelBateria.getText());

                    veiculo = new BicicletaEletrica(
                            modelo, estacao, bateriaBicicleta);
                    break;

                case PATINETE_ELETRICO:
                    int bateriaPatinete = Integer.parseInt(
                            txtNivelBateria.getText());

                    veiculo = new PatineteEletrico(
                            modelo, estacao, bateriaPatinete);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Selecione um tipo de veículo válido.");
            }

            sistema.cadastrarVeiculo(veiculo);

            lblMensagem.setForeground(new Color(39, 174, 96));
            lblMensagem.setText("Veículo cadastrado com sucesso!");

            txtModelo.setText("");
            txtNivelBateria.setText("");

        } catch (NumberFormatException e) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText("A bateria deve ser um número inteiro.");

        } catch (ValorInvalidoException | EstacaoLotadaException e) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(e.getMessage());

        } catch (IllegalArgumentException e) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(e.getMessage());
        } finally{
            limparMensagem();
        }
    }

    private void limparMensagem() {
        Timer timer = new Timer(3000, e -> lblMensagem.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }
}

