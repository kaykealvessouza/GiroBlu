# GiroBlu — Roteiro de montagem do código (v2)

Baseado no md de 14/11 e no enunciado. **Não tem código pronto**: para cada classe e método diz o que fazer, o que lembrar e o que testar. Os itens estão **na ordem em que devem ser feitos**.

## Como ler

- `[ ] implementado` / `[ ] testado`: marque cada um. Getters simples só precisam de "implementado".
- ⚠ **Antes:** o que precisa estar pronto para fazer ou testar este item.
- 📌 **Lembre:** regra que não pode ser esquecida.
- ✅ **Teste:** casos sugeridos para o JUnit.
- Todo método público de negócio que não seja getter/setter precisa de teste (exigência do enunciado).

## Regrinhas

1. Mudou um contrato (nome, parâmetro, `throws`)? Ordem: **md → esqueleto → UML → este roteiro**.
2. Os `throws` do esqueleto são iguais aos da tabela da seção 5 do md.
3. O pacote de negócio **nunca** importa `javax.swing`. Nenhuma regra de negócio na tela.
4. Estado só muda por métodos que validam a regra.
5. Nada de `if`/`instanceof` perguntando o tipo concreto. O que varia por tipo se resolve por método sobrescrito. (Regra do professor)
6. Método que devolve coleção devolve cópia ou lista não modificável, nunca o `ArrayList` interno.
7. Javadoc junto com o método.
8. **Classes abstratas se testam por meio das concretas.** Implemente a classe abstrata e rode os testes depois que as concretas existirem (avisos ⚠ abaixo).

---

## Antes de implementar: 3 pontos que ainda estão em aberto


**1. Persistência: binário ou CSV?**
- **O que é:** o enunciado do professor exiger gravar tudo em arquivo e recuperar ao abrir, inclusive viagens em andamento. falta decidir o formato e como vamos fazer isso.
- **Por que importa:** com binário (`Serializable`) o código das classes quase não muda. Com CSV, `Veiculo` e `Viagem` precisam de um jeito de ser reconstruídos (código do veículo, estado, identificador de viagem).
- **Contador de códigos:** decidir se `contadorCodigo` guarda o **último código usado** (novo = contador + 1) ou o **próximo a usar**. Depois de carregar o arquivo, o contador precisa voltar ao valor certo para não repetir código.
- **O que fazer agora:** tudo até o item 20 **não depende** disso. Não coloque `Serializable` ainda. O item 21 só começa depois da decisão.

**2. Duas dúvidas para decidir certinho**
- **Duração com segundos:** uma viagem de 12 min e 40 s cobra 12 ou 13 minutos?
- **Período do faturamento:** as duas datas entram (inclusivo)? (Exemplo: se consultar de 01/11 a 30/11 e existir viagem nesses dias, elas entram ou só as do meio, eu creio que entram, nem precisaria questionar na minha visão)
- **Premissa até responder:** minutos inteiros (descarta os segundos e poderia até arredondar pra cima se passar de 30) e período inclusivo. Mas é minha opinião né. Provavelmente professor falaria pra fazer o que achar melhor então, né.
- **O que fazer:** implementar cada regra **em um único lugar** (`Viagem.calcularDuracao()` e `Sistema.consultarFaturamento()`) para trocar fácil depois.

**3. Validações extras (opcional)**
- Estação com capacidade ≤ 0, nome ou e-mail vazios: o md **não** pede. É uma decisão nova (exceção, md, UML e testes). Por padrão: não validar, mas tem que ver melhor.

---

## Mapa da ordem

| Etapa | Itens | Pré-requisito |
|---|---|---|
| Fundação | 1 Enums, 2 Exceções | — |
| Contratos | 3 `MovimentacaoFinanceira`, 4 `Plano` | 1 |
| Financeiro simples | 5 `Recarga`, 6 `LinhaExtrato` | 1, 2, 3 |
| Frota e estações | 7 `Veiculo`, 8 `VeiculoEletrico`, 9 a 11 concretas, 12 `Estacao` | 1, 2 |
| Planos | 13 `PlanoAvulso`, 14 `PlanoMensal`, 15 `PlanoUniversitario` | 4, 9 a 11 |
| Carteira, usuário, viagem | 16 `Carteira`, 17 `Usuario`, 18 `Viagem` | 3, 5, 6, 12, 13 a 15 |
| Relatório e sistema | 19 `RelatorioFaturamento`, 20 `Sistema` | tudo acima |
| Depois do núcleo | 21 Persistência, 22 Tela | decisão do ponto 1 |

---

# 1. Enums

### 1.1 `EstadoVeiculo` · [ X ] implementado
- Valores: `DISPONIVEL`, `EM_USO`, `MANUTENCAO`.

### 1.2 `TipoVeiculo` · [ X ] implementado
- Valores: `BICICLETA_MECANICA`, `BICICLETA_ELETRICA`, `PATINETE_ELETRICO`.

