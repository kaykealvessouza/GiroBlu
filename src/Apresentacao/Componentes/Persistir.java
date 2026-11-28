package Apresentacao.Componentes;

import java.awt.Color;
import javax.swing.JLabel;
import Aplicacao.Sistema;
import Excecoes.PersistenciaException;

public class Persistir {

    // Salva depois de uma operação bem-sucedida; se falhar, avisa na própria tela
    public static void salvar(Sistema sistema, JLabel lblMensagem) {
        try {
            sistema.salvarDados();
        } catch (PersistenciaException e) {
            lblMensagem.setForeground(Color.RED);
            lblMensagem.setText("Operação concluída, mas não foi possível salvar os dados: "
                    + e.getMessage());
        }
    }
}