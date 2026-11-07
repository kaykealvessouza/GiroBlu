# GIROBLU

- Descrição de cada classe dentro do UML

# 1.0 RECARGA:
- Criei essa classe para deixar separado da Carteira, assim vai ficar mais organizado para gerar os extratos (Que o exercício pede)
- Vai ter os atributos obrigatórios (valor, data, hora)
- Dentro construtor tem que fazer o {throws ValorInvalidoException} para validar se o valor para recarga é válido

# 1.1 CARTEIRA:
- Coloquei apenas getSaldo() para centralizar a mudança de saldo apenas nos métodos recarregarSaldo() e debitar()
- recarregarSaldo(): vai criar o um new Recarga, vai adicionar na lista e atualizar o saldo

public void recarregarSaldo(double valor, LocalDateTime dataHora) throws ValorInvalidoException {
    Recarga nova = new Recarga(valor, dataHora);  
    recarga.add(nova);                           
    saldo += valor;
}

- debitar(): Subtrai do saldo sem checar se fica negativo
- temSaldoMinimo(): método para validar se tem os R$ 5,00 para iniciar a corrida

# 1.2 ESTACAO:
- Tem os atributos nome e qtdVagas, mas eu coloquei o atributo veiculos para saber qual o veículo está ali naquela estação
- recebeVeiculo(): coloquei o 'veiculo: Veiculo' para que quando o método receber o veiculo, ele guarde dentro do ArrayList e assim conseguimos controlar a quantidade de vagas:

    recebeVeiculo(veiculo):
    se veiculos.size() >= qtdVagas → lança EstacaoLotadaException (Para que a tela seja obrigada a tratar o erro)
    veiculos.add(veiculo)
- removerVeiculo(): Remove o veículo quando ele está em uso
- listarDisponível(): puxa apenas os veículos que estão status disponivel

# 1.3 VEICULO:
- Vai ser uma classe abstrata para os outros veículos.
- Criei os atributos:
    - codigo >
    - modelo > Em string para colocar o modelo do veículo
    - status > Vi com o claude para criar um enumerate, fica mais organizado porque já deixamos o padrão de status