### 1.3 `TipoMovimentacao` · [ X ] implementado
- Valores: `CREDITO`, `DEBITO`.

📌 Sem lógica nenhuma. Serve para evitar texto livre.

---

# 2. Exceções

Todas são **checadas**, herdam de `GiroBluException` e têm construtor `(String mensagem)`.

### 2.1 `GiroBluException` · [ X ] implementado
- Herda de `Exception`. Construtor repassa a mensagem para a superclasse.

### 2.2 Exceções filhas · [ X ] todas implementadas

| Exceção | Quando é lançada (resumo do md) |
|---|---|
| `ValorInvalidoException` | Recarga ≤ 0; bateria fora de 0 a 100 |
| `SaldoInsuficienteException` | Saldo menor que R$ 5,00 ao iniciar viagem |
| `ViagemEmAndamentoException` | Usuário já tem viagem ativa |
| `SemViagemEmAndamentoException` | Encerrar viagem inexistente, já encerrada ou que não é a ativa do usuário |
| `VeiculoIndisponivelException` | Veículo não pode ser liberado (indisponível ou elétrico com menos de 20%) |
| `EstacaoLotadaException` | Estação sem vaga |
| `VeiculoNaoEncontradoException` | Veículo **não está nesta estação** |
| `RegistroNaoEncontradoException` | Consulta de estação, veículo ou usuário sem resultado |
| `RegistroJaExistenteException` | Nome de estação ou e-mail de usuário duplicado |
| `DataInvalidaException` | Fim não posterior ao início; período de faturamento invertido |
| `OperacaoInvalidaException` | Manutenção de veículo em uso; retorno de veículo fora de manutenção; recarga de veículo sem bateria ou em uso |
| `PersistenciaException` | Falha ao ler ou gravar arquivos |

📌 As mensagens são exibidas na tela: escreva textos claros. `VeiculoNaoEncontrado` e `RegistroNaoEncontrado` têm nomes parecidos, então use mensagens bem diferentes. Exceções não têm teste próprio: são cobertas pelos testes de quem as lança.

---

# 3. `MovimentacaoFinanceira` (interface)

⚠ **Antes:** 1.3 (`TipoMovimentacao`).

### 3.1 `LocalDateTime consultarDataHoraMovimentacao()` · [ X ] declarado
### 3.2 `double consultarValorMovimentacao()` · [ X ] declarado
### 3.3 `TipoMovimentacao consultarTipoMovimentacao()` · [ X ] declarado

📌 Sem atributos e sem construtor. Implementada por `Recarga` (5) e `Viagem` (18). É a interface própria exigida pelo enunciado.

---

# 4. `Plano` (interface)

⚠ **Antes:** o esqueleto de `Veiculo` precisa existir (já existe).

### 4.1 `double calcularValor(Veiculo veiculo, long minutos)` · [ X ] declarado
### 4.2 `String consultarNome()` · [ X ] declarado

📌 O plano não conhece regra de bateria, disponibilidade nem tipo concreto de veículo.

---

# 5. `Recarga` implements `MovimentacaoFinanceira`

⚠ **Antes:** 1.3, 2 (`ValorInvalidoException`), 3.
**Atributos:** `private double valor`, `private LocalDateTime dataHora`.

### 5.1 Construtor `Recarga(double valor, LocalDateTime dataHora) throws ValorInvalidoException` · [ X ] implementado [ ] testado
- 📌 Lança se `valor <= 0`. Quando lança, nenhum objeto é criado.
- ✅ Teste: valor 0, negativo e positivo.

### 5.2 `consultarDataHoraMovimentacao()` · [ X ] implementado
- Devolve `dataHora`.

### 5.3 `consultarValorMovimentacao()` · [ X ] implementado
- Devolve `valor`.

### 5.4 `consultarTipoMovimentacao()` · [ X ] implementado [ ] testado
- Sempre `CREDITO`.

📌 Não existem `consultarValor()` nem `consultarDataHora()` (removidos). A recarga **não** atualiza o saldo (isso é da `Carteira`).

---

# 6. `LinhaExtrato`

⚠ **Antes:** 3.
**Atributos (ambos `final`):** `MovimentacaoFinanceira movimentacao`, `double saldoApos`.

### 6.1 Construtor `LinhaExtrato(MovimentacaoFinanceira, double)` · [ X ] implementado
### 6.2 `consultarMovimentacao()` · [ X ] implementado
### 6.3 `consultarSaldoApos()` · [ X ] implementado

📌 Imutável, não altera carteira nem movimentações, não calcula o saldo (quem calcula é a `Carteira`, item 16.7). Não é persistida.

---

# 7. `Veiculo` (abstrata)

