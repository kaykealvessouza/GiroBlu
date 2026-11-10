import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;

public class ExemploGui extends JFrame {

    private JTextField tfNome = new JTextField();
    private JButton btBotao = new JButton();

    public static void main(String[] args) {
        new ExemploGui();
        
    }

    public ExemploGui(){
        super("Título");
        
         
        setLayout(new BorderLayout(10, 10));

        add(new JLabel("Nome:"), BorderLayout.WEST);
        add(tfNome, BorderLayout.CENTER);

        btBotao.setText("Exibir mensagem");
        add(btBotao, BorderLayout.EAST);

        btBotao.addActionListener(e -> executarClique());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(340, 240);
        setVisible(true);
    }

    private void executarClique(){

        if (tfNome.getText().equals("Corinthians")){
            JOptionPane.showMessageDialog(null, tfNome.getText() + " vai cair pra serie B");
        } else{
            JOptionPane.showMessageDialog(null, tfNome.getText() + " fds");
        }

    }
}
