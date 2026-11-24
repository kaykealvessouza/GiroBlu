package AprendendoPersistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExemploLeituraBinaria {

    public static void main(String[] args) {

        File arquivo = new File("dados/exemplo.dat");

        // Verifica se o arquivo existe antes da leitura.
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado!");
            return;
        }

        try (FileInputStream fis = new FileInputStream(arquivo)) {

            int byteLido;

            // read() retorna o próximo byte.
            // Quando chega ao final, retorna -1.
            while ((byteLido = fis.read()) != -1) {

                System.out.println("Byte: " + byteLido);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler: " + e.getMessage());
        }
    }
}
