
package Apresentacao.Telas.Veiculos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import Modelo.Veiculo;
import Excecoes.OperacaoInvalidaException;

public class PainelGerenciarVeiculos extends JPanel {

    private JComboBox<Veiculo> cmbVeiculo;

    private JButton btnManutencao;
    private JButton btnRetirarManutencao;
    private JButton btnCarregar;

    private JLabel lblMensagem;

    private Sistema sistema;

    public PainelGerenciarVeiculos(Sistema sistema) {

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

        // Campo de seleção do veículo
        JPanel painelVeiculo = new JPanel(new BorderLayout(10, 0));
        painelVeiculo.setBackground(new Color(237, 241, 244));
        painelVeiculo.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35));

        JLabel lblVeiculo = new JLabel("Veículo:");
        lblVeiculo.setPreferredSize(new Dimension(150, 35));

        cmbVeiculo = new JComboBox<>();
        cmbVeiculo.setPreferredSize(new Dimension(300, 35));

        cmbVeiculo.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                atualizarVeiculos();
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        cmbVeiculo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> lista, Object valor, int indice,
                    boolean selecionado, boolean foco) {

                super.getListCellRendererComponent(
                        lista, valor, indice, selecionado, foco);

                if (valor == null) {
                    setText("Selecione um veículo...");
                } else {
                    Veiculo veiculo = (Veiculo) valor;

                    setText("#" + veiculo.consultarCodigo()
                            + " - " + veiculo.consultarModelo());
                }

                return this;
            }
        });

        painelVeiculo.add(lblVeiculo, BorderLayout.WEST);
        painelVeiculo.add(cmbVeiculo, BorderLayout.CENTER);

        painelFormulario.add(painelVeiculo);

        painelFormulario.add(
                javax.swing.Box.createVerticalStrut(15));

        // Botões de gerenciamento
        JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 10, 0));
        painelBotoes.setBackground(new Color(237, 241, 244));
        painelBotoes.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 35));

        btnManutencao = criarBotao("Enviar para manutenção");
        btnRetirarManutencao = criarBotao("Retirar da manutenção");
        btnCarregar = criarBotao("Carregar bateria");

        painelBotoes.add(btnManutencao);
        painelBotoes.add(btnRetirarManutencao);
        painelBotoes.add(btnCarregar);

        btnManutencao.addActionListener(e -> enviarParaManutencao());
        btnRetirarManutencao.addActionListener(e -> retornarDaManutencao());
        btnCarregar.addActionListener(e -> recarregarBateria());

        painelFormulario.add(painelBotoes);

        painelFormulario.add(
                javax.swing.Box.createVerticalStrut(10));

        // Mensagem de sucesso ou erro
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMensagem.setPreferredSize(new Dimension(500, 25));
        lblMensagem.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 25));

        painelFormulario.add(lblMensagem);

        add(painelFormulario, BorderLayout.CENTER);
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

    private void atualizarVeiculos() {
        cmbVeiculo.removeAllItems();

        for (Veiculo veiculo : sistema.consultarVeiculos()) {
            cmbVeiculo.addItem(veiculo);
        }
    }

    private void enviarParaManutencao() {
        Veiculo veiculo = (Veiculo) cmbVeiculo.getSelectedItem();

        if (veiculo == null) {
            lblMensagem.setText("Selecione um veículo!");
            return;
        }

        try {
            veiculo.enviarParaManutencao();
            lblMensagem.setForeground(new Color(0, 128, 0));
            lblMensagem.setText("Veículo enviado para manutenção!");
        } catch (OperacaoInvalidaException e) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(e.getMessage());
        } finally{
            limparMensagem();
        }
    }

    private void retornarDaManutencao() {
        Veiculo veiculo = (Veiculo) cmbVeiculo.getSelectedItem();

        if (veiculo == null) {
            lblMensagem.setText("Selecione um veículo!");
            return;
        }

        try {
            veiculo.retornarDaManutencao();
            lblMensagem.setForeground(new Color(0, 128, 0));
            lblMensagem.setText("Veículo retirado da manutenção!");
        } catch (OperacaoInvalidaException e) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(e.getMessage());
        } finally{
            limparMensagem();
        }
    }

    private void recarregarBateria() {
        Veiculo veiculo = (Veiculo) cmbVeiculo.getSelectedItem();

        if (veiculo == null) {
            lblMensagem.setText("Selecione um veículo elétrico!");
            limparMensagem();
            return;
        }

        try {
            veiculo.recarregarBateria();
            lblMensagem.setForeground(new Color(0, 128, 0));
            lblMensagem.setText("Veículo recarregado!");
        } catch (OperacaoInvalidaException e) {
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
