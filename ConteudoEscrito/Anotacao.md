# GIROBLU

## 1. Rascunho Inicial das Classes

Classes inicialmente pensadas para o sistema, organizadas de acordo com suas possíveis hierarquias.

Criada a prévia em 04/11.

### 1.1. Sistema
- Sistema

### 1.2. Estação
- Estacao

### 1.3. Veículos
- **Veiculo (abstrata)**
  - **VeiculoMecanico (abstrata)**
    - BicicletaMecanica
  - **VeiculoEletrico (abstrata)**
    - BicicletaEletrica
    - PatineteEletrico

### 1.4. Usuários
- Usuario

### 1.5. Planos
- **Plano (abstrata)**
  - PlanoAvulso
  - PlanoMensal
  - PlanoUniversitario

### 1.6. Carteira
- Carteira

### 1.7. Viagens
- Viagem

---

## 2. Observações de modelagem

Espaço destinado a registrar decisões, dúvidas e possíveis alterações na estrutura das classes durante o desenvolvimento.

Contém análises das classes inicialmente identificadas, considerando seus possíveis atributos, métodos, responsabilidades e relacionamentos.

As informações desta seção representam propostas de modelagem e decisões tomadas durante o desenvolvimento. Alterações devem ser registradas para manter o histórico da evolução do projeto.

### 2.1. Sistema

**Objetivo:** centralizar o gerenciamento das informações e operações do sistema.

**Possíveis atributos:**
- Coleção de estações cadastradas.
- Coleção de veículos cadastrados.
- Coleção de usuários cadastrados.
- Coleção de viagens registradas.

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

**Atualização 06/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- `private ArrayList<Estacao> estacoes;`
- `private ArrayList<Veiculo> veiculos;`
- `private ArrayList<Usuario> usuarios;`
- `private ArrayList<Viagem> viagens;`

**Construtor**
- `public Sistema();`

**Métodos**
- `public void cadastrarEstacao(Estacao estacao);`
- `public void cadastrarVeiculo(Veiculo veiculo);`
- `public void cadastrarUsuario(Usuario usuario);`
- `public RelatorioFaturamento consultarFaturamento(LocalDate dataInicial, LocalDate dataFinal);`
- `public Estacao consultarEstacao(String nome);`
- `public List<Estacao> consultarEstacoes();`
- `public Veiculo consultarVeiculo(int codigo);`
- `public List<Veiculo> consultarVeiculos();`
- `public Usuario consultarUsuario(String email);`
- `public List<Usuario> consultarUsuarios();`
- `public List<Viagem> consultarViagensDoUsuario(Usuario usuario);`
- `public List<Viagem> consultarViagens();`
- `public Viagem iniciarViagem(Usuario usuario, Veiculo veiculo, LocalDateTime dataHoraInicio);`
- `public void finalizarViagem(Viagem viagem, Estacao estacaoDestino, LocalDateTime dataHoraFim);`

**Observações da definição:**
- As coleções são mantidas internamente em `ArrayList`, mas os métodos de consulta utilizam `List` como tipo de retorno, reduzindo o acoplamento à implementação concreta.
- `Sistema` ficará responsável por coordenar operações que envolvem diferentes entidades, sem concentrar regras específicas de veículos, estações, planos ou carteiras.

**`iniciarViagem(Usuario usuario, Veiculo veiculo, LocalDateTime dataHoraInicio)`**
- Responsável por coordenar o início de uma viagem, realizando as validações necessárias antes de efetivá-la.
- Verifica se o usuário não possui outra viagem em andamento e se possui o saldo mínimo exigido de R$ 5,00.
- Verifica se o veículo está disponível e, caso seja elétrico, se possui a carga mínima de 20%.
- Obtém a estação de origem a partir da localização atual do veículo.
- Cria a `Viagem`, registra-a como viagem ativa do usuário e altera o estado do veículo para em uso, removendo-o da estação de origem.
- A operação deve ocorrer somente se todas as validações forem satisfeitas.

**`finalizarViagem(Viagem viagem, Estacao estacaoDestino, LocalDateTime dataHoraFim)`**
- Responsável por coordenar o encerramento de uma viagem.
- Verifica se a viagem está em andamento e se a estação de destino possui vaga disponível.
- Solicita à viagem o encerramento, registrando a estação de destino e a data e hora de término, além de calcular a duração e o valor cobrado conforme o plano do usuário.
- Atualiza o estado e a localização do veículo, adicionando-o à estação de destino.
- O consumo da bateria dos veículos elétricos é aplicado por meio do método polimórfico `finalizarUtilizacao(Estacao estacaoDestino, long minutos)` da classe `Veiculo`.
- Registra o débito na carteira do usuário e remove a referência da viagem ativa.
- O saldo pode ficar negativo após o pagamento, conforme as regras do sistema, impedindo o usuário de iniciar outra viagem até regularizar o saldo.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- Adicionado `private ArrayList<Plano> planosDisponiveis;`
  - Motivo: os três planos são criados uma única vez no construtor de `Sistema` e compartilhados por todos os usuários, pois não guardam dados próprios.
- Adicionado `public List<Plano> consultarPlanos();`
  - Motivo: a tela de cadastro lista os planos disponíveis por esse método, sem criar objetos de plano. Retorna `List` não modificável para impedir alterações externas.
- Adicionados `public void salvarDados() throws PersistenciaException;` e `public void carregarDados() throws PersistenciaException;`
  - Motivo: persistência automática na pasta `dados-gb26`. `carregarDados()` é executado ao abrir o programa; `salvarDados()` após cada operação que altera dados.
- `public void cadastrarVeiculo(Veiculo veiculo) throws EstacaoLotadaException;`
  - Motivo: o veículo é cadastrado já posicionado em uma estação, que pode estar lotada.
- `public RelatorioFaturamento consultarFaturamento(LocalDate dataInicial, LocalDate dataFinal) throws DataInvalidaException;`
  - Motivo: impedir períodos com data final anterior à inicial.
- `public Viagem iniciarViagem(Usuario usuario, Veiculo veiculo, LocalDateTime dataHoraInicio) throws ViagemEmAndamentoException, SaldoInsuficienteException, VeiculoIndisponivelException, VeiculoNaoEncontradoException;`
  - Motivo: `SaldoInsuficienteException` nasce aqui quando `temSaldoMinimo()` retorna `false`. As demais são propagadas de `Usuario`, `Veiculo` e `Estacao` até a interface gráfica.
