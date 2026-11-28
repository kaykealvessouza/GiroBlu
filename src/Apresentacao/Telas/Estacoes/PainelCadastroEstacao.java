
package Apresentacao.Telas.Estacoes;

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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import Aplicacao.Sistema;
import Modelo.Estacao;
import Apresentacao.Componentes.Persistir;
import Excecoes.RegistroJaExistenteException;

public class PainelCadastroEstacao extends JPanel {

    private JTextField txtNomeEstacao;
    private JTextField txtCapacidadeMaxima;
    private JButton btnCadastrar;
    private JLabel lblMensagem;

    private Sistema sistema;

    public PainelCadastroEstacao(Sistema sistema) {
        this.sistema = sistema;

        // Define o layout principal
        setLayout(new BorderLayout());
        setBackground(new Color(237, 241, 244));

        // Painel que organiza os componentes verticalmente
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBackground(new Color(237, 241, 244));
        painelFormulario.setBorder(
                BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Define o tamanho dos rótulos para alinhar os campos
        Dimension tamanhoLabel = new Dimension(150, 35);

        // Painel do nome da estação
        JPanel painelNome = new JPanel(new BorderLayout(10, 0));
        painelNome.setBackground(new Color(237, 241, 244));
        painelNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel lblNome = new JLabel("Nome da estação:");
        lblNome.setPreferredSize(tamanhoLabel);

        painelNome.add(lblNome, BorderLayout.WEST);

        txtNomeEstacao = new JTextField();
        txtNomeEstacao.setPreferredSize(new Dimension(300, 35));

        painelNome.add(txtNomeEstacao, BorderLayout.CENTER);

        painelFormulario.add(painelNome);

        // Espaçamento entre os campos
        painelFormulario.add(Box.createVerticalStrut(12));

        // Painel da capacidade máxima
        JPanel painelCapacidade = new JPanel(new BorderLayout(10, 0));
        painelCapacidade.setBackground(new Color(237, 241, 244));
        painelCapacidade.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel lblCapacidade = new JLabel("Capacidade máxima:");
        lblCapacidade.setPreferredSize(tamanhoLabel);

        painelCapacidade.add(lblCapacidade, BorderLayout.WEST);

        txtCapacidadeMaxima = new JTextField();
        txtCapacidadeMaxima.setPreferredSize(new Dimension(300, 35));

        painelCapacidade.add(txtCapacidadeMaxima, BorderLayout.CENTER);

        painelFormulario.add(painelCapacidade);

        // Espaçamento entre os campos e o botão
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

        // Efeito hover do botão
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

        // Chama o método ao clicar
        btnCadastrar.addActionListener(e -> cadastrarEstacao());

        painelFormulario.add(btnCadastrar);

        // Espaçamento entre o botão e a mensagem
        painelFormulario.add(Box.createVerticalStrut(10));

        // Mensagem de sucesso ou erro
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMensagem.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblMensagem.setPreferredSize(new Dimension(500, 25));
        lblMensagem.setMinimumSize(new Dimension(500, 25));
        lblMensagem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

        painelFormulario.add(lblMensagem);

        // Adiciona o formulário ao painel principal
        add(painelFormulario, BorderLayout.CENTER);
    }

    private void cadastrarEstacao() {
        try {
            Estacao estacao = new Estacao(
                    txtNomeEstacao.getText(),
                    Integer.parseInt(txtCapacidadeMaxima.getText().trim()));

            sistema.cadastrarEstacao(estacao);

            lblMensagem.setForeground(new Color(39, 174, 96));
            lblMensagem.setText("Estação cadastrada com sucesso!");

            txtNomeEstacao.setText("");
            txtCapacidadeMaxima.setText("");

            Persistir.salvar(sistema, lblMensagem); // depois do sucesso

        } catch (NumberFormatException e) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText("A capacidade deve ser um número inteiro.");

        } catch (IllegalArgumentException e) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText(e.getMessage());

        } catch (RegistroJaExistenteException e) {
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
