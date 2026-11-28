package Aplicacao;

import java.time.*;
import java.util.*;
import java.io.*;

import Excecoes.*;
import Interfaces.*;
import Modelo.*;
import Planos.*;
import Relatorios.*;

public class Sistema {

    // ==================== ATRIBUTOS ====================

    private ArrayList<Estacao> estacoes;
    private ArrayList<Veiculo> veiculos;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Viagem> viagens;
    private ArrayList<Plano> planosDisponiveis;
    private static final String ARQUIVO_DADOS = "dados-gb26/giroblu.dat";

    // ==================== CONSTRUTOR ====================
    // Status: IMPLEMENTADO

    public Sistema() {
        this.estacoes = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.viagens = new ArrayList<>();

        this.planosDisponiveis = new ArrayList<>();
        this.planosDisponiveis.add(new PlanoAvulso());
        this.planosDisponiveis.add(new PlanoMensal());
        this.planosDisponiveis.add(new PlanoUniversitario());
    }

    // ==================== CADASTRO DE ESTAÇÃO ====================
    // Status: IMPLEMENTADO

    /**
     * Cadastra uma nova estação.
     *
     * @param estacao estação a cadastrar
     * @throws RegistroJaExistenteException se já existir estação com o mesmo
     *                                      nome (ignorando maiúsculas/minúsculas e
     *                                      espaços nas pontas)
     */
    public void cadastrarEstacao(Estacao estacao) throws RegistroJaExistenteException {
        if (estacao == null) {
            throw new IllegalArgumentException("A estação não pode ser nula.");
        }

        String nomeNovo = estacao.consultarNome().trim();

        for (Estacao existente : estacoes) {
            if (existente.consultarNome().trim().equalsIgnoreCase(nomeNovo)) {
                throw new RegistroJaExistenteException(
                        "Já existe uma estação cadastrada com o nome \"" + nomeNovo + "\".");
            }
        }

        estacoes.add(estacao);
    }

    // ==================== CADASTRO DE VEÍCULO ====================
    // Status: IMPLEMENTADO

    /**
     * Cadastra um veículo, posicionando-o na estação informada na sua criação.
     * A estação é adicionada primeiro; se estiver lotada, o veículo não entra
     * na lista do sistema.
     *
     * @param veiculo veículo a cadastrar
     * @throws EstacaoLotadaException se a estação do veículo não tiver vaga
     */
    public void cadastrarVeiculo(Veiculo veiculo) throws EstacaoLotadaException {
        if (veiculo == null) {
            throw new IllegalArgumentException("O veículo não pode ser nulo.");
        }

        Estacao estacao = veiculo.consultarEstacaoAtual();
        if (estacao == null) {
            throw new IllegalArgumentException("O veículo precisa estar em uma estação.");
        }

        estacao.adicionarVeiculo(veiculo);
        veiculos.add(veiculo);
    }

    // ==================== CADASTRO DE USUÁRIO ====================
    // Status: IMPLEMENTADO

    /**
     * Cadastra um novo usuário.
     *
     * @param usuario usuário a cadastrar
     * @throws RegistroJaExistenteException se o e-mail já estiver em uso
     *                                      (ignorando maiúsculas/minúsculas e
     *                                      espaços nas pontas)
     */
    public void cadastrarUsuario(Usuario usuario) throws RegistroJaExistenteException {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }

        String emailNovo = usuario.consultarEmail().trim();

        for (Usuario existente : usuarios) {
            if (existente.consultarEmail().trim().equalsIgnoreCase(emailNovo)) {
                throw new RegistroJaExistenteException(
                        "Já existe um usuário cadastrado com o e-mail \"" + emailNovo + "\".");
            }
        }