- `public void finalizarViagem(Viagem viagem, Estacao estacaoDestino, LocalDateTime dataHoraFim) throws DataInvalidaException, EstacaoLotadaException;`
  - Motivo: propaga a validação de datas da `Viagem` e a falta de vaga na estação de destino.

**Observações da atualização:**
- `Sistema` passa a ter uma relação de agregação com `Plano` (`planosDisponiveis 0..*`).
- Quase todas as exceções do sistema passam por `iniciarViagem()` e `finalizarViagem()`, que funcionam como ponto de entrada das operações para a interface gráfica.

### 2.2. Estacao

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

**Atualização 06/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- `private String nome;`
- `private int capacidadeMaxima;`
- `private ArrayList<Veiculo> veiculosEstacionados;`

**Construtor**
- `public Estacao(String nome, int capacidadeMaxima);`

**Métodos**
- `public void adicionarVeiculo(Veiculo veiculo);`
- `public void removerVeiculo(Veiculo veiculo);`
- `public int consultarVagasDisponiveis();`
- `public List<Veiculo> consultarVeiculosDisponiveis();`
- `public int consultarQuantidadeVeiculosEstacionados();`

**Observações da definição:**
- A consulta de veículos disponíveis não deve incluir veículos em manutenção.
- A coleção de veículos estacionados deve respeitar a capacidade máxima da estação.
- Preferir retornar `List` em vez de expor diretamente a implementação `ArrayList`. Também será necessário evitar que consultas permitam modificar diretamente a coleção interna.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- `public void adicionarVeiculo(Veiculo veiculo) throws EstacaoLotadaException;`
  - Motivo: garantir a regra de que a estação nunca recebe mais veículos do que sua capacidade.
- `public void removerVeiculo(Veiculo veiculo) throws VeiculoNaoEncontradoException;`
  - Motivo: impedir a remoção de um veículo que não está estacionado nesta estação.

**Observações da atualização:**
- `adicionarVeiculo()` utiliza `consultarVagasDisponiveis()` para decidir se lança a exceção, mantendo a regra de capacidade em um único lugar.
- Como `Veiculo` e `Estacao` se conhecem (os dois lados da associação), a atualização da estação e do veículo deve ocorrer sempre em conjunto, coordenada por `Sistema`.

### 2.3. Veiculo (abstrata)

**Objetivo:** representar as características e os comportamentos comuns a todos os veículos.

**Possíveis atributos:**
- Código identificador.
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
- O estado do veículo será representado por um enum com os valores `DISPONIVEL`, `EM_USO` e `MANUTENCAO`.
- O código identificador será gerado automaticamente, evitando que seja informado manualmente no cadastro.

**Atualização 06/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- `private static int contadorCodigo;`
- `private final int codigoIdentificador;`
- `private String modelo;`
- `private EstadoVeiculo estadoAtual;`
- `private Estacao estacaoAtual;`

**Construtor**
- `protected Veiculo(String modelo, Estacao estacaoAtual);`

**Métodos**
- `public void iniciarUtilizacao();`
- `public void finalizarUtilizacao(Estacao estacaoDestino, long minutos);`
- `public void enviarParaManutencao();`
- `public void retornarDaManutencao();`
- `public boolean podeSerLiberado();`
- `public EstadoVeiculo consultarEstadoAtual();`
- `public Estacao consultarEstacaoAtual();`
- `public String consultarModelo();`
- `public int consultarCodigo();`
- `public abstract double consultarTarifaDeDesbloqueio();`
- `public abstract double consultarTarifaPorMinuto();`
- `public abstract TipoVeiculo consultarTipo();`

**Observações da definição:**
- O atributo `contadorCodigo` será estático para que a numeração seja compartilhada entre todos os veículos.
- O atributo `codigoIdentificador` será `final`, pois o código não deverá mudar após a criação do veículo.
- `consultarEstacaoAtual()` retorna uma referência para `Estacao`, e não uma `String`, pois a localização representa outra entidade do sistema.
- O método `finalizarUtilizacao(Estacao estacaoDestino, long minutos)` foi proposto para permitir que o encerramento da utilização seja tratado polimorficamente. A classe base poderá atualizar o estado, enquanto a classe `VeiculoEletrico` poderá complementar o comportamento aplicando o consumo da bateria.
- A restauração do contador de códigos durante a persistência ainda deverá ser considerada, evitando identificadores duplicados após reiniciar o sistema.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- Adicionado `public String consultarDescricao();`
  - Motivo: requisito 6. A tela exibe tipo e modelo de qualquer veículo por esse método; `VeiculoEletrico` sobrescreve acrescentando a carga da bateria. Evita que a interface gráfica pergunte se o veículo é elétrico.
- Adicionado `public boolean aceitaDescontoUniversitario();` (retorna `true`)
  - Motivo: o `PlanoUniversitario` pergunta ao veículo se o desconto se aplica, sem `instanceof` e sem comparar o tipo. Novos veículos herdam a resposta padrão.
- Adicionado `public void recarregarBateria() throws OperacaoInvalidaException;` (concreto, sempre lança a exceção)
  - Motivo: requisito 5. Permite solicitar a recarga a partir de qualquer `Veiculo`, sem cast para `VeiculoEletrico`. Veículos sem bateria informam que a operação não se aplica.
- `public void iniciarUtilizacao() throws VeiculoIndisponivelException;`
  - Motivo: impedir a liberação quando `podeSerLiberado()` retornar `false` (indisponível ou, nos elétricos, com menos de 20% de bateria).
- `public void enviarParaManutencao() throws OperacaoInvalidaException;`
  - Motivo: um veículo em uso não pode ser enviado para manutenção.
- `public void retornarDaManutencao() throws OperacaoInvalidaException;`
  - Motivo: só é possível retornar um veículo que está em manutenção.

**Observações da atualização:**
- `consultarDescricao()`, `aceitaDescontoUniversitario()` e `recarregarBateria()` são métodos concretos (não abstratos), pois possuem um comportamento padrão. Apenas as subclasses que precisam de outro comportamento os sobrescrevem.

