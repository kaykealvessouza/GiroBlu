package Planos;

import Modelo.Veiculo;
import Interfaces.Plano;

public class PlanoAvulso implements Plano {

    // ==================== CALCULAR VALOR DA VIAGEM ====================
    // Status: IMPLEMENTADO

    @Override
    public double calcularValor(Veiculo veiculo, long minutos) {
        if (veiculo == null) {
            throw new IllegalArgumentException("O veículo não pode ser nulo.");
        }

        if (minutos < 0) {
            throw new IllegalArgumentException("A duração deve ser maior que zero.");
        }

        return veiculo.consultarTarifaDeDesbloqueio() + veiculo.consultarTarifaPorMinuto() * minutos;
    }

    // ==================== CONSULTAR NOME DO PLANO ====================
    // Status: IMPLEMENTADO

    @Override
    public String consultarNome() {
        return "Avulso";
    }

}