⚠ **Antes:** 1.1, 1.2, 2 (`VeiculoIndisponivelException`, `OperacaoInvalidaException`) e o esqueleto de `Estacao`.
⚠ **Testes:** só rodam depois de 9 (use `BicicletaMecanica`).
**Atributos:** `private static int contadorCodigo`, `private final int codigoIdentificador`, `private String modelo`, `private EstadoVeiculo estadoAtual`, `private Estacao estacaoAtual`.

### 7.1 Construtor `protected Veiculo(String modelo, Estacao estacaoAtual)` · [ X ] implementado [ ] testado
- 📌 O código é gerado automaticamente a partir do contador compartilhado; nunca vem de fora. Dois veículos nunca repetem código.
- 📌 Estado inicial: `DISPONIVEL`.
- 📌 **Não** adiciona o veículo na lista da estação (quem faz é `Sistema.cadastrarVeiculo`).
- ✅ Teste: dois veículos com códigos diferentes; estado inicial `DISPONIVEL`.

### 7.2 `podeSerLiberado()` · [ X ] implementado [ ] testado
- Retorna `true` só se o estado for `DISPONIVEL`.
- 📌 Os elétricos sobrescrevem e acrescentam a regra da bateria (8.4).

### 7.3 `iniciarUtilizacao() throws VeiculoIndisponivelException` · [ X ] implementado [ ] testado
- Se `podeSerLiberado()` for `false`, lança a exceção **sem alterar nada**.
- Senão: estado `EM_USO` e `estacaoAtual = null` (veículo em uso não ocupa vaga).
- 📌 Use `podeSerLiberado()` (polimórfico). Não pergunte se o veículo é elétrico.
- ✅ Teste: disponível inicia; em manutenção lança; em uso lança; `estacaoAtual` fica nula.

### 7.4 `finalizarUtilizacao(Estacao estacaoDestino, long minutos)` · [ X ] implementado [ ] testado
- Estado volta para `DISPONIVEL` e `estacaoAtual = estacaoDestino`.
- 📌 A base **ignora** `minutos` (quem usa é o elétrico, 8.7).
- 📌 Não adiciona na lista da estação (o `Sistema` chama `estacaoDestino.adicionarVeiculo`).

### 7.5 `enviarParaManutencao() throws OperacaoInvalidaException` · [ X ] implementado [ ] testado
- Lança só se estiver `EM_USO`. Senão, estado `MANUTENCAO`.
- 📌 Se já estiver em manutenção, não faz nada (não é erro).
- ✅ Teste: disponível vai; em uso lança; já em manutenção continua sem erro.

### 7.6 `retornarDaManutencao() throws OperacaoInvalidaException` · [ X ] implementado [ ] testado
- Lança se **não** estiver em `MANUTENCAO`. Senão, `DISPONIVEL`.

### 7.7 `recarregarBateria() throws OperacaoInvalidaException` · [ X ] implementado [ ] testado
- **Sempre lança** (veículo sem bateria).
- 📌 Existe para evitar cast para `VeiculoEletrico`. Os elétricos sobrescrevem (8.8).
- ✅ Teste: bicicleta mecânica lança.

### 7.8 `aceitaDescontoUniversitario()` · [ X ] implementado [ ] testado
- Retorna `true` (padrão herdado).

### 7.9 `consultarDescricao()` · [ X ] implementado [ ] testado
- Texto com tipo (via `consultarTipo()`) e modelo.
- 📌 A tela usa esse método para listar veículos sem perguntar se são elétricos.

### 7.10 Getters · [ X ] implementado
- `consultarEstadoAtual()`, `consultarEstacaoAtual()`, `consultarModelo()`, `consultarCodigo()`.

### 7.11 Abstratos · [ X ] declarados
- `double consultarTarifaDeDesbloqueio()`, `double consultarTarifaPorMinuto()`, `TipoVeiculo consultarTipo()`.

---

# 8. `VeiculoEletrico` (abstrata, herda de `Veiculo`)

⚠ **Antes:** 7, 2 (`ValorInvalidoException`).
⚠ **Testes:** só rodam depois de 10 ou 11.
**Atributo:** `private int nivelBateria`.

### 8.1 Construtor `protected VeiculoEletrico(String, Estacao, int nivelBateria) throws ValorInvalidoException` · [ X ] implementado [ ] testado
- Lança se a bateria for menor que 0 ou maior que 100. Chama `super`.
- ✅ Teste: -1 e 101 lançam; 0 e 100 passam.

### 8.2 `consultarNivelBateria()` · [ X ] implementado

### 8.3 `protected possuiCargaMinimaParaViagem()` · [ X ] implementado [ ] testado
- `true` com 20% ou mais.
- ✅ Teste: 19 não, 20 sim.

### 8.4 `@Override podeSerLiberado()` · [ X ] implementado [ ] testado
- Regra da base **e** carga mínima.