### 2.4. VeiculoMecanico (abstrata)

**Removido da estrutura atual.**

**Objetivo anterior:** representar uma categoria de veículos que não dependem de bateria.

**Motivo da remoção:** a classe foi inicialmente considerada para manter uma hierarquia simétrica com `VeiculoEletrico`. Entretanto, não foram identificados atributos ou comportamentos próprios que justificassem sua existência. Como teria apenas a função de agrupar a bicicleta mecânica, sem acrescentar responsabilidades, optou-se por removê-la.

A classe `BicicletaMecanica` passa a herdar diretamente de `Veiculo`.

#### 2.4.1. BicicletaMecanica

**Objetivo:** representar as bicicletas mecânicas disponíveis no sistema.

**Possíveis atributos:**
- Atributos comuns herdados de `Veiculo`.

**Possíveis métodos:**
- Implementar os comportamentos abstratos exigidos pela classe `Veiculo`.
- Definir suas tarifas específicas.

**Atualização 06/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- Nenhum atributo próprio identificado.

**Construtor**
- `public BicicletaMecanica(String modelo, Estacao estacaoAtual);`

**Métodos**
- `@Override public double consultarTarifaDeDesbloqueio();`
- `@Override public double consultarTarifaPorMinuto();`
- `@Override public TipoVeiculo consultarTipo();`

**Observações da definição:**
- A classe herda diretamente de `Veiculo`.
- As tarifas e o tipo retornado deverão corresponder à bicicleta mecânica.
- A classe não precisa implementar comportamentos de bateria.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- Nenhuma alteração na classe.

**Observações da atualização:**
- Herda de `Veiculo` os novos métodos `consultarDescricao()`, `aceitaDescontoUniversitario()` (retorna `true`) e `recarregarBateria()` (lança `OperacaoInvalidaException`, pois a bicicleta mecânica não possui bateria).

### 2.5. VeiculoEletrico (abstrata)

**Objetivo:** representar características e comportamentos compartilhados pelos veículos elétricos.

**Possíveis atributos:**
- Nível de bateria (%).

**Possíveis métodos:**
- Recarregar bateria.
- Verificar carga mínima para iniciar viagem.
- Consultar nível de bateria.
- Controlar consumo de bateria.
- Definir consumo por minuto.

**Observações:**
- A bateria deve iniciar com um valor entre 0% e 100%.
- A viagem somente poderá ser iniciada com pelo menos 20% de bateria.
- A bateria nunca poderá ficar abaixo de 0%.
- A recarga deverá levar a bateria até 100%, respeitando a regra de que o veículo não pode estar em uso.
- O consumo por minuto poderá variar de acordo com o tipo de veículo elétrico.

**Atualização 06/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- Herdados da classe abstrata pai (`Veiculo`).
- `private int nivelBateria;`

**Construtor**
- `protected VeiculoEletrico(String modelo, Estacao estacaoAtual, int nivelBateria);`

**Métodos**
- `public void recarregarBateria();`
- `public boolean possuiCargaMinimaParaViagem();`
- `public int consultarNivelBateria();`
- `public void consumirBateria(long minutos);`
- `public abstract int consultarConsumoPorMinuto();`
- `@Override public void finalizarUtilizacao(Estacao estacaoDestino, long minutos);`
- `@Override public boolean podeSerLiberado();`

**Observações da definição:**
- `consultarNivelBateria()` deve ser implementado uma única vez nesta classe, pois o comportamento é comum aos veículos elétricos.
- `consumirBateria(long minutos)` também poderá ser implementado nesta classe, utilizando `consultarConsumoPorMinuto()` para obter o consumo específico da subclasse.
- `consultarConsumoPorMinuto()` será abstrato, pois cada tipo de veículo elétrico possui um consumo diferente.
- `recarregarBateria()` deverá impedir a recarga quando o veículo estiver em uso.
- `possuiCargaMinimaParaViagem()` deverá verificar se a bateria possui pelo menos 20%.
- `podeSerLiberado()` poderá reunir as condições necessárias para liberar o veículo, considerando sua disponibilidade e, nesta classe, também a carga mínima da bateria.
- `finalizarUtilizacao(Estacao estacaoDestino, long minutos)` poderá sobrescrever o método de `Veiculo`, chamando o comportamento da classe base e aplicando o consumo de bateria.
- O método `aceitaDescontoUniversitario()` foi retirado da proposta. A regra de desconto pertence ao plano contratado, não ao veículo. O plano universitário poderá consultar o tipo do veículo para decidir se o desconto será aplicado.
- Caso o consumo calculado ultrapasse a carga restante, a bateria deverá chegar a 0%, nunca assumir valor negativo. O encerramento da viagem ainda deverá ser permitido.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- `protected VeiculoEletrico(String modelo, Estacao estacaoAtual, int nivelBateria) throws ValorInvalidoException;`
  - Motivo: impedir a criação de veículos com bateria fora do intervalo de 0% a 100%.
- `possuiCargaMinimaParaViagem()` e `consumirBateria(long minutos)` passaram de `public` para `protected`.
  - Motivo: são usados apenas internamente pelas sobrescritas `podeSerLiberado()` e `finalizarUtilizacao()`; nenhuma classe externa precisa chamá-los.
- `@Override public void recarregarBateria() throws OperacaoInvalidaException;`
  - Motivo: sobrescreve o método de `Veiculo`, recarregando a bateria até 100%. Lança a exceção quando o veículo está em uso.
- `@Override public String consultarDescricao();`
  - Motivo: acrescenta a carga atual da bateria à descrição do veículo (requisito 6).

**Observações da atualização:**
- **Decisão revista:** o método `aceitaDescontoUniversitario()` voltou para a proposta, agora em `Veiculo` e `PatineteEletrico`. A consulta a `consultarTipo()` dentro do `PlanoUniversitario` seria uma verificação do tipo do objeto, que o enunciado pede para resolver por polimorfismo. Com o método, o plano apenas pergunta ao veículo se o desconto se aplica.
- `recarregarBateria()` e `consultarDescricao()` são implementados nesta classe e, por isso, não são abstratos; as subclasses elétricas os herdam.

### 2.5.1. BicicletaEletrica

**Objetivo:** representar as bicicletas elétricas.

