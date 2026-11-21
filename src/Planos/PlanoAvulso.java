package Planos;
import Modelo.Veiculo;
import Interfaces.Plano;

public class PlanoAvulso implements Plano {

    // ==================== CALCULAR VALOR DA VIAGEM ====================
    // Status: IMPLEMENTADO

    @Override
    public double calcularValor(Veiculo veiculo, long minutos) {
        return veiculo.consultarTarifaDeDesbloqueio() + veiculo.consultarTarifaPorMinuto() * minutos;
    }


    // ==================== CONSULTAR NOME DO PLANO ====================
    // Status: IMPLEMENTADO

    @Override
    public String consultarNome() {
        return "Avulso";
    }

}