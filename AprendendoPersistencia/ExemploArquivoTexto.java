package AprendendoPersistencia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExemploArquivoTexto {

    public static void main(String[] args) {

        File arquivo = new File("dados/anotacoes.txt");

        // O segundo parâmetro indica se queremos acrescentar texto.
        // false = sobrescrever.
        // true = acrescentar ao final.
        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter(arquivo, false))) {

            escritor.write("Estudando persistência em Java.");
            escritor.newLine();

            escritor.write("Arquivos de texto são legíveis.");
            escritor.newLine();

            escritor.write("Utilizando BufferedWriter.");

            System.out.println("Texto gravado!");

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
