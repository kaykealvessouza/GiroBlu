package AprendendoPersistencia;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ExemploDadosEstruturados {

    public static void main(String[] args) {

        String caminho = "dados/usuario.dat";

        // GRAVAÇÃO
        try (DataOutputStream saida = new DataOutputStream(
                new FileOutputStream(caminho))) {

            saida.writeInt(10);
            saida.writeUTF("Ka");
            saida.writeDouble(150.50);
            saida.writeBoolean(true);

            System.out.println("Usuário gravado!");

        } catch (IOException e) {
            System.out.println("Erro ao gravar: " + e.getMessage());
        }

        // LEITURA
        try (DataInputStream entrada = new DataInputStream(
                new FileInputStream(caminho))) {

            // A ordem de leitura deve ser a mesma da gravação!
            int codigo = entrada.readInt();
            String nome = entrada.readUTF();
            double saldo = entrada.readDouble();
            boolean ativo = entrada.readBoolean();

            System.out.println("Código: " + codigo);
            System.out.println("Nome: " + nome);
            System.out.println("Saldo: " + saldo);
            System.out.println("Ativo: " + ativo);

        } catch (IOException e) {
            System.out.println("Erro ao ler: " + e.getMessage());
        }
    }
}

