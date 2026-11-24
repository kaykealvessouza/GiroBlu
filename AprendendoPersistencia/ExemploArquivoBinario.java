
package AprendendoPersistencia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExemploArquivoBinario {

    public static void main(String[] args) {

        File arquivo = new File("dados/exemplo.dat");

        // Dados que serão gravados.
        byte[] dados = {79, 105, 33};

        // try-with-resources fecha o arquivo automaticamente.
        try (FileOutputStream fos = new FileOutputStream(arquivo)) {

            // Grava todo o array de bytes.
            fos.write(dados);

            // Garante que os dados pendentes sejam enviados.
            fos.flush();

            System.out.println("Dados gravados!");

        } catch (IOException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
        }

        // Acrescentando dados ao arquivo existente.
        try (FileOutputStream fos = new FileOutputStream(arquivo, true)) {

            // Grava mais um byte sem apagar o conteúdo anterior.
            fos.write(65);

            System.out.println("Novo byte acrescentado!");

        } catch (IOException e) {
            System.out.println("Erro ao acrescentar: " + e.getMessage());
        }
    }
}