### 8.5 `protected consumirBateria(long minutos)` · [ X ] implementado [ ] testado
- Desconta `minutos × consultarConsumoPorMinuto()`.
- 📌 A bateria **nunca fica abaixo de 0** (consumo maior que a carga zera).
- 📌 Atenção ao tipo: `long` × `int`.
- ✅ Teste: bateria 60% com 20 min de patinete → 20%; consumo maior que a carga → 0%.

### 8.6 `abstract int consultarConsumoPorMinuto()` · [ X ] declarado

### 8.7 `@Override finalizarUtilizacao(Estacao, long)` · [ X ] implementado [ ] testado
- Executa o comportamento da base **e** aplica `consumirBateria(minutos)`.
- 📌 O encerramento é permitido mesmo se a bateria zerar.

### 8.8 `@Override recarregarBateria() throws OperacaoInvalidaException` · [ X ] implementado [ ] testado
- Lança se `EM_USO`. Senão, bateria 100%. Em manutenção pode recarregar.

### 8.9 `@Override consultarDescricao()` · [ X ] implementado [ ] testado
- Descrição da base **mais** a carga atual da bateria.

---

# 9. `BicicletaMecanica` (herda de `Veiculo`)

⚠ **Antes:** 7, 1.2.

### 9.1 Construtor `BicicletaMecanica(String, Estacao)` · [X] implementado
### 9.2 `consultarTarifaDeDesbloqueio()` · [X] implementado [ ] testado → R$ 1,00
### 9.3 `consultarTarifaPorMinuto()` · [X] implementado [ ] testado → R$ 0,15
### 9.4 `consultarTipo()` · [X] implementado → `BICICLETA_MECANICA`

📌 **Não** sobrescreva `recarregarBateria()` nem `aceitaDescontoUniversitario()`: o padrão da base já está certo para ela.
✅ **Agora rode os testes do item 7.**

---

# 10. `BicicletaEletrica` (herda de `VeiculoEletrico`)

⚠ **Antes:** 8.

### 10.1 Construtor `(String, Estacao, int) throws ValorInvalidoException` · [X] implementado [ ] testado (repassa para `super`)
### 10.2 `consultarTarifaDeDesbloqueio()` · [X] implementado [ ] testado → R$ 2,00
### 10.3 `consultarTarifaPorMinuto()` · [X] implementado [ ] testado → R$ 0,30
### 10.4 `consultarTipo()` · [X] implementado → `BICICLETA_ELETRICA`
### 10.5 `consultarConsumoPorMinuto()` · [X] implementado [ ] testado → 1

---

# 11. `PatineteEletrico` (herda de `VeiculoEletrico`)

⚠ **Antes:** 8.

### 11.1 Construtor `(String, Estacao, int) throws ValorInvalidoException` · [X] implementado [ ] testado
### 11.2 `consultarTarifaDeDesbloqueio()` · [X] implementado [ ] testado → R$ 3,00
### 11.3 `consultarTarifaPorMinuto()` · [X] implementado [ ] testado → R$ 0,40
### 11.4 `consultarTipo()` · [X] implementado → `PATINETE_ELETRICO`
### 11.5 `consultarConsumoPorMinuto()` · [X] implementado [ ] testado → 2
### 11.6 `@Override aceitaDescontoUniversitario()` · [X] implementado [ ] testado → `false`

✅ **Agora rode os testes do item 8** (use as duas elétricas).

---

# 12. `Estacao`

⚠ **Antes:** 7, 9 a 11 (os testes usam veículos reais), 2 (`EstacaoLotadaException`, `VeiculoNaoEncontradoException`).
**Atributos:** `private String nome`, `private int capacidadeMaxima`, `private ArrayList<Veiculo> veiculosEstacionados`.

### 12.1 Construtor `Estacao(String nome, int capacidadeMaxima)` · [ ] implementado
- Lista de veículos começa vazia.

### 12.2 `consultarNome()` · [ ] implementado
- 📌 Necessário para `Sistema.consultarEstacao(nome)` e para a tela.

### 12.3 `consultarVagasDisponiveis()` · [ ] implementado [ ] testado
- Capacidade menos veículos estacionados.
- 📌 Veículo em **manutenção ocupa vaga**; veículo em uso não está na lista.

### 12.4 `consultarQuantidadeVeiculosEstacionados()` · [ ] implementado [ ] testado

### 12.5 `adicionarVeiculo(Veiculo) throws EstacaoLotadaException` · [ ] implementado [ ] testado
- Usa `consultarVagasDisponiveis()` para decidir (a regra de capacidade fica em um lugar só).
- 📌 Não altera `estacaoAtual` do veículo.
- ✅ Teste: lotar a estação e tentar mais um.

### 12.6 `removerVeiculo(Veiculo) throws VeiculoNaoEncontradoException` · [ ] implementado [ ] testado
- Lança se o veículo não está nesta estação.