**Possíveis atributos:**
- Atributos comuns herdados de `VeiculoEletrico`.

**Possíveis métodos:**
- Definir consumo de bateria por minuto: 1%.
- Definir tarifas específicas.
- Retornar o tipo de veículo correspondente.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- Nenhum atributo próprio identificado.

**Construtor**
- `public BicicletaEletrica(String modelo, Estacao estacaoAtual, int nivelBateria);`

**Métodos**
- `@Override public double consultarTarifaDeDesbloqueio();`
- `@Override public double consultarTarifaPorMinuto();`
- `@Override public TipoVeiculo consultarTipo();`
- `@Override public int consultarConsumoPorMinuto();`

**Observações da definição:**
- O consumo de bateria será de 1% por minuto.
- O comportamento de recarga e consumo será herdado de `VeiculoEletrico`.
- Não será necessário sobrescrever `consumirBateria()` nem `finalizarUtilizacao(Estacao estacaoDestino, long minutos)`, desde que a implementação compartilhada em `VeiculoEletrico` atenda às regras.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- `public BicicletaEletrica(String modelo, Estacao estacaoAtual, int nivelBateria) throws ValorInvalidoException;`
  - Motivo: o construtor chama o de `VeiculoEletrico` (`super`), que pode lançar a exceção de bateria inválida.

### 2.5.2. PatineteEletrico

**Objetivo:** representar os patinetes elétricos.

**Possíveis atributos:**
- Atributos comuns herdados de `VeiculoEletrico`.

**Possíveis métodos:**
- Definir consumo de bateria por minuto: 2%.
- Definir tarifas específicas.
- Retornar o tipo de veículo correspondente.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- Nenhum atributo próprio identificado.

**Construtor**
- `public PatineteEletrico(String modelo, Estacao estacaoAtual, int nivelBateria);`

**Métodos**
- `@Override public double consultarTarifaDeDesbloqueio();`
- `@Override public double consultarTarifaPorMinuto();`
- `@Override public TipoVeiculo consultarTipo();`
- `@Override public int consultarConsumoPorMinuto();`

**Observações da definição:**
- O consumo de bateria será de 2% por minuto.
- O comportamento de recarga e consumo será herdado de `VeiculoEletrico`.
- Não será necessário sobrescrever `consumirBateria()` nem `finalizarUtilizacao(Estacao estacaoDestino, long minutos)`, desde que a implementação compartilhada em `VeiculoEletrico` atenda às regras.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- `public PatineteEletrico(String modelo, Estacao estacaoAtual, int nivelBateria) throws ValorInvalidoException;`
  - Motivo: o construtor chama o de `VeiculoEletrico` (`super`), que pode lançar a exceção de bateria inválida.
- Adicionado `@Override public boolean aceitaDescontoUniversitario();` (retorna `false`)
  - Motivo: regra do enunciado. O plano universitário não concede desconto em patinetes.

### 2.6. Usuario

**Objetivo:** representar os usuários cadastrados no sistema.

**Possíveis atributos:**
- Nome.
- E-mail.
- Plano contratado.
- Carteira.
- Viagem ativa.

**Possíveis métodos:**
- Consultar saldo.
- Consultar plano.
- Consultar viagem ativa.
- Consultar extrato.
- Registrar viagem ativa.
- Finalizar viagem ativa.
- Realizar recarga.
- Efetuar pagamento de viagem.

**Observações:**
- Um usuário não pode possuir mais de uma viagem ativa simultaneamente.
- O usuário precisa possuir pelo menos R$ 5,00 de saldo para iniciar uma viagem.
- Caso o saldo fique negativo após o encerramento de uma viagem, será necessário realizar uma recarga suficiente antes de iniciar outra.
- A referência à viagem ativa permite que o próprio usuário controle essa restrição.
- As operações financeiras deverão ser delegadas à classe `Carteira`.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- `private String nome;`
- `private String email;`
- `private Viagem viagemAtiva;`
- `private Plano planoContratado;`
- `private Carteira carteira;`

**Construtor**
- `public Usuario(String nome, String email, Plano planoContratado);`

**Métodos**
- `public String consultarNome();`
- `public String consultarEmail();`
- `public Plano consultarPlanoContratado();`
- `public Viagem consultarViagemAtiva();`
- `public boolean possuiViagemEmAndamento();`
- `public boolean temSaldoMinimo();`
- `public void registrarViagemAtiva(Viagem viagem);`
- `public void finalizarViagemAtiva();`
- `public void recarregarCarteira(double valor, LocalDateTime dataHora);`
- `public void pagarViagem(Viagem viagem);`
- `public double consultarSaldo();`
- `public List<MovimentacaoFinanceira> consultarHistoricoMovimentacoes();`
- `public List<Recarga> consultarRecargas()`

**Observações da definição:**
- A carteira será criada no construtor do usuário, iniciando com saldo zero.
- `registrarViagemAtiva()` deverá impedir o registro de uma nova viagem caso já exista uma em andamento.
- `finalizarViagemAtiva()` deverá remover a referência à viagem ativa após o encerramento.
- `temSaldoMinimo()` deverá consultar a carteira, evitando duplicar a regra de saldo mínimo em `Usuario`.
- A forma de obtenção do extrato completo ainda depende da definição de como as movimentações financeiras serão representadas.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- Removido `public List<Recarga> consultarRecargas();`
  - Motivo: a `Carteira` não mantém mais uma lista exclusiva de recargas, e sim o histórico de `MovimentacaoFinanceira`. O extrato utiliza `consultarHistoricoMovimentacoes()`.
- `public void registrarViagemAtiva(Viagem viagem) throws ViagemEmAndamentoException;`
  - Motivo: impedir o registro de uma nova viagem quando já existe uma em andamento.
- `public void finalizarViagemAtiva(Viagem viagem) throws SemViagemEmAndamentoException;`
  - Motivo: o método passa a receber a viagem encerrada para conferir se é a viagem ativa do usuário. Lança a exceção quando não há viagem em andamento.
- `public void recarregarCarteira(double valor, LocalDateTime dataHora) throws ValorInvalidoException;`
  - Motivo: o método cria a `Recarga`, cujo construtor valida o valor. A exceção é propagada até a interface gráfica.

