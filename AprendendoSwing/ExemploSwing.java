import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.*;

public class ExemploSwing extends JFrame {

    // Componentes da interface
    private JTextField tfTexto = new JTextField(15);
    private JButton btEnviar = new JButton("Enviar");
    private JButton btLimpar = new JButton("Limpar");
    private JLabel lbResultado = new JLabel("Digite alguma coisa");

    public static void main(String[] args) {
        new ExemploSwing();
    }

    public ExemploSwing() {
        // Define o título da janela
        super("Exemplo com FlowLayout");

        // Organiza os componentes em sequência
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        // Adiciona os componentes à janela
        add(new JLabel("Texto:"));
        add(tfTexto);
        add(btEnviar);
        add(btLimpar);
        add(lbResultado);
        
        // Define a ação de cada botão
        btEnviar.addActionListener(e -> enviarTexto());
        btLimpar.addActionListener(e -> limparTexto());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 180);
        setLocationRelativeTo(null); // Centraliza a janela
        setVisible(true);
    }

    // Exibe o conteúdo digitado
    private void enviarTexto() {
        String texto = tfTexto.getText();

        if (texto.isEmpty()) {
            lbResultado.setText("Digite algo primeiro!");
        } else {
            lbResultado.setText("Você digitou: " + texto);
        }
    }

    // Limpa o campo e o resultado
    private void limparTexto() {
        tfTexto.setText("");
        lbResultado.setText("Digite alguma coisa");
    }
}