### 12.7 `consultarVeiculosDisponiveis()` · [ ] implementado [ ] testado
- Só veículos com estado `DISPONIVEL` (sem manutenção).
- 📌 Devolve lista que não permite alterar a coleção interna.

---

# 13. `PlanoAvulso` implements `Plano`

⚠ **Antes:** 4, 9 a 11.

### 13.1 `calcularValor(Veiculo, long minutos)` · [X] implementado [ ] testado
- Desbloqueio + minutos × tarifa por minuto.
- ✅ Teste: Carla (mecânica, 12 min) = 2,80.

### 13.2 `consultarNome()` · [X] implementado
- Escolham o texto (por exemplo, "Avulso"); a tela o usa para listar os planos.

📌 Sem atributos. Um único objeto é compartilhado por todos os usuários do plano. Não compara tipo de veículo e **não arredonda** (isso é da `Viagem`). Nos testes com `double`, use margem de tolerância.

---

# 14. `PlanoMensal` implements `Plano`

⚠ **Antes:** 4, 9 a 11.

### 14.1 `calcularValor(Veiculo, long minutos)` · [X] implementado [ ] testado
- **Sem desbloqueio.** Os 15 primeiros minutos são grátis; cobra só o excedente. (long minutosCobrados -  Math.Max serve para trazer o maior número, nunca fica menos que 0)
- 📌 Menos de 15 minutos custa zero (nunca negativo).
- ✅ Teste: Bruno (patinete, 20 min) = 2,00; Eva (mecânica, 10 min) = 0,00.

### 14.2 `consultarNome()` · [X] implementado

---

# 15. `PlanoUniversitario` implements `Plano`

⚠ **Antes:** 4, 9 a 11 (principalmente 11.6).

### 15.1 `calcularValor(Veiculo, long minutos)` · [X] implementado [ ] testado
- Valor integral = desbloqueio + minutos × tarifa por minuto.
- Se `veiculo.aceitaDescontoUniversitario()` for `true`, aplica 50% **sobre o total** (inclui o desbloqueio).
- 📌 Não compare tipo de veículo; só pergunte ao veículo.
- ✅ Teste: Ana (bicicleta elétrica, 25 min) = 4,75; Diego (patinete, 10 min) = 7,00.

### 15.2 `consultarNome()` · [X] implementado

---

# 16. `Carteira`

⚠ **Antes:** 3, 5, 6, 1.3.
⚠ Os testes de 16.6 e 16.7 só podem ser feitos **depois do item 18** (precisam de uma `Viagem` encerrada).
**Atributos:** `private double saldoAtual`, `private List<MovimentacaoFinanceira> historicoMovimentacoes`, `private static final double SALDO_MINIMO = 5.0`.

### 16.1 Construtor `Carteira()` · [ ] implementado [ ] testado
- Saldo zero e histórico vazio.

### 16.2 `consultarSaldoAtual()` · [ ] implementado

### 16.3 `temSaldoMinimo()` · [ ] implementado [ ] testado
- `true` com R$ 5,00 ou mais. Usa a constante.
- ✅ Teste: 4,99 não, 5,00 sim.

### 16.4 `adicionarSaldo(Recarga)` · [ ] implementado [ ] testado
- Guarda no histórico e soma o valor ao saldo.
- 📌 Não declara exceção: a recarga já nasceu validada.

### 16.5 `consultarHistoricoMovimentacoes()` · [ ] implementado [ ] testado
- 📌 Não permite alterar a coleção interna.

### 16.6 `debitarValor(Viagem)` · [ ] implementado [ ] testado
- ⚠ **Antes:** 18 (a viagem precisa estar **encerrada**, com valor e data).
- Guarda no histórico e **subtrai sem checar** (o saldo pode ficar negativo).
- ✅ Teste: débito maior que o saldo deixa o saldo negativo.

### 16.7 `consultarExtrato()` · [ ] implementado [ ] testado
- ⚠ **Antes:** 6 e 18 (para testar).
- Passo a passo:
  1. Trabalhe numa **cópia** do histórico (o original não muda).
  2. Ordene por `consultarDataHoraMovimentacao()`, da mais antiga para a mais recente. Em empate, preserve a ordem de registro.
  3. O saldo parte de **zero**.
  4. `CREDITO` soma e `DEBITO` subtrai, lendo `consultarTipoMovimentacao()` (sem `instanceof`).
  5. Gere uma `LinhaExtrato` por movimentação com o saldo **depois** dela.
- ✅ Teste: recarga registrada depois, mas com data anterior, aparece primeiro; empate de data preserva a ordem; histórico original intacto; o saldo da última linha é igual a `consultarSaldoAtual()`.

---

# 17. `Usuario`

⚠ **Antes:** 4, 13 (para ter um plano real nos testes), 16, 5, 6 e o esqueleto de `Viagem`.
**Atributos:** `nome`, `email`, `viagemAtiva`, `planoContratado`, `carteira`.

