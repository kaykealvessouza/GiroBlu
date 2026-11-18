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

# Atualizações
# GiroBlu — Atualizações necessárias no UML

## 1. Alterações realizadas no MD

### 1.1. Sistema

- Adicionada a exceção `SemViagemEmAndamentoException` à assinatura de `finalizarViagem(Viagem, Estacao, LocalDateTime)`.
- Mantidas as consultas individuais de estação, veículo e usuário, agora com `RegistroNaoEncontradoException`.
- Adicionada a exceção `RegistroJaExistenteException` aos métodos:
  - `cadastrarEstacao(Estacao)`.
  - `cadastrarUsuario(Usuario)`.

### 1.2. Usuario

- Mantido `finalizarViagemAtiva(Viagem)`, com `SemViagemEmAndamentoException`.
- Adicionado `consultarExtrato(): List<LinhaExtrato>`, delegando a consulta à carteira.

### 1.3. Viagem

- Adicionada `SemViagemEmAndamentoException` à assinatura de `finalizarViagem(Estacao, LocalDateTime)`.
- Definido que a viagem deve validar se ainda está em andamento e se a data de encerramento é válida antes de alterar seus atributos.

### 1.4. Carteira

- Adicionado `consultarExtrato(): List<LinhaExtrato>`.
- Definido que o extrato deve apresentar as movimentações em ordem cronológica, com o saldo acumulado após cada movimentação.
- A ordenação deve ocorrer sobre uma cópia do histórico, preservando a lista original.
- Movimentações com data e horário iguais devem preservar a ordem original de registro.
- O cálculo do saldo considera créditos e débitos por meio da interface `MovimentacaoFinanceira`.

### 1.5. Nova classe LinhaExtrato

Criada a classe `LinhaExtrato`, com:

**Atributos:**
- `movimentacao: MovimentacaoFinanceira`
- `saldoApos: double`

**Construtor:**
- `LinhaExtrato(MovimentacaoFinanceira, double)`

**Métodos:**
- `consultarMovimentacao(): MovimentacaoFinanceira`
- `consultarSaldoApos(): double`

Os atributos são imutáveis (`final`). A classe não implementa `Serializable`, pois suas instâncias serão geradas a partir do histórico financeiro persistido.

### 1.6. Exceções

Adicionadas duas novas exceções à hierarquia `GiroBluException`:

- `RegistroNaoEncontradoException`: consulta de estação, veículo ou usuário sem resultado.
- `RegistroJaExistenteException`: tentativa de cadastrar estação com nome duplicado ou usuário com e-mail duplicado.

Também foi atualizada a descrição de `SemViagemEmAndamentoException`, abrangendo viagens inexistentes, já encerradas ou diferentes da viagem ativa do usuário.

---

## 2. Alterações necessárias no UML

### 2.1. Sistema

Atualizar as assinaturas:

- `consultarEstacao(String): Estacao {throws RegistroNaoEncontradoException}`
- `consultarVeiculo(int): Veiculo {throws RegistroNaoEncontradoException}`
- `consultarUsuario(String): Usuario {throws RegistroNaoEncontradoException}`
- `cadastrarEstacao(Estacao): void {throws RegistroJaExistenteException}`
- `cadastrarUsuario(Usuario): void {throws RegistroJaExistenteException}`
- `finalizarViagem(Viagem, Estacao, LocalDateTime): void {throws SemViagemEmAndamentoException, DataInvalidaException, EstacaoLotadaException}`

### 2.2. Estacao

Adicionar:

- `consultarNome(): String`

### 2.3. Usuario

Adicionar:

- `consultarExtrato(): List<LinhaExtrato>`

Manter:

- `finalizarViagemAtiva(Viagem): void {throws SemViagemEmAndamentoException}`

Remover:

- `consultarHistoricoMovimentacoes(): List<MovimentacaoFinanceira>`

O histórico de movimentações continua sendo responsabilidade da `Carteira`. O usuário poderá consultar o extrato por meio de `consultarExtrato()`.

### 2.4. Viagem

Atualizar:

- `finalizarViagem(Estacao, LocalDateTime): void {throws SemViagemEmAndamentoException, DataInvalidaException}`

### 2.5. Carteira

Adicionar:

- `consultarExtrato(): List<LinhaExtrato>`

### 2.6. LinhaExtrato

Criar a nova classe com:

**Atributos privados e finais:**

- `movimentacao: MovimentacaoFinanceira`
- `saldoApos: double`

**Métodos públicos:**

- `LinhaExtrato(MovimentacaoFinanceira, double)`
- `consultarMovimentacao(): MovimentacaoFinanceira`
- `consultarSaldoApos(): double`

### 2.7. Relacionamentos

- `Carteira` deve possuir uma dependência de criação/uso de `LinhaExtrato`, pois gera as linhas ao consultar o extrato.
- `LinhaExtrato` deve possuir uma associação com `MovimentacaoFinanceira`, pois mantém uma referência à movimentação correspondente. Multiplicidade: `movimentacao 1`.
- `Usuario` já possui associação com `Carteira`; não é necessário criar outra associação estrutural entre eles por causa do extrato.
- Adicionar `RegistroNaoEncontradoException` e `RegistroJaExistenteException` à hierarquia de herança de `GiroBluException`.
- Atualizar os relacionamentos de dependência das exceções nos métodos que as lançam ou propagam.

### 2.8. Limpeza opcional dos relacionamentos

Corrigir as setas que estão sem referências estruturais no XML do diagrama:

- `-105`, `-107`, `-109` e `-111`: setas de `Viagem` para `Usuario`, `Veiculo` e as duas `Estacao`, atualmente sem `source`.
- `-78`: seta de `Usuario` para `Carteira`, atualmente sem `target`.

Essas setas funcionam visualmente, mas não acompanham as classes caso sejam movidas no diagrama. Recomenda-se corrigir suas referências para manter os relacionamentos vinculados aos elementos correspondentes.

---

**Observações:**

- Não é necessário criar uma associação permanente entre `Carteira` e `LinhaExtrato`, pois as linhas são objetos temporários gerados durante a consulta. Uma dependência de criação é suficiente.
- A ordem das operações, as regras de cadastro, a observação sobre `estacaoAtual` (o `0..1` já está), o estado inicial e a manutenção repetida não exigem alterações no UML.