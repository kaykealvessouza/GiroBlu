# GiroBlu — Plano de Testes (JUnit 5)

Base: enunciado, Anotação e Roteiro de montagem.

O enunciado exige que **todos os métodos públicos da camada de negócio que não sejam getters nem setters** tenham teste. Este plano cobre esses métodos, classe por classe. Cada linha é um caso de teste (CT), e cada CT vira um método `@Test`.

---

## Como ler

- **ID:** identificador do caso, para citar no PDF e na apresentação.
- **Método:** o que está sendo testado.
- **Cenário / entrada:** o que montar antes de chamar o método.
- **Resultado esperado:** o que o teste confere.
- **[ ]:** marque quando o teste estiver implementado e passando.

### Convenções

- Uma classe de teste por classe de negócio: `EstacaoTest`, `CarteiraTest`, `SistemaTest`...
- Valores `double`: `assertEquals(esperado, obtido, 0.001)`. Nunca compare `double` sem tolerância.
- Exceções: `assertThrows(XxxException.class, () -> ...)`.
- "**Nada mudou**": depois da exceção, confira o estado dos objetos (saldo, estado do veículo, quantidade na estação, viagem ativa).
- Use `@BeforeEach` para montar estações, veículos e usuários comuns aos testes da classe.
- Datas fixas, nunca `LocalDateTime.now()`. Exemplo: `LocalDateTime.of(2026, 11, 1, 10, 0)`.

---

## 1. Recarga — `RecargaTest`

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-01 | `Recarga(valor, dataHora)` | valor = 20.0 | Cria; `consultarValorMovimentacao()` = 20.0 | [ ] |
| CT-02 | `Recarga(valor, dataHora)` | valor = 0 | Lança `ValorInvalidoException` | [ ] |
| CT-03 | `Recarga(valor, dataHora)` | valor = -10 | Lança `ValorInvalidoException` | [ ] |
| CT-04 | `Recarga(valor, dataHora)` | dataHora = `null` | Lança `IllegalArgumentException` | [ ] |
| CT-05 | `consultarTipoMovimentacao()` | Recarga válida | `CREDITO` | [ ] |

---

## 2. Veiculo e BicicletaMecanica — `BicicletaMecanicaTest`

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-06 | Construtor | Dois veículos criados em sequência | Códigos diferentes | [ ] |
| CT-07 | Construtor | Veículo novo | Estado `DISPONIVEL` | [ ] |
| CT-08 | `podeSerLiberado()` | Disponível | `true` | [ ] |
| CT-09 | `podeSerLiberado()` | Em manutenção | `false` | [ ] |
| CT-10 | `iniciarUtilizacao()` | Disponível | Estado `EM_USO`; `consultarEstacaoAtual()` = `null` | [ ] |
| CT-11 | `iniciarUtilizacao()` | Em manutenção | Lança `VeiculoIndisponivelException`; estado continua `MANUTENCAO` | [ ] |
| CT-12 | `iniciarUtilizacao()` | Já em uso | Lança `VeiculoIndisponivelException` | [ ] |
| CT-13 | `finalizarUtilizacao(destino, min)` | Em uso | Estado `DISPONIVEL`; estação atual = destino | [ ] |
| CT-14 | `enviarParaManutencao()` | Disponível | Estado `MANUTENCAO` | [ ] |
| CT-15 | `enviarParaManutencao()` | Em uso | Lança `OperacaoInvalidaException` | [ ] |
| CT-16 | `enviarParaManutencao()` | Já em manutenção | Não lança; continua `MANUTENCAO` | [ ] |
| CT-17 | `retornarDaManutencao()` | Em manutenção | Estado `DISPONIVEL` | [ ] |
| CT-18 | `retornarDaManutencao()` | Disponível | Lança `OperacaoInvalidaException` | [ ] |
| CT-19 | `recarregarBateria()` | Bicicleta mecânica | Lança `OperacaoInvalidaException` | [ ] |
| CT-20 | `aceitaDescontoUniversitario()` | Bicicleta mecânica | `true` | [ ] |
| CT-21 | `consultarTarifaDeDesbloqueio()` / `consultarTarifaPorMinuto()` | Bicicleta mecânica | 1.00 / 0.15 | [ ] |
| CT-22 | `consultarTipo()` | Bicicleta mecânica | `BICICLETA_MECANICA` | [ ] |
| CT-23 | `consultarDescricao()` | Modelo "Caloi" | Texto contém o tipo e "Caloi" | [ ] |