### 17.1 Construtor `Usuario(String nome, String email, Plano plano)` · [ ] implementado [ ] testado
- Cria a `Carteira` (saldo zero). Começa sem viagem ativa.

### 17.2 Getters · [ ] implementado
- `consultarNome()`, `consultarEmail()`, `consultarPlanoContratado()`, `consultarViagemAtiva()`.

### 17.3 `possuiViagemEmAndamento()` · [ ] implementado [ ] testado

### 17.4 `registrarViagemAtiva(Viagem) throws ViagemEmAndamentoException` · [ ] implementado [ ] testado
- Lança se já existe viagem ativa; senão guarda.

### 17.5 `finalizarViagemAtiva(Viagem) throws SemViagemEmAndamentoException` · [ ] implementado [ ] testado
- Lança se não há viagem ativa **ou** se a viagem recebida não é a ativa (o mesmo objeto). Senão limpa a referência.
- 📌 Não calcula nem debita (o `Sistema` chama `pagarViagem` antes).

### 17.6 `temSaldoMinimo()` · [ ] implementado [ ] testado
- **Delega à carteira** (a regra de R$ 5,00 mora lá).

### 17.7 `recarregarCarteira(double valor, LocalDateTime dataHora) throws ValorInvalidoException` · [ ] implementado [ ] testado
- Cria a `Recarga` (que valida o valor) e entrega para `carteira.adicionarSaldo`.
- ✅ Teste: valor inválido lança e o saldo não muda.

### 17.8 `pagarViagem(Viagem)` · [ ] implementado [ ] testado
- Delega a `carteira.debitarValor`. Sem exceção (saldo pode ficar negativo).
- ⚠ A viagem precisa estar encerrada (item 18).

### 17.9 `consultarSaldo()` · [ ] implementado

### 17.10 `consultarExtrato()` · [ ] implementado [ ] testado
- Delega à carteira (16.7). **Não** existe mais `consultarHistoricoMovimentacoes()` aqui.

---

# 18. `Viagem` implements `MovimentacaoFinanceira`

⚠ **Antes:** 3, 7 a 12, 13 a 15, 17, 2 (`SemViagemEmAndamentoException`, `DataInvalidaException`).
**Atributos:** `usuario`, `veiculo`, `estacaoOrigem`, `estacaoDestino`, `dataHoraInicio`, `dataHoraFim`, `double valorCobrado`.

### 18.1 Construtor `Viagem(Usuario, Veiculo, Estacao estacaoOrigem, LocalDateTime dataHoraInicio)` · [ ] implementado [ ] testado
- A viagem nasce **em andamento**: sem destino, sem fim e sem valor.

### 18.2 Getters · [ ] implementado
- `consultarUsuario()`, `consultarVeiculo()`, `consultarEstacaoOrigem()`, `consultarEstacaoDestino()`, `consultarDataHoraInicio()`, `consultarDataHoraFim()`, `consultarValorCobrado()`.

### 18.3 `estaEmAndamento()` · [ ] implementado [ ] testado
- Verdadeiro enquanto `dataHoraFim` não foi definida (sem atributo extra).

### 18.4 `calcularDuracao()` · [ ] implementado [ ] testado
- Minutos entre início e fim.
- 📌 Pré-condição (registre no Javadoc): só chamar **depois de encerrar**.
- 📌 Premissa provisória: minutos inteiros (ponto em aberto 2). Mantenha essa regra isolada neste método.

### 18.5 `calcularValor()` · [ ] implementado [ ] testado
- Usa `usuario.consultarPlanoContratado().calcularValor(veiculo, duração)`.
- 📌 A regra de cobrança fica **nos planos**, não aqui. Mesma pré-condição de 18.4.

### 18.6 `finalizarViagem(Estacao, LocalDateTime) throws SemViagemEmAndamentoException, DataInvalidaException` · [ ] implementado [ ] testado
- Ordem obrigatória:
  1. Se a viagem já foi encerrada → `SemViagemEmAndamentoException`.
  2. Se o fim **não for posterior** ao início → `DataInvalidaException`.
  3. **Só depois de validar:** guarda destino e fim, calcula o valor e guarda `valorCobrado` **arredondado em 2 casas**.
- 📌 Se lançar exceção, a viagem continua exatamente como estava.
- 📌 **Não** mexe em veículo, estação nem carteira (quem coordena é o `Sistema`).
- ✅ Teste: encerrar duas vezes; fim igual ou anterior ao início; os 5 exemplos do enunciado (tabela no fim).

### 18.7 `consultarDataHoraMovimentacao()` · [ ] implementado [ ] testado
- Devolve `dataHoraFim` (é `null` enquanto em andamento; a carteira só recebe viagens encerradas).

### 18.8 `consultarValorMovimentacao()` · [ ] implementado
- Devolve `valorCobrado`.

