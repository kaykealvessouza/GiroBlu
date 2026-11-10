# GIROBLU

## 1. Rascunho Inicial das Classes

Classes iniciais que foram pensadas para o sistema, organizadas de acordo com suas possíveis hierarquias.
Criado a prévia dia 4/11.

## 1.1. Sistema
- Sistema

## 1.2. Estação
- Estacao

## 1.3. Veículos
- **Veiculo (abstrata)**
  - **VeiculoMecanico (abstrata)**
    - BicicletaMecanica
  - **VeiculoEletrico (abstrata)**
    - BicicletaEletrica
    - PatineteEletrico

## 1.4. Usuários
- Usuario

## 1.5. Planos
- **Plano (abstrata)**
  - PlanoAvulso
  - PlanoMensal
  - PlanoUniversitario

## 1.6. Carteira
- Carteira

## 1.7. Viagens
- Viagem

---

## 2. Observações de modelagem

Espaço destinado a registrar decisões, dúvidas e possíveis alterações na estrutura das classes durante o desenvolvimento.
Contém análise prévia das classes inicialmente identificadas, considerando seus possíveis atributos, métodos, responsabilidades e relacionamentos.

As informações desta seção são propostas iniciais e poderão ser modificadas conforme o desenvolvimento da modelagem.

## 2.1. Sistema

**Objetivo:** centralizar o gerenciamento das informações e operações do sistema.

**Possíveis atributos:**
- Coleção de estações cadastradas.
- Coleção de veículos cadastrados.
- Coleção de usuários cadastrados.
- Coleção de viagens registradas (a definir).

**Possíveis métodos:**
- Cadastrar estação.
- Cadastrar veículo.
- Cadastrar usuário.
- Consultar faturamento por período.
- Consultar informações do sistema.

**Observações:**
- Avaliar se todas as coleções precisam existir diretamente em `Sistema` ou se algumas informações podem ser obtidas por meio de outras classes.
- Avaliar se o histórico de viagens ficará armazenado diretamente em `Sistema`.
- O relatório de faturamento deverá considerar as viagens encerradas dentro do período informado, agrupando os resultados por tipo de veículo.

**Atualização 06/11 - Definindo atributos e métodos**

**UML CRIADO? --> [ X ]**

**Atributos**
- private ArrayList<Estacao> estacoes;
- private ArrayList<Veiculo> veiculos;
- private ArrayList<Usuario> usuarios;
- private ArrayList<Viagem> viagens;

**Construtor**
- public Sistema();

**Métodos**
- public void cadastrarEstacao(Estacao estacao);
- public void cadastrarVeiculo(Veiculo veiculo);
- public void cadastrarUsuario(Usuario usuario);

- public RelatorioFaturamento consultarFaturamento(LocalDate dataInicial,LocalDate dataFinal); 

- public Estacao consultarEstacao(String nome);
- public List<Estacao> consultarEstacoes();

- public Veiculo consultarVeiculo(int codigo);
- public List<Veiculo> consultarVeiculos();

- public Usuario consultarUsuario(String email);
- public List<Usuario> consultarUsuarios();

- public Viagem consultarViagensDoUsuario(Usuario usuario);
- public List<Viagem> consultarViagens();

## 2.2. Estacao

**Objetivo:** representar os pontos de estacionamento e disponibilização dos veículos.

**Possíveis atributos:**
- Nome.
- Capacidade máxima.
- Veículos estacionados.

**Possíveis métodos:**
- Adicionar veículo.
- Remover veículo.
- Verificar disponibilidade de vagas.
- Consultar veículos disponíveis.
- Consultar quantidade de veículos estacionados.

**Observações:**
- Veículos em manutenção continuam ocupando vagas quando estão estacionados.
- Veículos em uso não ocupam vagas na estação de origem.
- Uma estação não pode receber mais veículos do que sua capacidade permite.

**Atualização 07/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**
- private String nome;
- private int capacidadeMaxima;
- private ArrayList<Veiculo> veiculosEstacionados;

**Construtor**
- public Estacao(String nome, int capacidadeMaxima);