---

## 3. VeiculoEletrico, BicicletaEletrica e PatineteEletrico — `VeiculoEletricoTest`

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-24 | Construtor | bateria = -1 | Lança `ValorInvalidoException` | [ ] |
| CT-25 | Construtor | bateria = 101 | Lança `ValorInvalidoException` | [ ] |
| CT-26 | Construtor | bateria = 0 e 100 | Cria normalmente | [ ] |
| CT-27 | `podeSerLiberado()` | Disponível com 19% | `false` | [ ] |
| CT-28 | `podeSerLiberado()` | Disponível com 20% | `true` | [ ] |
| CT-29 | `iniciarUtilizacao()` | Disponível com 10% | Lança `VeiculoIndisponivelException` | [ ] |
| CT-30 | `finalizarUtilizacao(destino, 20)` | Patinete com 60% | Bateria 20%; estado `DISPONIVEL` | [ ] |
| CT-31 | `finalizarUtilizacao(destino, 25)` | Bicicleta elétrica com 80% | Bateria 55% | [ ] |
| CT-32 | `finalizarUtilizacao(destino, 100)` | Patinete com 30% (consumo maior que a carga) | Bateria 0% (nunca negativa) | [ ] |
| CT-33 | `recarregarBateria()` | Disponível com 40% | Bateria 100% | [ ] |
| CT-34 | `recarregarBateria()` | Em manutenção | Bateria 100% (é permitido) | [ ] |
| CT-35 | `recarregarBateria()` | Em uso | Lança `OperacaoInvalidaException`; bateria não muda | [ ] |
| CT-36 | `consultarConsumoPorMinuto()` | Bicicleta elétrica / patinete | 1 / 2 | [ ] |
| CT-37 | `aceitaDescontoUniversitario()` | Bicicleta elétrica / patinete | `true` / `false` | [ ] |
| CT-38 | Tarifas | Bicicleta elétrica / patinete | 2.00 e 0.30 / 3.00 e 0.40 | [ ] |
| CT-39 | `consultarTipo()` | Bicicleta elétrica / patinete | `BICICLETA_ELETRICA` / `PATINETE_ELETRICO` | [ ] |
| CT-40 | `consultarDescricao()` | Patinete com 75% | Texto contém tipo, modelo e "75" | [ ] |

---

## 4. Estacao — `EstacaoTest`

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-41 | `adicionarVeiculo(v)` | Estação com vaga | Quantidade estacionada +1; vagas −1 | [ ] |
| CT-42 | `adicionarVeiculo(v)` | Estação lotada (capacidade 2, já com 2) | Lança `EstacaoLotadaException`; quantidade continua 2 | [ ] |
| CT-43 | `adicionarVeiculo(null)` | — | Lança `IllegalArgumentException` | [ ] |
| CT-44 | `removerVeiculo(v)` | Veículo na estação | Quantidade −1 | [ ] |
| CT-45 | `removerVeiculo(v)` | Veículo que não está na estação | Lança `VeiculoNaoEncontradoException` | [ ] |
| CT-46 | `consultarVagasDisponiveis()` | Capacidade 3, 1 disponível + 1 em manutenção | 1 (manutenção ocupa vaga) | [ ] |
| CT-47 | `consultarQuantidadeVeiculosEstacionados()` | Mesmo cenário do CT-46 | 2 | [ ] |
| CT-48 | `consultarVeiculosDisponiveis()` | Mesmo cenário do CT-46 | Lista com 1 veículo (sem o de manutenção) | [ ] |
| CT-49 | `consultarVeiculosDisponiveis()` | Tentar `add` na lista devolvida | Lança `UnsupportedOperationException` | [ ] |