**Observações da atualização:**
- A extração do extrato fica resolvida: `consultarHistoricoMovimentacoes()` delega à `Carteira`, que mantém recargas e viagens em uma única coleção.

### 2.7. Plano (interface)

**Objetivo:** definir o contrato de comportamento das modalidades de cobrança aplicadas aos usuários.

**Possíveis atributos:**
- Não foram identificados atributos comuns obrigatórios.

**Possíveis métodos:**
- Calcular valor da viagem.
- Consultar nome do plano.

**Observações:**
- Cada modalidade deverá implementar suas próprias regras de cobrança.
- A estrutura deve permitir adicionar novos planos sem modificar as regras das modalidades existentes.
- A proposta atual é utilizar uma interface, pois os planos representam diferentes implementações de um mesmo contrato de cálculo, sem necessidade identificada de compartilhar estado ou implementação.
- O cálculo deverá utilizar as tarifas fornecidas pelo veículo, evitando duplicar os valores das tarifas nas classes de plano.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- Não possui atributos.

**Construtor**
- Não possui construtor próprio, por ser uma interface.

**Métodos**
- `public double calcularValor(Veiculo veiculo, long minutos);`
- `public String consultarNome();`

**Observações da definição:**
- Cada classe concreta implementará o cálculo de acordo com as regras de sua modalidade.
- O método `calcularValor()` receberá o veículo e a duração da viagem, permitindo utilizar as tarifas específicas por meio do polimorfismo.
- A interface não deverá conhecer regras específicas de bateria ou de disponibilidade dos veículos.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- Nenhuma alteração no contrato da interface.

**Observações da atualização:**
- `Sistema` passa a manter os planos em `planosDisponiveis`. Como os planos não possuem estado, um mesmo objeto é compartilhado por todos os usuários da modalidade.

#### 2.7.1. PlanoAvulso

**Regra:** cobrança integral da tarifa do veículo, incluindo desbloqueio e valor por minuto.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- Nenhum atributo próprio identificado.

**Construtor**
- Não possui atributos que exijam inicialização.

**Métodos**
- `@Override public double calcularValor(Veiculo veiculo, long minutos);`
- `@Override public String consultarNome();`

#### 2.7.2. PlanoMensal

**Regra:** não cobra desbloqueio e oferece os primeiros 15 minutos gratuitos de cada viagem. A cobrança considera somente os minutos excedentes.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- Nenhum atributo próprio identificado.

**Construtor**
- Não possui atributos que exijam inicialização.

**Métodos**
- `@Override public double calcularValor(Veiculo veiculo, long minutos);`
- `@Override public String consultarNome();`

#### 2.7.3. PlanoUniversitario

**Regra:** oferece 50% de desconto sobre o valor total da viagem, exceto para patinetes elétricos, que mantêm a tarifa integral.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- Nenhum atributo próprio identificado.

**Construtor**
- Não possui atributos que exijam inicialização.

**Métodos**
- `@Override public double calcularValor(Veiculo veiculo, long minutos);`
- `@Override public String consultarNome();`

**Observações da definição:**
- A decisão sobre aplicar o desconto ficará dentro de `PlanoUniversitario`.
- O plano poderá consultar `veiculo.consultarTipo()` para identificar patinetes elétricos, sem precisar utilizar `instanceof`.
- O desconto será aplicado sobre o valor total da viagem, incluindo desbloqueio, quando aplicável.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- `PlanoUniversitario` passa a usar `veiculo.aceitaDescontoUniversitario()` em vez de `veiculo.consultarTipo()`.
  - Motivo: decidir o desconto comparando o tipo do veículo é uma verificação de tipo, que o enunciado pede para substituir por polimorfismo. Com o novo método, cada veículo informa se aceita o desconto, e um novo tipo de veículo herda a resposta padrão sem alterar o plano.

### 2.8. Carteira

**Objetivo:** controlar o saldo e as movimentações financeiras do usuário, registrando créditos e débitos realizados.

**Possíveis atributos:**
- Saldo atual.
- Histórico de movimentações financeiras.

**Possíveis métodos:**
- Adicionar saldo.
- Debitar valor de uma viagem.
- Consultar saldo.
- Consultar histórico de movimentações.
- Consultar saldo mínimo para iniciar uma viagem.

**Observações:**
- A carteira começa com saldo zero.
- Recargas devem possuir data, horário e valor.
- O valor de uma recarga deve ser maior que zero.
- O saldo pode ficar negativo após o encerramento de uma viagem.
- O usuário precisa possuir pelo menos R$ 5,00 para iniciar uma viagem.
- O extrato deve apresentar recargas e viagens em ordem cronológica, com o saldo após cada movimentação.
- A carteira deverá controlar o saldo, mas não será responsável por calcular o valor das viagens.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- `private double saldoAtual;`
- `private List<MovimentacaoFinanceira> historicoMovimentacoes;`

**Construtor**
- `public Carteira();`

**Métodos**
- `public void adicionarSaldo(Recarga recarga);`
- `public void debitarValor(Viagem viagem);`
- `public double consultarSaldoAtual();`
- `public List<MovimentacaoFinanceira> consultarHistoricoMovimentacoes();`
- `public boolean temSaldoMinimo();`

**Observações da definição:**
- O construtor deverá inicializar o saldo em zero e criar a coleção de movimentações.
- `adicionarSaldo()` deverá registrar uma recarga válida e atualizar o saldo.
- `debitarValor()` deverá registrar o valor cobrado pela viagem e atualizar o saldo, permitindo que ele fique negativo.
- `temSaldoMinimo()` deverá verificar se o saldo atual é igual ou superior a R$ 5,00.
- O histórico deverá permitir consultar tanto recargas quanto viagens, mantendo ambas em uma coleção comum por meio da interface `MovimentacaoFinanceira`.
- A consulta do histórico não deverá permitir alterações diretas na coleção interna.
- O extrato deverá ordenar as movimentações cronologicamente e apresentar o saldo após cada operação. A implementação dessa consulta ainda dependerá da definição de como será representado o saldo após cada movimentação.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- Adicionada a constante `private static final double SALDO_MINIMO = 5.0;`
  - Motivo: registrar o valor da regra de R$ 5,00 em um único lugar, evitando um número fixo dentro de `temSaldoMinimo()`. Se a regra mudar, apenas a constante é alterada.

