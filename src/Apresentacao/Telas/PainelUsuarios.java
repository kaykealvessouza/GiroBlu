package Apresentacao.Telas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;

public class PainelUsuarios extends JPanel {

    public PainelUsuarios() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Tela de Usuários", JLabel.CENTER);

        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        add(lblTitulo, BorderLayout.CENTER);
    }
}