**Métodos**
- public void adicionarVeiculo(Veiculo veiculo);
- public void removerVeiculo(Veiculo veiculo);
- public int consultarVagasDisponíveis();
- public ArrayList<Veiculo> consultarVeiculosDisponiveis();
- public int consultarQuantidadeVeiculosEstacionados();

## 2.3. Veiculo (abstrata)

**Objetivo:** representar as características e os comportamentos comuns a todos os veículos.

**Possíveis atributos:**
- private static int contadorCodigo = 0;
- private final int codigoIdentificador;
- Modelo.
- Estado atual (disponível, em uso ou em manutenção).
- Estação atual.

**Possíveis métodos:**
- Iniciar utilização.
- Finalizar utilização.
- Enviar para manutenção.
- Retornar da manutenção.
- Consultar disponibilidade.
- Consultar tarifa de desbloqueio.
- Consultar tarifa por minuto.

**Observações:**
- A classe deverá concentrar as regras comuns a todos os tipos de veículo.
- Métodos específicos de cada categoria poderão ser definidos nas subclasses.
- Avaliar quais comportamentos devem ser abstratos e implementados pelas subclasses.
- Estado do Veiculo será um enum com os valores DISPONIVEL, EM_USO e MANUTENCAO.

**Atualização 07/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**
- private static int contadorCodigo;
- private final int codigoIdentificador
- private String modelo;
- private private EstadoVeiculo estadoAtual;
- private Estacao estacaoAtual;

**Construtor**
- protected Veiculo(String modelo, Estacao estacaoAtual)

**Métodos**
- public void iniciarUtilizacao();
- public void finalizarUtilizacao();
- public void enviarParaManutencao();
- public void retornarDaManutencao();
- public EstadoVeiculo consultarEstadoAtual();
- public String consultarEstacaoAtual();
- public String consultarModelo();
- public int consultarCodigo();
- public abstract double consultarTarifaDeDesbloqueio();
- public abstract double consultarTarifaPorMinuto();
- public abstract TipoVeiculo consultarTipo();

## 2.4. VeiculoMecanico (abstrata) 

**Removido**

**Objetivo Anterior:** representar uma categoria de veículos que não dependem de bateria.

**Motivo da Remoção:** seria apenas para seguir a lógica de VeiculoEletrico, porém como não teria nenhum método ou atributo próprio, é mais pertinente remover.

### 2.4.1. BicicletaMecanica

**Objetivo:** representar as bicicletas mecânicas disponíveis no sistema.

**Possíveis atributos:**
- Atributos comuns herdados de `Veiculo`.

**Possíveis métodos:**
- Implementar comportamentos específicos exigidos pela classe abstrata `Veiculo`, quando necessário.
- Definir suas tarifas específicas.

**Atualização 07/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**
- Herdados da classe abstrata pai (Veiculo)

**Construtor**
- public BicletaMecanica(String modelo, Estacao estacaoAtual);

**Métodos**
@Override 
- public double consultarTarifaDeDesbloqueio();
@Override
- public double consultarTarifaPorMinuto();
@Override
- public TipoVeiculo consultarTipo();

## 2.5. VeiculoEletrico (abstrata)

**Objetivo:** representar características e comportamentos compartilhados pelos veículos elétricos.

**Possíveis atributos:**
- Nível de bateria (%).

**Possíveis métodos:**
- Recarregar bateria.
- Verificar carga mínima para iniciar viagem.
- Consultar nível de bateria.
- Calcular ou controlar consumo de bateria.
- Definir consumo por minuto.

**Observações:**
- A bateria deve iniciar com um valor entre 0% e 100%.
- A viagem somente poderá ser iniciada com pelo menos 20% de bateria.
- A bateria nunca poderá ficar abaixo de 0%.
- A recarga deverá levar a bateria até 100%, respeitando a regra de que o veículo não pode estar em uso.
- O consumo por minuto poderá variar de acordo com o tipo de veículo elétrico.

**Atualização 07/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**
- Herdados da classe abstrata pai (Veiculo)
- private int nivelBateria;

**Construtor**
- protected veiculoEletrico(String modelo, Estacao estacaoAtual, int nivelBateria);