---

## 5. Planos — `PlanoAvulsoTest`, `PlanoMensalTest`, `PlanoUniversitarioTest`

Os planos **não arredondam**: use tolerância de 0.001.

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-50 | `PlanoAvulso.calcularValor` | **Carla**: bicicleta mecânica, 12 min | 2.80 | [ ] |
| CT-51 | `PlanoAvulso.calcularValor` | Patinete, 10 min | 7.00 | [ ] |
| CT-52 | `PlanoMensal.calcularValor` | **Bruno**: patinete, 20 min | 2.00 (só os 5 min excedentes, sem desbloqueio) | [ ] |
| CT-53 | `PlanoMensal.calcularValor` | **Eva**: bicicleta mecânica, 10 min | 0.00 | [ ] |
| CT-54 | `PlanoMensal.calcularValor` | Bicicleta mecânica, exatamente 15 min | 0.00 (limite) | [ ] |
| CT-55 | `PlanoMensal.calcularValor` | Bicicleta mecânica, 16 min | 0.15 | [ ] |
| CT-56 | `PlanoUniversitario.calcularValor` | **Ana**: bicicleta elétrica, 25 min | 4.75 (50% sobre o total, com desbloqueio) | [ ] |
| CT-57 | `PlanoUniversitario.calcularValor` | **Diego**: patinete, 10 min | 7.00 (sem desconto) | [ ] |
| CT-58 | `PlanoUniversitario.calcularValor` | Bicicleta mecânica, 12 min | 1.40 | [ ] |
| CT-59 | `consultarNome()` | Cada plano | Nomes diferentes e não vazios | [ ] |

---

## 6. Carteira — `CarteiraTest`

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-60 | `Carteira()` | Carteira nova | Saldo 0; histórico vazio | [ ] |
| CT-61 | `adicionarSaldo(recarga)` | Recarga de 20 | Saldo 20; histórico com 1 item | [ ] |
| CT-62 | `temSaldoMinimo()` | Saldo 4.99 | `false` | [ ] |
| CT-63 | `temSaldoMinimo()` | Saldo 5.00 | `true` (limite) | [ ] |
| CT-64 | `debitarValor(viagem)` | Saldo 10, viagem encerrada de 2.80 | Saldo 7.20 | [ ] |
| CT-65 | `debitarValor(viagem)` | Saldo 5, viagem de 7.00 | Saldo −2.00 (pode ficar negativo) | [ ] |
| CT-66 | `consultarHistoricoMovimentacoes()` | Tentar `add` na lista devolvida | Lança `UnsupportedOperationException` | [ ] |
| CT-67 | `consultarExtrato()` | Recarga registrada depois, mas com data anterior à da viagem | A recarga aparece primeiro | [ ] |
| CT-68 | `consultarExtrato()` | Duas movimentações com a mesma data | Ordem de registro preservada | [ ] |
| CT-69 | `consultarExtrato()` | Recarga 20 e viagem 2.80 | Saldos das linhas: 20.00 e 17.20 | [ ] |
| CT-70 | `consultarExtrato()` | Qualquer histórico | Saldo da última linha = `consultarSaldoAtual()` | [ ] |
| CT-71 | `consultarExtrato()` | Depois de gerar o extrato | Ordem de `consultarHistoricoMovimentacoes()` não mudou | [ ] |

---

