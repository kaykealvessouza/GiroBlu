package Planos;
import java.io.Serializable;
import Modelo.Veiculo;
import Interfaces.Plano;

public class PlanoUniversitario implements Plano, Serializable {

    // ==================== ATRIBUTOS ====================
    
    private static final long serialVersionUID = 1L;

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
