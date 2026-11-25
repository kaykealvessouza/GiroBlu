package Aplicacao;

import javax.swing.SwingUtilities;
import Apresentacao.TelaPrincipal;

public class Main {
    public static final String CODIGO_HOMOLOGACAO = "GB-POO-26-0B7";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();
            TelaPrincipal tela = new TelaPrincipal(sistema);
            tela.setVisible(true);
        });
    }
}