## 7. Usuario — `UsuarioTest`

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-72 | `recarregarCarteira(valor, data)` | valor = 30 | Saldo 30 | [ ] |
| CT-73 | `recarregarCarteira(valor, data)` | valor = 0 | Lança `ValorInvalidoException`; saldo não muda | [ ] |
| CT-74 | `temSaldoMinimo()` | Recarga de 5 | `true` | [ ] |
| CT-75 | `registrarViagemAtiva(v)` | Sem viagem ativa | `possuiViagemEmAndamento()` = `true` | [ ] |
| CT-76 | `registrarViagemAtiva(v)` | Já com viagem ativa | Lança `ViagemEmAndamentoException`; a viagem ativa continua a primeira | [ ] |
| CT-77 | `finalizarViagemAtiva(v)` | `v` é a viagem ativa | `possuiViagemEmAndamento()` = `false` | [ ] |
| CT-78 | `finalizarViagemAtiva(v)` | Sem viagem ativa | Lança `SemViagemEmAndamentoException` | [ ] |
| CT-79 | `finalizarViagemAtiva(outra)` | Viagem diferente da ativa | Lança `SemViagemEmAndamentoException`; a ativa não muda | [ ] |
| CT-80 | `pagarViagem(v)` | Saldo 20, viagem encerrada de 2.80 | Saldo 17.20 | [ ] |
| CT-81 | `consultarExtrato()` | Uma recarga e uma viagem | 2 linhas, igual ao extrato da carteira | [ ] |

---

## 8. Viagem — `ViagemTest`

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-82 | Construtor | Viagem nova | `estaEmAndamento()` = `true`; destino e fim `null` | [ ] |
| CT-83 | `finalizarViagem(destino, fim)` | Fim 12 min depois do início | Em andamento = `false`; destino e fim gravados | [ ] |
| CT-84 | `finalizarViagem(destino, fim)` | Fim **igual** ao início | Lança `DataInvalidaException`; continua em andamento | [ ] |
| CT-85 | `finalizarViagem(destino, fim)` | Fim **antes** do início | Lança `DataInvalidaException`; continua em andamento | [ ] |
| CT-86 | `finalizarViagem(destino, fim)` | Viagem já encerrada | Lança `SemViagemEmAndamentoException` | [ ] |
| CT-87 | `calcularDuracao()` | 10:00 → 10:12 | 12 | [ ] |
| CT-88 | `calcularDuracao()` | 10:00:00 → 10:12:30 | 13 (arredonda para cima) | [ ] |
| CT-89 | `calcularDuracao()` | 10:00:00 → 10:00:20 | 1 (nunca zero) | [ ] |
| CT-90 | `consultarValorCobrado()` | **Os 5 exemplos do enunciado** (Carla, Ana, Bruno, Diego, Eva) | 2.80 / 4.75 / 2.00 / 7.00 / 0.00 | [ ] |
| CT-91 | `consultarTipoMovimentacao()` | Viagem | `DEBITO` | [ ] |
| CT-92 | `consultarDataHoraMovimentacao()` | Viagem encerrada | Igual a `dataHoraFim` | [ ] |

> Dica: o CT-90 fica mais limpo com `@ParameterizedTest` e `@CsvSource`, uma linha por exemplo.

---

## 9. RelatorioFaturamento — `RelatorioFaturamentoTest`

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-93 | `consultarQuantidadeTotalViagens()` | Mapa: mecânica 2, patinete 1 | 3 | [ ] |
| CT-94 | `consultarFaturamentoTotal()` | Mapa: mecânica 5.60, patinete 7.00 | 12.60 | [ ] |
| CT-95 | `consultarQuantidadeViagens(tipo)` / `consultarFaturamento(tipo)` | Tipo ausente do mapa | 0 / 0.0 | [ ] |
| CT-96 | Construtor | Alterar o mapa original depois de criar o relatório | Relatório não muda (cópia defensiva) | [ ] |

---

## 10. Sistema — `SistemaTest`