**Métodos**
- public void recarregarBateria();
- public boolean possuiCargaMinimaParaViagem();
- public int consultarNivelBateria();
- public abstract int consultarNivelBateria();
- public abstract void consumirBateria(int minutos);
- public abstract int consultarConsumoPorMinuto();
- public abstract boolean aceitaDescontoUniversitario() 

### 2.5.1. BicicletaEletrica

**Objetivo:** representar as bicicletas elétricas.

**Possíveis atributos:**
- Atributos comuns herdados de `VeiculoEletrico`.

**Possíveis métodos:**
- Definir consumo de bateria por minuto: 1%.
- Definir tarifas específicas.

**Atualização 07/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**

**Construtor**
- public BicicletaEletrica(modelo: String, estacaoAtual: Estacao, nivelBateria: int)

**Métodos**
- public double consultarTarifaDeDesbloqueio()
- public double consultarTarifaPorMinuto()
- public TipoVeiculo consultarTipo()
@Override
- public int consultarConsumoPorMinuto()
@Override
- public void finalizarUtilizacao(minutos: int)
@Override
- public void consumirBateria(minutos: int)
@Override
- public boolean aceitaDescontoUniversitario() 

### 2.5.2. PatineteEletrico

**Objetivo:** representar os patinetes elétricos.

**Possíveis atributos:**
- Atributos comuns herdados de `VeiculoEletrico`.

**Possíveis métodos:**
- Definir consumo de bateria por minuto: 2%.
- Definir tarifas específicas.

**Atualização 07/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**

**Construtor**
- public PatineteEletrico(modelo: String, estacaoAtual: Estacao, nivelBateria: int)

**Métodos**
- public double consultarTarifaDeDesbloqueio()
- public double consultarTarifaPorMinuto()
- public TipoVeiculo consultarTipo() 
@Override
- public boolean aceitaDescontoUniversitario() 
@Override
- public int consultarConsumoPorMinuto()
@Override
- public void finalizarUtilizacao(minutos: int)
@Override
- public void consumirBateria(minutos: int) 


## 2.6. Usuario

**Objetivo:** representar os usuários cadastrados no sistema.

**Possíveis atributos:**
- Nome.
- E-mail.
- Plano contratado.
- Carteira.
- Viagem ativa (avaliar necessidade).

**Possíveis métodos:**
- Consultar saldo.
- Consultar plano.
- Consultar viagem ativa.
- Consultar extrato.

**Observações:**
- Um usuário não pode possuir mais de uma viagem ativa simultaneamente.
- O usuário precisa possuir pelo menos R$ 5,00 de saldo para iniciar uma viagem.
- Caso o saldo fique negativo após o encerramento de uma viagem, será necessário realizar uma recarga suficiente antes de iniciar outra.

**Atualização 08/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**

- private String nome;
- private String email;
- private Viagem viagemAtiva;
- private Plano planoContratado;
- private Carteira carteira;

**Construtor**

- public Usuario(String nome, String email, Plano planoContratado) {
    this.nome = nome;
    this.email = email;
    this.planoContratado = planoContratado;
    this.carteira = new Carteira();   // ← o usuário cria a própria carteira, zerada
}

**Métodos**

- public String consultarNome()
- public String consultarEmail()
- public Plano consultarPlanoContratado()
- public Viagem consultarViagemAtiva()

- public boolean possuiViagemEmAndamento() {return viagemAtiva != null;}   // tem objeto? então tem viagem
- public boolean temSaldoMinimo() {return carteira.temSaldoMinimo();}     // repassa a pergunta pra carteira

- public void registrarViagemAtiva(Viagem viagem) throws ViagemEmAndamentoException {
    if (possuiViagemEmAndamento()) {
        throw new ViagemEmAndamentoException("Usuário já possui uma viagem em andamento.");
    }
    this.viagemAtiva = viagem;     // ← o trabalho de verdade do método
}

- public void finalizarViagemAtiva() throws SemViagemEmAndamentoException {
    if (!possuiViagemEmAndamento()) {
        throw new SemViagemEmAndamentoException("Usuário não possui viagem em andamento.");
    }
    this.viagemAtiva = null;     // ← o usuário deixa de ter viagem ativa
}