### 18.9 `consultarTipoMovimentacao()` · [ ] implementado [ ] testado
- Sempre `DEBITO`.

✅ **Agora termine os testes de 16.6, 16.7 e 17.8.**

---

# 19. `RelatorioFaturamento`

⚠ **Antes:** 1.2.
**Atributos:** `dataInicial`, `dataFinal`, `Map<TipoVeiculo, Integer> quantidadeViagens`, `Map<TipoVeiculo, Double> faturamentoPorTipo`.

### 19.1 Construtor `(LocalDate, LocalDate, Map<TipoVeiculo,Integer>, Map<TipoVeiculo,Double>)` · [ ] implementado [ ] testado
- 📌 **Copia** os mapas (alterar o mapa original depois não muda o relatório).

### 19.2 `consultarQuantidadeViagens(TipoVeiculo)` · [ ] implementado [ ] testado
- Tipo sem viagens devolve **0**.

### 19.3 `consultarFaturamento(TipoVeiculo)` · [ ] implementado [ ] testado
- Tipo sem viagens devolve **0**.

### 19.4 `consultarQuantidadeTotalViagens()` e `consultarFaturamentoTotal()` · [ ] implementados [ ] testados
- Calculados a partir dos mapas (sem atributo próprio).

### 19.5 `consultarDataInicial()` e `consultarDataFinal()` · [ ] implementados

---

# 20. `Sistema`

⚠ **Antes:** praticamente tudo (itens 1 a 19).
📌 O `Sistema` **coordena**; não concentra regra de veículo, estação, plano ou carteira.
**Atributos:** `ArrayList<Estacao> estacoes`, `ArrayList<Veiculo> veiculos`, `ArrayList<Usuario> usuarios`, `ArrayList<Viagem> viagens`, `ArrayList<Plano> planosDisponiveis`.

### 20.1 Construtor `Sistema()` · [ ] implementado [ ] testado
- Listas vazias e os **3 planos** criados uma única vez, compartilhados por todos.
- ⚠ **Antes:** 13 a 15.

### 20.2 `consultarEstacoes()`, `consultarVeiculos()`, `consultarUsuarios()`, `consultarViagens()` · [ ] implementados [ ] testados
- 📌 Listas que não permitem alterar a coleção interna.

### 20.3 `consultarPlanos()` · [ ] implementado [ ] testado
- Lista não modificável.

### 20.4 `cadastrarEstacao(Estacao) throws RegistroJaExistenteException` · [ ] implementado [ ] testado
- ⚠ **Antes:** 12.2.
- Lança se já existe estação com o mesmo nome, **ignorando maiúsculas/minúsculas e espaços nas pontas**.

### 20.5 `cadastrarUsuario(Usuario) throws RegistroJaExistenteException` · [ ] implementado [ ] testado
- Mesma regra de comparação, aplicada ao e-mail.

### 20.6 `cadastrarVeiculo(Veiculo) throws EstacaoLotadaException` · [ ] implementado [ ] testado
- Assume que a estação (`veiculo.consultarEstacaoAtual()`) já está cadastrada: a tela só oferece estações de `consultarEstacoes()`.
- Ordem: `estacao.adicionarVeiculo(veiculo)` **primeiro**; só depois inclui o veículo em `veiculos`. Se a estação estiver lotada, o veículo **não** entra na lista.
- ✅ Teste: estação lotada não inclui o veículo.

### 20.7 `consultarEstacao(String) throws RegistroNaoEncontradoException` · [ ] implementado [ ] testado
- Mesma comparação de 20.4.

### 20.8 `consultarVeiculo(int codigo) throws RegistroNaoEncontradoException` · [ ] implementado [ ] testado

### 20.9 `consultarUsuario(String email) throws RegistroNaoEncontradoException` · [ ] implementado [ ] testado
- Mesma comparação de 20.5.

### 20.10 `iniciarViagem(Usuario, Veiculo, LocalDateTime) throws ViagemEmAndamentoException, SaldoInsuficienteException, VeiculoIndisponivelException, VeiculoNaoEncontradoException` · [ ] implementado [ ] testado
- Regra geral: **primeiro só leitura (lançando exceção); só depois alterar.** Se qualquer exceção ocorrer, nada pode ter mudado.
- Passo a passo:
  1. Validações: `usuario.possuiViagemEmAndamento()` → `ViagemEmAndamentoException`; `usuario.temSaldoMinimo()` → `SaldoInsuficienteException`; `veiculo.podeSerLiberado()` → `VeiculoIndisponivelException`.
  2. **Leia a estação de origem** (`veiculo.consultarEstacaoAtual()`) **antes** de iniciar a utilização, porque ela vira `null`.
  3. Crie a `Viagem`.
  4. Altere: `origem.removerVeiculo(veiculo)` (primeiro, pois pode lançar), `veiculo.iniciarUtilizacao()`, `usuario.registrarViagemAtiva(viagem)` e adicione em `viagens`.
  5. Devolva a viagem.
