package Interfaces;
import Modelo.Veiculo;

public interface Plano {

    // ==================== CALCULAR VALOR DA VIAGEM ====================
    // Status: NÃO INICIADO

    public double calcularValor(Veiculo veiculo, long minutos);


    // ==================== CONSULTAR NOME DO PLANO ====================
    // Status: NÃO INICIADO

    public String consultarNome();

}