- public void recarregarCarteira(double valor, LocalDateTime dataHora) throws ValorInvalidoException {carteira.adicionarSaldo(valor, dataHora);}

- public void pagarViagem(double valor) {carteira.debitarValor(valor);}

- public double consultarSaldo() {return carteira.getSaldoAtual();}

- public List<Recarga> consultarRecargas() {return carteira.getHistoricoRecarga();}


## 2.7. Plano (Interface)

**Objetivo:** definir as regras de cobrança aplicadas aos usuários.

**Possíveis atributos:**
- Ainda não foram identificados atributos comuns obrigatórios.

**Possíveis métodos:**
- Calcular valor da viagem.
- Aplicar regras específicas de cobrança.

**Observações:**
- Cada modalidade deverá implementar suas próprias regras de cobrança.
- A estrutura deve permitir adicionar novos planos sem modificar as regras das modalidades existentes.

**Atualização 08/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**

**Construtor**

**Métodos**
- public double calcularValor(veiculo: Veiculo, minutos: int)
- public String cosultarNome()

### 2.7.1. PlanoAvulso

**Regra:** cobrança integral da tarifa do veículo, incluindo desbloqueio e valor por minuto.

**Atualização 08/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**

**Construtor**

**Métodos**
@Override
- public double calcularValor(veiculo: Veiculo, minutos: int)
@Override
- public String cosultarNome()

### 2.7.2. PlanoMensal

**Regra:** não cobra desbloqueio e oferece os primeiros 15 minutos gratuitos de cada viagem. A cobrança considera somente os minutos excedentes.

**Atualização 08/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**

**Construtor**

**Métodos**
@Override
- public double calcularValor(veiculo: Veiculo, minutos: int)
@Override
- public String cosultarNome()

### 2.7.3. PlanoUniversitario

**Regra:** oferece 50% de desconto sobre o valor total da viagem, exceto para patinetes elétricos, que mantêm a tarifa integral.

**Atualização 08/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**

**Construtor**

**Métodos**
@Override
- public double calcularValor(veiculo: Veiculo, minutos: int)
@Override
- public String cosultarNome()

## 2.8. Carteira

**Objetivo:** controlar o saldo e as movimentações financeiras do usuário.

**Possíveis atributos:**
- Saldo atual.
- Histórico de recargas (avaliar necessidade de uma classe específica para representar cada recarga).

**Possíveis métodos:**
- Adicionar saldo.
- Debitar valor de uma viagem.
- Consultar saldo.
- Consultar histórico de movimentações.
- Gerar extrato.

**Observações:**
- A carteira começa com saldo zero.
- Recargas devem possuir data, horário e valor.
- O valor de uma recarga deve ser maior que zero.
- O saldo pode ficar negativo após o encerramento de uma viagem.
- O extrato deve apresentar recargas e viagens em ordem cronológica, com o saldo após cada movimentação.

**Atualização 07/11 - Definindo atributos e métodos**

**UML CRIADO? --> [x]**

**Atributos**
- private double saldoAtual
- private ArrayList<Recarga> historicoRecarga 
- private static final double SALDO_MINIMO = 5.0

**Construtor**
- public Carteira()

**Métodos**
- public double consultarSaldoAtual()
- public List<Recarga> consultarHistoricoRecarga()
- public void adicionarSaldo(valor: double, dataHora: LocalDateTime)
- public void debitarValor(valor: double)
- public boolean temSaldoMinimo()

## 2.9. Viagem

**Objetivo:** representar a utilização de um veículo por determinado usuário.

**Possíveis atributos:**
- Usuário.
- Veículo utilizado.
- Estação de origem.
- Estação de destino.
- Data e horário de início.
- Data e horário de encerramento.
- Duração.
- Valor cobrado.

**Possíveis métodos:**
- Registrar início da viagem.
- Registrar encerramento da viagem.
- Calcular duração.
- Calcular valor da viagem.
- Consultar situação da viagem.

**Observações:**
- A viagem deve possuir data e horário de início e encerramento.
- O encerramento deve ocorrer depois do início.
- A viagem precisa terminar em uma estação com vaga disponível.
- O valor final deve ser arredondado para duas casas decimais.
- O encerramento deve atualizar a localização e a disponibilidade do veículo, além de aplicar o consumo de bateria quando necessário.