**Observações da atualização:**
- `adicionarSaldo(Recarga recarga)` não declara exceção: recebe uma `Recarga` já criada e, portanto, já validada pelo construtor. A exceção de valor inválido ocorre antes, em `Usuario.recarregarCarteira()`.

### 2.9. Viagem

**Objetivo:** representar a utilização de um veículo por determinado usuário, registrando seu início, encerramento, duração e valor cobrado.

**Possíveis atributos:**
- Usuário responsável pela viagem.
- Veículo utilizado.
- Estação de origem.
- Estação de destino.
- Data e horário de início.
- Data e horário de encerramento.
- Valor cobrado.

**Possíveis métodos:**
- Registrar encerramento da viagem.
- Calcular duração.
- Calcular valor da viagem.
- Consultar situação da viagem.
- Consultar informações da viagem.

**Observações:**
- A viagem deve possuir data e horário de início e encerramento.
- O encerramento deve ocorrer depois do início.
- Uma viagem em andamento ainda não possui estação de destino, data de encerramento ou valor cobrado.
- A viagem precisa terminar em uma estação com vaga disponível.
- O valor final deve ser calculado de acordo com o plano contratado pelo usuário e as tarifas do veículo.
- O valor final deve ser arredondado para duas casas decimais.
- O encerramento deve atualizar a localização e a disponibilidade do veículo, além de aplicar o consumo de bateria quando necessário.
- O início e o encerramento envolvem diferentes entidades. A coordenação dessas operações ficará sob responsabilidade de `Sistema`, evitando concentrar todas as regras na classe `Viagem`.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- `private Usuario usuario;`
- `private Veiculo veiculo;`
- `private Estacao estacaoOrigem;`
- `private Estacao estacaoDestino;`
- `private LocalDateTime dataHoraInicio;`
- `private LocalDateTime dataHoraFim;`
- `private double valorCobrado;`

**Construtor**
- `public Viagem(Usuario usuario, Veiculo veiculo, Estacao estacaoOrigem, LocalDateTime dataHoraInicio);`

**Métodos**
- `public void finalizarViagem(Estacao estacaoDestino, LocalDateTime dataHoraFim);`
- `public long calcularDuracao();`
- `public double calcularValor();`
- `public boolean estaEmAndamento();`
- `public Usuario consultarUsuario();`
- `public Veiculo consultarVeiculo();`
- `public Estacao consultarEstacaoOrigem();`
- `public Estacao consultarEstacaoDestino();`
- `public LocalDateTime consultarDataHoraInicio();`
- `public LocalDateTime consultarDataHoraFim();`
- `public double consultarValorCobrado();`
- `public LocalDateTime consultarDataHoraMovimentacao();`
- `public double consultarValorMovimentacao();`
- `public TipoMovimentacao consultarTipoMovimentacao();`

**Observações da definição:**
- A classe implementará a interface `MovimentacaoFinanceira`.
- O construtor receberá os dados necessários para registrar o início da viagem. A estação de destino, a data de encerramento e o valor cobrado serão definidos posteriormente.
- `finalizarViagem()` deverá validar se a viagem ainda está em andamento e se a data e horário informados são posteriores ao início.
- `calcularDuracao()` utilizará as datas de início e encerramento para obter o tempo total da viagem em minutos.
- `calcularValor()` utilizará o plano contratado pelo usuário, por meio de `usuario.consultarPlanoContratado()`, e as tarifas fornecidas pelo veículo. Dessa forma, a regra de cobrança continuará concentrada nas implementações de `Plano`.
- O valor calculado deverá ser arredondado para duas casas decimais e armazenado em `valorCobrado` no encerramento.
- `estaEmAndamento()` poderá verificar se `dataHoraFim` ainda não foi definida, dispensando um atributo adicional para representar a situação da viagem.
- A verificação de vagas na estação de destino e a atualização da localização do veículo serão coordenadas por `Sistema`, utilizando os métodos de `Estacao` e `Veiculo`.
- O consumo da bateria será aplicado pelo comportamento polimórfico de `Veiculo.finalizarUtilizacao(Estacao estacaoDestino, long minutos)`, sem necessidade de verificar o tipo concreto do veículo.
- A cobrança será registrada na `Carteira` do usuário durante o encerramento da viagem.
- `consultarDataHoraMovimentacao()` retornará `dataHoraFim`, pois a viagem somente será considerada uma movimentação financeira após seu encerramento.
- `consultarValorMovimentacao()` retornará `valorCobrado`.
- `consultarTipoMovimentacao()` retornará `TipoMovimentacao.DEBITO`.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- `public void finalizarViagem(Estacao estacaoDestino, LocalDateTime dataHoraFim) throws DataInvalidaException;`
  - Motivo: o enunciado exige que a data e a hora de término sejam posteriores ao início.

### 2.10. RelatorioFaturamento

**Objetivo:** representar os resultados de uma consulta de faturamento, armazenando a quantidade de viagens encerradas e os valores arrecadados por tipo de veículo e no total.

**Exemplo de saída:**

| Tipo de veículo | Viagens | Faturamento |
|---|---:|---:|
| Bicicleta mecânica | 12 | R$ 45,00 |
| Bicicleta elétrica | 8 | R$ 62,50 |
| Patinete elétrico | 15 | R$ 90,00 |
| **Total** | **35** | **R$ 197,50** |

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

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- `private LocalDate dataInicial;`
- `private LocalDate dataFinal;`
- `private Map<TipoVeiculo, Integer> quantidadeViagens;`
- `private Map<TipoVeiculo, Double> faturamentoPorTipo;`

**Construtor**
- `public RelatorioFaturamento(LocalDate dataInicial, LocalDate dataFinal, Map<TipoVeiculo, Integer> quantidadeViagens, Map<TipoVeiculo, Double> faturamentoPorTipo);`

**Métodos**
- `public int consultarQuantidadeViagens(TipoVeiculo tipo);`
- `public double consultarFaturamento(TipoVeiculo tipo);`
- `public int consultarQuantidadeTotalViagens();`
- `public double consultarFaturamentoTotal();`
- `public LocalDate consultarDataInicial();`
- `public LocalDate consultarDataFinal();`