### 10.1 Cadastros e consultas

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-97 | `Sistema()` | Sistema novo | Listas vazias; `consultarPlanos()` com 3 planos | [ ] |
| CT-98 | `cadastrarEstacao(e)` | Nome novo | `consultarEstacoes()` com 1 | [ ] |
| CT-99 | `cadastrarEstacao(e)` | " centro " quando já existe "Centro" | Lança `RegistroJaExistenteException`; continua 1 | [ ] |
| CT-100 | `cadastrarUsuario(u)` | E-mail novo | `consultarUsuarios()` com 1 | [ ] |
| CT-101 | `cadastrarUsuario(u)` | " ANA@x.com " quando já existe "ana@x.com" | Lança `RegistroJaExistenteException` | [ ] |
| CT-102 | `cadastrarUsuario(u)` | Nome igual, e-mail diferente | Cadastra (o nome pode repetir) | [ ] |
| CT-103 | `cadastrarVeiculo(v)` | Estação com vaga | Veículo em `consultarVeiculos()` e na estação | [ ] |
| CT-104 | `cadastrarVeiculo(v)` | Estação lotada | Lança `EstacaoLotadaException`; veículo **não** entra em `consultarVeiculos()` | [ ] |
| CT-105 | `consultarEstacao(nome)` | " furb " com "FURB" cadastrada | Devolve a estação FURB | [ ] |
| CT-106 | `consultarEstacao(nome)` | Nome inexistente | Lança `RegistroNaoEncontradoException` | [ ] |
| CT-107 | `consultarUsuario(email)` | E-mail em maiúsculas | Devolve o usuário | [ ] |
| CT-108 | `consultarUsuario(email)` | E-mail inexistente | Lança `RegistroNaoEncontradoException` | [ ] |
| CT-109 | `consultarVeiculo(codigo)` | Código de um veículo cadastrado | Devolve o veículo | [ ] |
| CT-110 | `consultarVeiculo(codigo)` | Código inexistente | Lança `RegistroNaoEncontradoException` | [ ] |
| CT-111 | `consultarViagensDoUsuario(u)` | Ana com 2 viagens, Bruno com 1 | Lista da Ana com 2 | [ ] |
| CT-112 | `consultarEstacoes()` e demais listas | Tentar `add` na lista devolvida | Lança `UnsupportedOperationException` | [ ] |

### 10.2 Iniciar viagem

Em todos os casos de exceção, confira que **nada mudou**: o veículo continua `DISPONIVEL` e na estação, e o usuário continua sem viagem ativa.

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-113 | `iniciarViagem(u, v, inicio)` | Saldo 20, veículo disponível | Devolve a viagem; veículo `EM_USO` e fora da estação; usuário com viagem ativa; viagem em `consultarViagens()` | [ ] |
| CT-114 | `iniciarViagem` | Usuário já com viagem ativa | Lança `ViagemEmAndamentoException`; nada mudou | [ ] |
| CT-115 | `iniciarViagem` | Saldo 4.99 | Lança `SaldoInsuficienteException`; nada mudou | [ ] |
| CT-116 | `iniciarViagem` | Veículo em manutenção | Lança `VeiculoIndisponivelException`; nada mudou | [ ] |
| CT-117 | `iniciarViagem` | Patinete com 19% | Lança `VeiculoIndisponivelException`; nada mudou | [ ] |
| CT-118 | `iniciarViagem` | Saldo negativo depois de uma viagem cara | Lança `SaldoInsuficienteException` | [ ] |

### 10.3 Finalizar viagem

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-119 | `finalizarViagem(viagem, destino, fim)` | **Os 5 exemplos do enunciado, de ponta a ponta** | Valores 2.80 / 4.75 / 2.00 / 7.00 / 0.00; baterias 55% / 20% / 80% | [ ] |
| CT-120 | `finalizarViagem` | Caso normal | Veículo `DISPONIVEL` na estação de destino; saldo debitado; usuário sem viagem ativa | [ ] |
| CT-121 | `finalizarViagem` | Viagem já encerrada | Lança `SemViagemEmAndamentoException` | [ ] |
| CT-122 | `finalizarViagem` | Estação de destino lotada | Lança `EstacaoLotadaException`; viagem **continua em andamento**; veículo continua `EM_USO`; saldo não muda | [ ] |
| CT-123 | `finalizarViagem` | Fim antes do início | Lança `DataInvalidaException`; veículo continua `EM_USO` e fora da estação de destino | [ ] |
| CT-124 | `finalizarViagem` | Saldo 5, viagem de 7.00 | Encerra; saldo −2.00 | [ ] |