- ✅ Teste: cada exceção deixa tudo como estava (veículo ainda na estação, usuário sem viagem ativa).

### 20.11 `finalizarViagem(Viagem, Estacao, LocalDateTime) throws SemViagemEmAndamentoException, DataInvalidaException, EstacaoLotadaException` · [ ] implementado [ ] testado
- Passo a passo:
  1. Validações de leitura: a viagem está em andamento e é a ativa do usuário → `SemViagemEmAndamentoException`; a estação de destino tem vaga → `EstacaoLotadaException`.
  2. `viagem.finalizarViagem(destino, fim)`: valida as datas (`DataInvalidaException`) antes de alterar a si mesma.
  3. `veiculo.finalizarUtilizacao(destino, minutos)`: estado, localização e, nos elétricos, a bateria (polimórfico).
  4. `destino.adicionarVeiculo(veiculo)`.
  5. `usuario.pagarViagem(viagem)` e depois `usuario.finalizarViagemAtiva(viagem)`.
- ✅ Teste: os 5 exemplos do enunciado de ponta a ponta; saldo ficando negativo; estação lotada **não** encerra a viagem; data de fim inválida não altera veículo nem estação.

### 20.12 `consultarViagensDoUsuario(Usuario)` · [ ] implementado [ ] testado

### 20.13 `consultarFaturamento(LocalDate, LocalDate) throws DataInvalidaException` · [ ] implementado [ ] testado
- Lança se a data final for anterior à inicial.
- Considera só viagens **encerradas** com a data de `dataHoraFim` dentro do período (**inclusivo**, premissa provisória: ponto em aberto 2).
- Agrupa por `viagem.consultarVeiculo().consultarTipo()`; devolve um `RelatorioFaturamento`.

---

# 21. Persistência (depois do núcleo)

⚠ **Antes:** decidir o ponto em aberto 1 e ter o item 20 funcionando.

### 21.1 `salvarDados() throws PersistenciaException` · [ ] implementado [ ] testado
### 21.2 `carregarDados() throws PersistenciaException` · [ ] implementado [ ] testado

📌 Pasta `dados-gb26` na raiz do projeto.
📌 Arquivo inexistente na primeira execução **não é erro**: começa vazio, com os 3 planos.
📌 As **viagens em andamento** também precisam sobreviver ao fechar e abrir.
📌 Depois de carregar, restaure o contador de códigos dos veículos.
📌 Quem chama `salvarDados()`: a **tela**, depois de cada operação bem-sucedida (`Veiculo` e `Usuario` mudam de estado sem passar pelo `Sistema`).

### 21.3 Arquivos de dados de exemplo · [ ] criados
- Exigidos na entrega, para o sistema já abrir com informações.

---

# 22. Tela Swing

⚠ **Antes:** 20 e 21.

- [ ] Título da janela principal exatamente `GiroBlu | Painel de Operação`.
- [ ] A classe do `main` declara `public static final String CODIGO_HOMOLOGACAO = "GB-POO-26-0B7";`.
- [ ] Tratar `GiroBluException` com mensagem clara, sem travar e sem perder dados.
- [ ] Funcionalidades 1 a 8 do enunciado: cadastros, recarga, iniciar e encerrar viagem (mostrando duração e valor), manutenção e recarga de bateria, veículos disponíveis na estação (com `consultarDescricao()`), extrato e faturamento.
- [ ] Nenhuma regra de negócio nas telas.

---

## Exemplos do enunciado (casos de teste prontos)

| Situação | Valor | Bateria final |
|---|---:|---:|
| Carla (Avulso), bicicleta mecânica, 12 min | 2,80 | — |
| Ana (Universitário), bicicleta elétrica 80%, 25 min | 4,75 | 55% |
| Bruno (Mensal), patinete 60%, 20 min | 2,00 | 20% |
| Diego (Universitário), patinete 100%, 10 min | 7,00 | 80% |
| Eva (Mensal), bicicleta mecânica, 10 min | 0,00 | — |

Use-os nos itens 13, 14, 15, 18 e 20.11.

---

## Checklist final de entrega

- [ ] Todos os `throws` iguais entre código, md (seção 5) e UML.
- [ ] Nenhum `import javax.swing` no pacote de negócio.
- [ ] Nenhum `instanceof`/`if` por tipo concreto nas regras (só na criação do veículo na tela, se for o caso).
- [ ] Todo método público de negócio que não é getter/setter com teste JUnit passando.
- [ ] Javadoc em todas as classes e métodos públicos.
- [ ] UML exportado em PDF, compatível com o código-fonte.
- [ ] Plano de testes em PDF.
- [ ] Pasta `dados-gb26` com dados de exemplo dentro do `.zip` do projeto.
- [ ] Entrega no AVA até **29/11/2026 às 23:59**.