**Observações da atualização:**
- Os mapas armazenam os resultados agrupados por `TipoVeiculo`.
- Os totais gerais são calculados a partir dos mapas, sem necessidade de atributos próprios.
- O construtor recebe os resultados já calculados pelo `Sistema`.
- Os mapas devem ser copiados no construtor e não devem permitir alterações externas.
- Consultas para tipos sem viagens devem retornar zero.

### 2.11. MovimentacaoFinanceira

**Objetivo:** definir um contrato comum para operações que alteram o saldo da carteira de um usuário, permitindo armazenar e consultar recargas e pagamentos de viagens de maneira uniforme.

**Atributos**
- Não possui atributos, pois será uma interface.

**Construtor**
- Não possui construtor.

**Métodos**
- `public LocalDateTime consultarDataHoraMovimentacao();`
- `public double consultarValorMovimentacao();`
- `public TipoMovimentacao consultarTipoMovimentacao();`

**Implementações**
- `Viagem` implementará `MovimentacaoFinanceira`:
  - A data da movimentação será a data de encerramento da viagem (`dataHoraFim`).
  - O valor será o valor cobrado pela viagem (`valorCobrado`).
  - O tipo será `TipoMovimentacao.DEBITO`.
- `Recarga` implementará `MovimentacaoFinanceira`:
  - A data da movimentação será a data e hora da recarga.
  - O valor será o valor adicionado à carteira.
  - O tipo será `TipoMovimentacao.CREDITO`.

**Observações de modelagem**
- A interface permite que `Carteira` armazene recargas e viagens em uma mesma coleção, sem precisar tratar cada classe separadamente.
- O método `consultarTipoMovimentacao()` permite diferenciar créditos e débitos.
- O histórico financeiro poderá ser ordenado cronologicamente utilizando a data e hora de cada movimentação.
- A interface atende ao requisito de implementação de uma interface própria por mais de uma classe.
- A interface não será responsável por alterar o saldo da carteira; ela apenas padroniza as informações das movimentações.

**Enum TipoMovimentacao**
- `CREDITO`
- `DEBITO`

### 2.12. Recarga

**Objetivo:** representar uma operação de recarga realizada na carteira de um usuário, armazenando seu valor e data e horário.

**Atualização 10/11 — Definindo atributos e métodos**

**UML CRIADO? --> [X]**

**Atributos**
- `private double valor;`
- `private LocalDateTime dataHora;`

**Construtor**
- `public Recarga(double valor, LocalDateTime dataHora);`

**Métodos**
- `public double consultarValor();`
- `public LocalDateTime consultarDataHora();`
- `public LocalDateTime consultarDataHoraMovimentacao();`
- `public double consultarValorMovimentacao();`
- `public TipoMovimentacao consultarTipoMovimentacao();`

**Observações da definição:**
- A classe implementará a interface `MovimentacaoFinanceira`.
- O construtor deverá validar se o valor da recarga é maior que zero, lançando `ValorInvalidoException` caso contrário.
- A data e o horário serão armazenados para permitir o registro cronológico das movimentações.
- `consultarDataHoraMovimentacao()` retornará o atributo `dataHora`.
- `consultarValorMovimentacao()` retornará o atributo `valor`.
- `consultarTipoMovimentacao()` retornará `TipoMovimentacao.CREDITO`.
- A classe não será responsável por atualizar o saldo da carteira. Essa operação ficará sob responsabilidade de `Carteira`.

**Atualização 13/11 — Ajustes após revisão do diagrama**

**UML CRIADO? --> [X]**

**Ajustes**
- Removidos `public double consultarValor();` e `public LocalDateTime consultarDataHora();`
  - Motivo: duplicavam `consultarValorMovimentacao()` e `consultarDataHoraMovimentacao()`, que a classe já implementa pela interface `MovimentacaoFinanceira`.
- `public Recarga(double valor, LocalDateTime dataHora) throws ValorInvalidoException;`
  - Motivo: a exceção nasce no construtor quando o valor é menor ou igual a zero, impedindo que uma recarga inválida chegue a existir.

---

## 3. Interfaces

### 3.1. Recarregavel - Descartada(Até o presente momento)

Interface inicialmente proposta para representar o comportamento de recarga de bateria.

**Possível método:**
- Recarregar bateria.

**Aplicação inicial:**
- Relacionada aos veículos elétricos.
- `VeiculoEletrico` poderá implementar essa interface, concentrando o comportamento comum de recarga.

### 3.2. MovimentacaoFinanceira

**Objetivo:** Padronizar a consulta de informações das movimentações financeiras realizadas na carteira, permitindo que diferentes classes sejam tratadas de maneira uniforme.

**Métodos**

- `public LocalDateTime consultarDataHoraMovimentacao();`
  - Retorna a data e hora em que a movimentação ocorreu.
- `public double consultarValorMovimentacao();`
  - Retorna o valor da movimentação.
- `public TipoMovimentacao consultarTipoMovimentacao();`
  - Retorna o tipo da movimentação, identificado pelo enum `TipoMovimentacao` (`CREDITO` ou `DEBITO`).

**Implementações**

- `Recarga`: representa uma movimentação de crédito, retornando sua data, valor e o tipo `CREDITO`.
- `Viagem`: representa uma movimentação de débito, retornando a data de encerramento da viagem, o valor cobrado e o tipo `DEBITO`.

**Observações adicionais**

- A interface permite que a classe `Carteira` armazene recargas e viagens em uma única coleção do tipo `List<MovimentacaoFinanceira>`.
- Dessa forma, o histórico financeiro pode consultar e organizar diferentes tipos de movimentação sem precisar identificar a classe concreta de cada objeto.
- A interface não possui atributos nem construtor, pois define apenas os métodos que suas implementações devem fornecer.
- Foi adotada para substituir a proposta anterior de interface `Recarregavel`, que foi descartada por não atender tão bem à necessidade de padronizar o histórico financeiro.

---

## 4. Enumerações

Espaço destinado à definição dos tipos enumerados utilizados pelo sistema, representando conjuntos fixos de valores que auxiliam na padronização dos estados e classificações.

### 4.1. EstadoVeiculo

**Objetivo:** representar a situação atual de um veículo no sistema.

**Valores:**

- `DISPONIVEL`
- `EM_USO`
- `MANUTENCAO`

