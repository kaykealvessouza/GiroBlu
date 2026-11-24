package AprendendoPersistencia;

import java.io.File;
import java.io.IOException;

public class ExemploSistemaArquivos {

    public static void main(String[] args) {

        // Representa um diretório.
        // Não precisa existir para criar o objeto File.
        File diretorio = new File("dados");

        // Verifica se o diretório existe.
        System.out.println("Existe? " + diretorio.exists());

        // Cria o diretório caso ele ainda não exista.
        if (!diretorio.exists()) {
            boolean criado = diretorio.mkdir();

            System.out.println("Diretório criado? " + criado);
        }

        // Representa um arquivo dentro do diretório.
        File arquivo = new File(diretorio, "exemplo.txt");

        // Verifica se o arquivo existe.
        System.out.println("Arquivo existe? " + arquivo.exists());

        // Cria o arquivo fisicamente.
        try {
            if (arquivo.createNewFile()) {
                System.out.println("Arquivo criado!");
            } else {
                System.out.println("Arquivo já existia.");
            }
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        // Consulta informações do arquivo.
        System.out.println("Nome: " + arquivo.getName());
        System.out.println("É arquivo? " + arquivo.isFile());
        System.out.println("É diretório? " + arquivo.isDirectory());
        System.out.println("Tamanho: " + arquivo.length() + " bytes");

        // Lista os arquivos presentes no diretório.
        File[] arquivos = diretorio.listFiles();

        if (arquivos != null) {
            for (File item : arquivos) {
                System.out.println(item.getName());
            }
        }

        // Exclui o arquivo.
        // Retorna true se conseguiu excluir.
        // Não exclui diretórios que ainda possuem conteúdo.
        // System.out.println(arquivo.delete());
    }
}