**Atualização XX/11 - Definindo atributos e métodos**

**UML CRIADO? --> [  ]**

**Atributos**

**Construtor**

**Métodos**

## 2.10. RelatorioFaturamento

**Objetivo:** representar os resultados de uma consulta de faturamento, armazenando a quantidade de viagens encerradas e os valores arrecadados por tipo de veículo e no total.

Ex de saída:

Tipo de veículo |	Viagens |	Faturamento
----------------|---------|-----------
Bicicleta mecânica |	12 |	R$ 45,00
Bicicleta elétrica |	8 |	R$ 62,50
Patinete elétrico |	15 |	R$ 90,00
Total |	35 |	R$ 197,50

**Possíveis atributos:**
- Período inicial e final do relatório.
- Quantidade de viagens por tipo de veículo.
- Faturamento por tipo de veículo.
- Quantidade total de viagens.
- Faturamento total.

**Possíveis métodos:**
- Consultar quantidade de viagens por tipo de veículo.
- Consultar faturamento por tipo de veículo.
- Consultar quantidade total de viagens.
- Consultar faturamento total.

**Observações:**
- O relatório deverá considerar somente viagens encerradas dentro do período informado.
- Os resultados deverão ser agrupados por tipo de veículo.
- A classe armazenará os resultados calculados, sem responsabilidade pela apresentação visual do relatório.
- A geração do relatório será coordenada por `Sistema`, que consultará o histórico de viagens e retornará um objeto `RelatorioFaturamento`.

**Atualização XX/11 - Definindo atributos e métodos**

**UML CRIADO? --> [  ]**

**Atributos**

**Construtor**

**Métodos**

## 2.11 Recarga

**Objetivo:** armazenar o histórico de recargas da carteira de um certo usuário.

**Atualização XX/11 - Definindo atributos e métodos**

**UML CRIADO? --> [ x ]**

**Atributos**
- private double valor 
- private LocalDateTime dataHora

**Construtor**
- public Recarga(valor: int, dataHora: LocalDateTime)
	{throws ValorInvalidoException} 

**Métodos**
- public double consultarValor()
- public LocalDateTime consultarDataHora() 

---

# 3. Interfaces

## 3.1. Recarregavel

Interface inicialmente proposta para representar o comportamento de recarga de bateria.

**Possível método:**
- Recarregar bateria.

**Aplicação inicial:**
- Relacionada aos veículos elétricos.
- `VeiculoEletrico` poderá implementar essa interface, concentrando o comportamento comum de recarga.

**Observações:**
- A interface ainda não está definida como decisão final.
- O trabalho exige pelo menos uma interface própria implementada por mais de uma classe.
- Ainda precisamos encontrar uma aplicação coerente para atender a esse requisito, sem criar responsabilidades artificiais.

---

# 4. Pendências gerais

- [ ] Definir os atributos e métodos definitivos de cada classe.
- [ ] Definir os relacionamentos entre as classes.
- [ ] Definir a aplicação da interface `Recarregavel`.
- [ ] Encontrar uma segunda classe que possa implementar uma interface de maneira coerente.
- [ ] Avaliar a necessidade de uma classe específica para representar as recargas.
- [ ] Definir como será armazenado o histórico de viagens.
- [ ] Definir onde ficará a lógica de cálculo do faturamento.
- [ ] Avaliar a necessidade de uma classe específica para representar o relatório de faturamento.
- [ ] Definir como será realizada a persistência dos dados.

---

# 5. Observações gerais

- A estrutura deve permitir adicionar novos tipos de veículos e planos com o mínimo de alterações nas classes existentes.
- As regras de negócio devem ficar na camada de negócio, sem dependência da interface gráfica.
- Os atributos devem ser encapsulados e suas alterações devem respeitar as regras do sistema.
- O polimorfismo deve ser utilizado para evitar verificações excessivas de tipos com `if` ou `instanceof`.
- Classes abstratas devem concentrar características e comportamentos compartilhados por suas subclasses.
- Interfaces devem representar contratos de comportamento coerentes, evitando sua criação apenas para cumprir requisitos acadêmicos.