### 4.2. TipoVeiculo

**Objetivo:** identificar a categoria de cada veículo cadastrado, permitindo diferenciar suas tarifas e características.

**Valores:**

- `BICICLETA_MECANICA`
- `BICICLETA_ELETRICA`
- `PATINETE_ELETRICO`

### 4.3. TipoMovimentacao

**Objetivo:** identificar a natureza de uma movimentação financeira registrada na carteira.

**Valores:**

- `CREDITO`
- `DEBITO`

**Observações gerais:**

- As enumerações representam conjuntos fixos de valores, evitando o uso de textos livres para identificar estados e categorias.
- `EstadoVeiculo` será utilizado por `Veiculo`.
- `TipoVeiculo` será retornado pelo método `consultarTipo()` das classes concretas de veículos.
- `TipoMovimentacao` será utilizado pela interface `MovimentacaoFinanceira` para diferenciar créditos e débitos.

---

## 5. Exceções

Espaço destinado às exceções criadas pela equipe para sinalizar violações das regras de negócio. As exceções são tratadas na interface gráfica, que exibe uma mensagem clara ao usuário sem encerrar o programa.

**Atualização 13/11 — Definição das exceções**

**UML CRIADO? --> [X]**

**Hierarquia**
- `Exception` (java.lang)
  - `GiroBluException`
    - Demais exceções do sistema.

**Observações da hierarquia:**
- Todas as exceções herdam de `GiroBluException`, que herda de `Exception`. Dessa forma, a interface gráfica pode tratar todas as violações de regra em um único `catch`, se necessário.
- Cada exceção possui um construtor `public NomeException(String mensagem)`, repassando a mensagem para a superclasse.

| Exceção | Regra violada | Onde é lançada | Por onde é propagada |
|---|---|---|---|
| `ValorInvalidoException` | Recarga com valor menor ou igual a zero; bateria fora de 0% a 100% | `Recarga()`, `VeiculoEletrico()` | `Usuario.recarregarCarteira()`, construtores de `BicicletaEletrica` e `PatineteEletrico` |
| `SaldoInsuficienteException` | Saldo menor que R$ 5,00 para iniciar viagem | `Sistema.iniciarViagem()` | — |
| `ViagemEmAndamentoException` | Usuário já possui viagem em andamento | `Usuario.registrarViagemAtiva()` | `Sistema.iniciarViagem()` |
| `SemViagemEmAndamentoException` | Encerrar viagem inexistente | `Usuario.finalizarViagemAtiva()` | — |
| `VeiculoIndisponivelException` | Veículo indisponível ou elétrico com menos de 20% | `Veiculo.iniciarUtilizacao()` | `Sistema.iniciarViagem()` |
| `EstacaoLotadaException` | Estação sem vaga | `Estacao.adicionarVeiculo()` | `Sistema.cadastrarVeiculo()`, `Sistema.finalizarViagem()` |
| `VeiculoNaoEncontradoException` | Veículo não está na estação | `Estacao.removerVeiculo()` | `Sistema.iniciarViagem()` |
| `DataInvalidaException` | Término anterior ao início; período de faturamento inválido | `Viagem.finalizarViagem()`, `Sistema.consultarFaturamento()` | `Sistema.finalizarViagem()` |
| `OperacaoInvalidaException` | Manutenção de veículo em uso; retorno de veículo fora de manutenção; recarga de veículo em uso ou sem bateria | `Veiculo.enviarParaManutencao()`, `Veiculo.retornarDaManutencao()`, `recarregarBateria()` | — |
| `PersistenciaException` | Falha ao ler ou gravar os arquivos de dados | `Sistema.salvarDados()`, `Sistema.carregarDados()` | — |

**Observações gerais:**
- Métodos que não violam regras (consultas, `possuiViagemEmAndamento()`, `temSaldoMinimo()`, `podeSerLiberado()`, `aceitaDescontoUniversitario()`, `pagarViagem()`, `debitarValor()` e os métodos de `Plano`) não declaram exceções.
- `pagarViagem()` e `debitarValor()` não lançam exceção porque o saldo pode ficar negativo após uma viagem.
- No diagrama, as exceções lançadas são indicadas no final de cada método com `{throws NomeException}`.

---

## 6. Pendências gerais

- [X] Definir os atributos e métodos definitivos de cada classe.
- [X] Definir os relacionamentos entre as classes.
- [X] Definir a aplicação da interface própria exigida pela atividade.
- [X] Avaliar a proposta `Recarregavel` e a alternativa `MovimentacaoFinanceira`.
- [X] Avaliar a necessidade de uma classe específica para representar as recargas.
- [X] Definir como será armazenado o histórico de movimentações financeiras.
- [X] Definir como será armazenado o histórico de viagens.
- [X] Definir a responsabilidade de cada classe no início e encerramento de uma viagem.
- [X] Definir como será realizada a persistência dos dados (`salvarDados()` e `carregarDados()` em `Sistema`, pasta `dados-gb26`).
- [X] Definir a estrutura de `RelatorioFaturamento`.
- [ ] Definir como será realizada a restauração do contador de códigos dos veículos após carregar os dados persistidos.
- [X] Criar e revisar o diagrama UML conforme as decisões forem consolidadas.
- [X] Definir as exceções do sistema e onde cada uma é lançada.
- [ ] Definir o formato dos arquivos CSV (campos de cada entidade e como as referências entre objetos serão salvas).
- [ ] Exportar o diagrama final em PDF para a entrega.

---

## 7. Observações gerais

- A estrutura deve permitir adicionar novos tipos de veículos e planos com o mínimo de alterações nas classes existentes.
- As regras de negócio devem ficar na camada de negócio, sem dependência da interface gráfica.
- Os atributos devem ser encapsulados e suas alterações devem respeitar as regras do sistema.
- O polimorfismo deve ser utilizado para evitar verificações excessivas de tipos com `if` ou `instanceof`.
- Classes abstratas devem concentrar características e comportamentos compartilhados por suas subclasses.
- Interfaces devem representar contratos de comportamento coerentes, evitando sua criação apenas para cumprir requisitos acadêmicos.
- As decisões de modelagem são registradas ao longo do desenvolvimento, preservando o histórico das alterações e suas justificativas.
