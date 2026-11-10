import javax.swing.*;
import java.awt.*;

public class ExemploSwingCalculo extends JFrame {

    // Campos para receber os números
    private JTextField tfNumero1 = new JTextField();
    private JTextField tfNumero2 = new JTextField();

    // Botão para executar o cálculo
    private JButton btSomar = new JButton("Somar");

    // Resultado da operação
    private JLabel lbResultado = new JLabel("Resultado: ");

    public static void main(String[] args) {
        new ExemploSwingCalculo();
    }

    public ExemploSwingCalculo() {
        super("Calculadora Simples");

        // Cria uma grade com 3 linhas e 2 colunas
        setLayout(new GridLayout(3, 2, 10, 10));

        // Adiciona os componentes na ordem da grade
        add(new JLabel("Primeiro número:"));
        add(tfNumero1);

        add(new JLabel("Segundo número:"));
        add(tfNumero2);

        add(btSomar);
        add(lbResultado);

        // Registra a ação do botão
        btSomar.addActionListener(e -> somar());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 180);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void somar() {

        try {
            // Converte o texto dos campos para números inteiros
            int numero1 = Integer.parseInt(tfNumero1.getText());
            int numero2 = Integer.parseInt(tfNumero2.getText());

            // Realiza a operação
            int resultado = numero1 + numero2;

            // Atualiza o resultado na tela
            lbResultado.setText("Resultado: " + resultado);

        } catch (NumberFormatException erro) {
            // Executado quando o usuário digita algo que não é número
            JOptionPane.showMessageDialog(
                this,
                "Digite apenas números inteiros!"
            );
        }
    }
}
