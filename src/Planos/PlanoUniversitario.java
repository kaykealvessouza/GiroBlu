package Planos;
import Modelo.Veiculo;
import Interfaces.Plano;

public class PlanoUniversitario implements Plano {

    // ==================== CALCULAR VALOR DA VIAGEM ====================
    // Status: IMPLEMENTADO


    @Override
    public double calcularValor(Veiculo veiculo, long minutos) {

        double valorIntegral = (veiculo.consultarTarifaDeDesbloqueio() + veiculo.consultarTarifaPorMinuto() * minutos);

        if (veiculo.aceitaDescontoUniversitario()) {
            return valorIntegral * 0.50;
        }
        return valorIntegral;
    }


    // ==================== CONSULTAR NOME DO PLANO ====================
    // Status: IMPLEMENTADO

    @Override
    public String consultarNome() {
        return "Universitário";
    }

}
