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



