package Interfaces;

import java.time.LocalDateTime;
import Financeiro.TipoMovimentacao;

public interface MovimentacaoFinanceira {

    // ==================== CONSULTAR DATA E HORA DA MOVIMENTAÇÃO ====================

    LocalDateTime consultarDataHoraMovimentacao();

    // ==================== CONSULTAR VALOR DA MOVIMENTAÇÃO ====================

    double consultarValorMovimentacao();

    // ==================== CONSULTAR TIPO DA MOVIMENTAÇÃO ====================

    TipoMovimentacao consultarTipoMovimentacao();

}
