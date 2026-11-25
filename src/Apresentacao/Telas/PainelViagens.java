package Apresentacao.Telas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;

public class PainelViagens extends JPanel {

    public PainelViagens() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Tela de Viagens", JLabel.CENTER);

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        add(lblTitulo, BorderLayout.CENTER);
    }
}