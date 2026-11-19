package Interfaces;
import Modelo.Veiculo;

public interface Plano {

    // ==================== CALCULAR VALOR DA VIAGEM ====================

    public double calcularValor(Veiculo veiculo, long minutos);


    // ==================== CONSULTAR NOME DO PLANO ====================

    public String consultarNome();

}