        usuarios.add(usuario);
    }

    // ==================== CONSULTA DE ESTAÇÃO ====================
    // Status: IMPLEMENTADO

    /**
     * Busca uma estação pelo nome.
     *
     * @param nome nome da estação
     * @return a estação encontrada
     * @throws RegistroNaoEncontradoException se não houver estação com esse nome
     */
    public Estacao consultarEstacao(String nome) throws RegistroNaoEncontradoException {
        if (nome == null) {
            throw new IllegalArgumentException("O nome não pode ser nulo.");
        }

        String nomeBuscado = nome.trim();

        for (Estacao estacao : estacoes) {
            if (estacao.consultarNome().trim().equalsIgnoreCase(nomeBuscado)) {
                return estacao;
            }
        }

        throw new RegistroNaoEncontradoException(
                "Nenhuma estação encontrada com o nome \"" + nomeBuscado + "\".");
    }

    /**
     * @return lista não modificável com todas as estações
     */
    public List<Estacao> consultarEstacoes() {
        return Collections.unmodifiableList(estacoes);
    }

    // ==================== CONSULTA DE VEÍCULO ====================
    // Status: IMPLEMENTADO

    /**
     * Busca um veículo pelo código identificador.
     *
     * @param codigo código do veículo
     * @return o veículo encontrado
     * @throws RegistroNaoEncontradoException se não houver veículo com esse código
     */
    public Veiculo consultarVeiculo(int codigo) throws RegistroNaoEncontradoException {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.consultarCodigo() == codigo) {
                return veiculo;
            }
        }

        throw new RegistroNaoEncontradoException(
                "Nenhum veículo encontrado com o código " + codigo + ".");
    }

    // ==================== CONSULTA DE VEÍCULOS COM FILTROS ====================
    // Status: IMPLEMENTADO

    /**
     * Consulta veículos aplicando os filtros informados.
     * Os parâmetros nulos ou vazios são ignorados.
     *
     * @param modelo  parte do modelo do veículo a ser pesquisado
     * @param estacao estação atual do veículo
     * @param estado  estado atual do veículo
     * @param tipo    tipo do veículo
     * @return lista de veículos que correspondem aos filtros informados
     */
    public List<Veiculo> consultarVeiculos(
            String modelo,
            Estacao estacao,
            EstadoVeiculo estado,
            TipoVeiculo tipo) {

        List<Veiculo> resultado = new ArrayList<>();

        for (Veiculo veiculo : veiculos) {

            if (modelo != null && !modelo.isBlank()
                    && !veiculo.consultarModelo().toLowerCase()
                            .contains(modelo.toLowerCase())) {
                continue;
            }

            if (estacao != null && veiculo.consultarEstacaoAtual() != estacao) {
                continue;
            }

            if (estado != null && veiculo.consultarEstadoAtual() != estado) {
                continue;
            }

            if (tipo != null && veiculo.consultarTipo() != tipo) {
                continue;
            }

            resultado.add(veiculo);
        }

        return resultado;
    }

    /**
     * @return lista não modificável com todos os veículos
     */
    public List<Veiculo> consultarVeiculos() {
        return Collections.unmodifiableList(veiculos);
    }

    // ==================== CONSULTA DE USUÁRIO ====================
    // Status: IMPLEMENTADO

    /**
     * Busca um usuário pelo e-mail.
     *
     * @param email e-mail do usuário
     * @return o usuário encontrado
     * @throws RegistroNaoEncontradoException se não houver usuário com esse e-mail
     */
    public Usuario consultarUsuario(String email) throws RegistroNaoEncontradoException {
        if (email == null) {
            throw new IllegalArgumentException("O e-mail não pode ser nulo.");
        }

        String emailBuscado = email.trim();

        for (Usuario usuario : usuarios) {
            if (usuario.consultarEmail().trim().equalsIgnoreCase(emailBuscado)) {
                return usuario;
            }
        }

        throw new RegistroNaoEncontradoException(
                "Nenhum usuário encontrado com o e-mail \"" + emailBuscado + "\".");
    }

    /**
     * @return lista não modificável com todos os usuários
     */
    public List<Usuario> consultarUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }

    // ==================== CONSULTA DE USUÁRIOS POR NOME OU E-MAIL
    // ====================
    // Status: IMPLEMENTADO

    /**
     * Consulta usuários pelo nome ou e-mail.
     * A pesquisa ignora diferenças entre letras maiúsculas e minúsculas.
     * Caso o parâmetro seja nulo ou vazio, retorna todos os usuários.
     *
     * @param pesquisa texto utilizado para pesquisar o nome ou e-mail do usuário
     * @return lista não modificável com os usuários que correspondem à pesquisa
     */
    public List<Usuario> consultarUsuarios(String pesquisa) {

        List<Usuario> resultado = new ArrayList<>();

        String pesquisaNormalizada = pesquisa == null
                ? ""
                : pesquisa.trim().toLowerCase(Locale.ROOT);

        for (Usuario usuario : usuarios) {

            if (pesquisaNormalizada.isEmpty()
                    || usuario.consultarNome().toLowerCase(Locale.ROOT)
                            .contains(pesquisaNormalizada)
                    || usuario.consultarEmail().toLowerCase(Locale.ROOT)
                            .contains(pesquisaNormalizada)) {

                resultado.add(usuario);
            }
        }

        return Collections.unmodifiableList(resultado);
    }

    // ==================== CONSULTA DE VIAGENS ====================
    // Status: IMPLEMENTADO

    /**
     * Lista as viagens de um usuário, em andamento ou encerradas.
     *
     * @param usuario usuário cujas viagens serão listadas
     * @return lista não modificável com as viagens do usuário
     */
    public List<Viagem> consultarViagensDoUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }

        ArrayList<Viagem> doUsuario = new ArrayList<>();

        for (Viagem viagem : viagens) {
            if (viagem.consultarUsuario() == usuario) {
                doUsuario.add(viagem);
            }
        }

        return Collections.unmodifiableList(doUsuario);
    }

    /**
     * @return lista não modificável com todas as viagens
     */
    public List<Viagem> consultarViagens() {
        return Collections.unmodifiableList(viagens);
    }

    // ==================== CONSULTA DE PLANOS ====================
    // Status: IMPLEMENTADO

    /**
     * @return lista não modificável com os planos disponíveis
     */
    public List<Plano> consultarPlanos() {
        return Collections.unmodifiableList(planosDisponiveis);
    }

    /**
     * Altera o plano contratado de um usuário.
     *
     * @param usuario   usuário que terá o plano alterado
     * @param novoPlano novo plano a ser contratado
     * @throws IllegalArgumentException  se o usuário ou o novo plano for nulo,
     *                                   ou se o usuário já possuir o plano
     *                                   informado
     * @throws OperacaoInvalidaException se o usuário possuir uma viagem em
     *                                   andamento
     */
    public void alterarPlanoUsuario(Usuario usuario, Plano novoPlano)
            throws OperacaoInvalidaException {

        if (usuario == null) {
            throw new IllegalArgumentException("O usuário não pode ser nulo.");
        }

        usuario.alterarPlanoContratado(novoPlano);
    }

    // ==================== INICIAR VIAGEM ====================
    // Status: IMPLEMENTADO

    /**
     * Inicia uma viagem. Todas as validações são feitas antes de qualquer
     * alteração: se uma exceção for lançada, nada muda.
     *
     * @param usuario        usuário que vai utilizar o veículo
     * @param veiculo        veículo a ser liberado
     * @param dataHoraInicio data e hora de início
     * @return a viagem criada
     * @throws ViagemEmAndamentoException    se o usuário já tiver viagem ativa
     * @throws SaldoInsuficienteException    se o saldo for menor que o mínimo
     * @throws VeiculoIndisponivelException  se o veículo não puder ser liberado
     * @throws VeiculoNaoEncontradoException se o veículo não estiver na estação
     */
    public Viagem iniciarViagem(Usuario usuario, Veiculo veiculo, LocalDateTime dataHoraInicio)
            throws ViagemEmAndamentoException,
            SaldoInsuficienteException,
            VeiculoIndisponivelException,
            VeiculoNaoEncontradoException {

        if (usuario == null || veiculo == null || dataHoraInicio == null) {
            throw new IllegalArgumentException("Usuário, veículo e data de início são obrigatórios.");
        }

        // 1. Validações que só leem o estado
        if (usuario.possuiViagemEmAndamento()) {
            throw new ViagemEmAndamentoException("O usuário já possui uma viagem em andamento.");
        }
        if (!usuario.temSaldoMinimo()) {
            throw new SaldoInsuficienteException("Saldo insuficiente para iniciar a viagem (mínimo R$ 5,00).");
        }
        if (!veiculo.podeSerLiberado()) {
            throw new VeiculoIndisponivelException("O veículo não está disponível para uso.");
        }

        // 2. Ler a origem antes de iniciar a utilização (depois ela vira null)
        Estacao origem = veiculo.consultarEstacaoAtual();

        // 3. Criar a viagem
        Viagem viagem = new Viagem(usuario, veiculo, origem, dataHoraInicio);

        // 4. Alterações (removerVeiculo primeiro, pois pode lançar)
        origem.removerVeiculo(veiculo);
        veiculo.iniciarUtilizacao();
        usuario.registrarViagemAtiva(viagem);
        viagens.add(viagem);

        return viagem;
    }

    // ==================== FINALIZAR VIAGEM ====================
    // Status: IMPLEMENTADO

    /**
     * Encerra uma viagem: calcula o valor, devolve o veículo na estação de
     * destino e debita o valor da carteira do usuário (o saldo pode ficar
     * negativo).
     *
     * @param viagem         viagem a encerrar
     * @param estacaoDestino estação onde o veículo será devolvido
     * @param dataHoraFim    data e hora de término
     * @throws SemViagemEmAndamentoException se a viagem não estiver em
     *                                       andamento ou não for a viagem ativa do
     *                                       usuário
     * @throws DataInvalidaException         se o término não for posterior ao
     *                                       início
     * @throws EstacaoLotadaException        se a estação de destino não tiver vaga
     */
    public void finalizarViagem(Viagem viagem, Estacao estacaoDestino, LocalDateTime dataHoraFim)
            throws SemViagemEmAndamentoException,
            DataInvalidaException,
            EstacaoLotadaException {

        if (viagem == null || estacaoDestino == null || dataHoraFim == null) {
            throw new IllegalArgumentException("Viagem, estação de destino e data de término são obrigatórias.");
        }

        Usuario usuario = viagem.consultarUsuario();
        Veiculo veiculo = viagem.consultarVeiculo();

        if (!viagem.estaEmAndamento() || usuario.consultarViagemAtiva() != viagem) {
            throw new SemViagemEmAndamentoException("Esta viagem não está em andamento.");
        }
        if (estacaoDestino.consultarVagasDisponiveis() <= 0) {
            throw new EstacaoLotadaException("A estação de destino não possui vagas disponíveis.");
        }

        viagem.finalizarViagem(estacaoDestino, dataHoraFim);

        veiculo.finalizarUtilizacao(estacaoDestino, viagem.calcularDuracao());

        estacaoDestino.adicionarVeiculo(veiculo);

        usuario.pagarViagem(viagem);
        usuario.finalizarViagemAtiva(viagem);
    }

    // ==================== CONSULTA DE FATURAMENTO ====================
    // Status: IMPLEMENTADO

    /**
     * Gera o relatório de faturamento de um período. Considera apenas viagens
     * encerradas cuja data de término esteja dentro do período (datas
     * inclusivas), agrupadas por tipo de veículo.
     *
     * @param dataInicial primeiro dia do período
     * @param dataFinal   último dia do período
     * @return o relatório do período
     * @throws DataInvalidaException se a data final for anterior à inicial
     */
    public RelatorioFaturamento consultarFaturamento(LocalDate dataInicial, LocalDate dataFinal)
            throws DataInvalidaException {

        if (dataInicial == null || dataFinal == null) {
            throw new IllegalArgumentException("As datas do período são obrigatórias.");
        }
        if (dataFinal.isBefore(dataInicial)) {
            throw new DataInvalidaException("A data final não pode ser anterior à data inicial.");
        }

        Map<TipoVeiculo, Integer> quantidadeViagens = new HashMap<>();
        Map<TipoVeiculo, Double> faturamentoPorTipo = new HashMap<>();

        for (Viagem viagem : viagens) {
            if (viagem.estaEmAndamento()) {
                continue;
            }

            LocalDate dataFim = viagem.consultarDataHoraFim().toLocalDate();
            if (dataFim.isBefore(dataInicial) || dataFim.isAfter(dataFinal)) {
                continue;
            }

            TipoVeiculo tipo = viagem.consultarVeiculo().consultarTipo();

            quantidadeViagens.put(tipo, quantidadeViagens.getOrDefault(tipo, 0) + 1);

            double novoTotal = faturamentoPorTipo.getOrDefault(tipo, 0.0) + viagem.consultarValorCobrado();
            faturamentoPorTipo.put(tipo, Math.round(novoTotal * 100) / 100.0);
        }

        return new RelatorioFaturamento(dataInicial, dataFinal, quantidadeViagens, faturamentoPorTipo);
    }

    // ==================== PERSISTÊNCIA ====================
    // Status: IMPLEMENTADO
    //
    // ORDEM DE GRAVAÇÃO/LEITURA (não mudar!):
    // 1. estacoes 2. veiculos 3. usuarios 4. viagens 5. planosDisponiveis

    /**
     * Grava todos os dados do sistema em disco, em um único arquivo,
     * preservando as ligações entre os objetos (inclusive viagens em andamento).
     * Cria a pasta de dados se ela ainda não existir.
     *
     * @throws PersistenciaException se não for possível gravar o arquivo
     */
    public void salvarDados() throws PersistenciaException {
        File arquivo = new File(ARQUIVO_DADOS);
        File pasta = arquivo.getParentFile();

        if (!pasta.exists()) { // verifica se a pasta existe
            pasta.mkdirs(); // cria a pasta
        }

        try (ObjectOutputStream saida = new ObjectOutputStream(
                new FileOutputStream(arquivo))) {

            saida.writeObject(estacoes); 
            saida.writeObject(veiculos);
            saida.writeObject(usuarios); 
            saida.writeObject(viagens); 
            saida.writeObject(planosDisponiveis); 

        } catch (IOException e) {
            throw new PersistenciaException("Não foi possível salvar os dados: " + e.getMessage());
        }
    }

    /**
     * Carrega os dados gravados em disco. Se o arquivo ainda não existir
     * (primeira execução), mantém o sistema vazio. Depois de carregar,
     * restaura o contador de códigos dos veículos para não repetir código.
     *
     * @throws PersistenciaException se o arquivo existir mas não puder ser lido
     */
    @SuppressWarnings("unchecked")
    public void carregarDados() throws PersistenciaException {
        File arquivo = new File(ARQUIVO_DADOS);

        if (!arquivo.exists()) { // primeira execução: nada para carregar
            return;
        }

        try (ObjectInputStream entrada = new ObjectInputStream(
                new FileInputStream(arquivo))) {

            estacoes = (ArrayList<Estacao>) entrada.readObject();
            veiculos = (ArrayList<Veiculo>) entrada.readObject();
            usuarios = (ArrayList<Usuario>) entrada.readObject();
            viagens = (ArrayList<Viagem>) entrada.readObject();
            planosDisponiveis = (ArrayList<Plano>) entrada.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Não foi possível carregar os dados: " + e.getMessage());
        }

        //Não repetir código quando reabrir o programa
        int maiorCodigo = 0;

        for (Veiculo veiculo : veiculos) {
            if (veiculo.consultarCodigo() > maiorCodigo) {
                maiorCodigo = veiculo.consultarCodigo();
            }
        }

        Veiculo.restaurarContador(maiorCodigo);
    }
}