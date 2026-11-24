package AprendendoPersistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExemploLeituraTexto {

    public static void main(String[] args) {

        File arquivo = new File("dados/anotacoes.txt");

        try (BufferedReader leitor = new BufferedReader(
                new FileReader(arquivo))) {

            String linha;

            // readLine() retorna null quando chega ao final.
            while ((linha = leitor.readLine()) != null) {

                System.out.println(linha);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler: " + e.getMessage());
        }
    }
}
