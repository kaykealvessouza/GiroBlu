
package Financeiro;

public enum TipoMovimentacao {

    CREDITO("Crédito"),
    DEBITO("Débito");

    private final String descricao;

    TipoMovimentacao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
