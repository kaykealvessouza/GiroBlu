package Planos;
import Modelo.Veiculo;
import Interfaces.Plano;

public class PlanoMensal implements Plano {

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