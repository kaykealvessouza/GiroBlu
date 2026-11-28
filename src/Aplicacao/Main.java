package Aplicacao;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import Apresentacao.TelaPrincipal;
import Excecoes.PersistenciaException;

public class Main {
    public static final String CODIGO_HOMOLOGACAO = "GB-POO-26-0B7";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();

            // Tenta carregar tudo do arquivo pro sistema
            try {
                sistema.carregarDados();
            } catch (PersistenciaException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Erro ao carregar dados", JOptionPane.ERROR_MESSAGE);
            }

            TelaPrincipal tela = new TelaPrincipal(sistema);
            tela.setVisible(true);
        });
    }
}