### 10.4 Faturamento

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-125 | `consultarFaturamento(ini, fim)` | Data final antes da inicial | Lança `DataInvalidaException` | [ ] |
| CT-126 | `consultarFaturamento` | Viagens encerradas em 01/11 e 30/11; período 01/11 a 30/11 | As duas entram (período inclusivo) | [ ] |
| CT-127 | `consultarFaturamento` | Viagem encerrada em 01/12; período de novembro | Não entra | [ ] |
| CT-128 | `consultarFaturamento` | Uma viagem em andamento | Não entra | [ ] |
| CT-129 | `consultarFaturamento` | 2 mecânicas (2.80 cada) e 1 patinete (7.00) | Mecânica: 2 viagens, 5.60; patinete: 1, 7.00; total 12.60 | [ ] |
| CT-130 | `consultarFaturamento` | Período sem viagens | Total 0 viagens e 0.00 | [ ] |

### 10.5 Persistência (quando `salvarDados`/`carregarDados` estiverem prontos)

Use uma pasta temporária (`@TempDir`) para não apagar os dados de exemplo da `dados-gb26`.

| ID | Método | Cenário / entrada | Resultado esperado | ✓ |
|---|---|---|---|---|
| CT-131 | `carregarDados()` | Pasta sem arquivos (primeira execução) | Não lança; sistema vazio com os 3 planos | [ ] |
| CT-132 | `salvarDados()` + `carregarDados()` | Estações, veículos, usuários e viagens encerradas | Tudo volta igual em um `Sistema` novo | [ ] |
| CT-133 | `salvarDados()` + `carregarDados()` | Uma viagem **em andamento** | Volta em andamento; o usuário continua com ela ativa; dá para finalizar | [ ] |
| CT-134 | `carregarDados()` + cadastro | Cadastrar um veículo novo depois de carregar | Código diferente de todos os carregados (contador restaurado) | [ ] |
| CT-135 | `carregarDados()` | Arquivo corrompido | Lança `PersistenciaException` | [ ] |

---

## Exemplo de formato

Modelo para os casos de exceção com "nada mudou" (CT-115):

```java
@Test
void iniciarViagemComSaldoInsuficienteNaoAlteraNada() throws Exception {
    // cenário
    Sistema sistema = new Sistema();
    Estacao centro = new Estacao("Centro", 5);
    sistema.cadastrarEstacao(centro);
    Veiculo bike = new BicicletaMecanica("Caloi", centro);
    sistema.cadastrarVeiculo(bike);
    Usuario carla = new Usuario("Carla", "carla@x.com", new PlanoAvulso());
    sistema.cadastrarUsuario(carla);
    carla.recarregarCarteira(4.99, LocalDateTime.of(2026, 11, 1, 9, 0));

    // ação + verificação da exceção
    assertThrows(SaldoInsuficienteException.class,
            () -> sistema.iniciarViagem(carla, bike, LocalDateTime.of(2026, 11, 1, 10, 0)));

    // nada mudou
    assertEquals(EstadoVeiculo.DISPONIVEL, bike.consultarEstadoAtual());
    assertEquals(1, centro.consultarQuantidadeVeiculosEstacionados());
    assertFalse(carla.possuiViagemEmAndamento());
    assertTrue(sistema.consultarViagens().isEmpty());
}
```

---

## Resumo

| Classe | Casos |
|---|---|
| Recarga | CT-01 a CT-05 |
| Veiculo / BicicletaMecanica | CT-06 a CT-23 |
| VeiculoEletrico / BicicletaEletrica / PatineteEletrico | CT-24 a CT-40 |
| Estacao | CT-41 a CT-49 |
| Planos | CT-50 a CT-59 |
| Carteira | CT-60 a CT-71 |
| Usuario | CT-72 a CT-81 |
| Viagem | CT-82 a CT-92 |
| RelatorioFaturamento | CT-93 a CT-96 |
| Sistema | CT-97 a CT-135 |
| **Total** | **135 casos** |

**Para a apresentação:** o enunciado pede uma amostra do plano, um ou dois métodos JUnit e o resultado da execução. Os melhores para mostrar são o **CT-119** (os 5 exemplos do enunciado, de ponta a ponta) e o **CT-122** (estação lotada não encerra a viagem). Eles mostram a regra de negócio e o cuidado de não alterar nada quando ocorre uma exceção.
