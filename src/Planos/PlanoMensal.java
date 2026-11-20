package Planos;
import Modelo.Veiculo;
import Interfaces.Plano;

public class PlanoMensal implements Plano {

    // ==================== CALCULAR VALOR DA VIAGEM ====================
    // Status: IMPLEMENTADO

    @Override
    public double calcularValor(Veiculo veiculo, long minutos) {
        long minutosCobrados = Math.max(0, minutos - 15);
        return (veiculo.consultarTarifaPorMinuto() * minutosCobrados);
    }


    // ==================== CONSULTAR NOME DO PLANO ====================
    // Status: IMPLEMENTADO

    @Override
    public String consultarNome() {
        return "Mensal";
    }

}