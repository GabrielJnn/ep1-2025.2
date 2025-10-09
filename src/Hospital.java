import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

// essa Classe Hospital está funcionando como um centro de comando para todas as aplicações
public class Hospital {

    //atributos
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Quarto> quartos;
    private List<Consulta> consultas;
    private List<Internacao> internacoes;
    private List<PlanoDeSaude> planos;

    private GerenciadorDeArquivos gerenciadorDeArquivos;
    private static final double CUSTO_DIARIA_INTERNACAO = 500.0;

    //construtor
    public Hospital() {
        this.gerenciadorDeArquivos = new GerenciadorDeArquivos();

        this.planos = gerenciadorDeArquivos.carregarPlanos();
        this.quartos = gerenciadorDeArquivos.carregarQuartos();

        //essas aqui carregam depois pois dependem da de cima
        this.pacientes = gerenciadorDeArquivos.carregarPacientes(this.planos);
        this.medicos = gerenciadorDeArquivos.carregarMedicos();

        // depende da anterior
        this.consultas = gerenciadorDeArquivos.carregarConsultas(this.pacientes, this.medicos);
        this.internacoes = gerenciadorDeArquivos.carregarInternacoes(this.pacientes, this.medicos, this.quartos);
    }

    // salvar os dados métodos
    public void salvarDados() {
        gerenciadorDeArquivos.salvarPacientes(this.pacientes);
        gerenciadorDeArquivos.salvarMedicos(this.medicos);
        gerenciadorDeArquivos.salvarPlanos(this.planos);
        gerenciadorDeArquivos.salvarQuartos(this.quartos);
        gerenciadorDeArquivos.salvarConsultas(this.consultas);
        gerenciadorDeArquivos.salvarInternacoes(this.internacoes);
        System.out.println("Dados salvos com sucesso!");
    }

    // Cadastro
    public void cadastrarPaciente(Paciente paciente) {
        this.pacientes.add(paciente); System.out.println("Paciente " + paciente.getNome() + " cadastrado com sucesso!");}
    public void cadastrarMedico(Medico medico) {

        this.medicos.add(medico); System.out.println("Médico " + medico.getNome() + " cadastrado com sucesso!");}

    public void cadastrarQuarto(Quarto novoQuarto) {
        //Validação
        for (Quarto quartoExistente : this.quartos) {
            // Se o número do quarto que já existe for igual ao do novo quarto então erro
            if (quartoExistente.getNumero() == novoQuarto.getNumero()) {
                //avisamos o erro e não fazemos mais nada.
                System.out.println("ERRO: Um quarto com o número " + novoQuarto.getNumero() + " já existe!");
                return; //return para a execução
            }
        }

        // Se o loop terminar e não encontrar nenhum quarto igual, aí sim adicionamos.
        this.quartos.add(novoQuarto);
        System.out.println("Quarto N° " + novoQuarto.getNumero() + " cadastrado com sucesso!");
    }
    public void cadastrarPlanoDeSaude(PlanoDeSaude plano) { this.planos.add(plano); System.out.println("Plano '" + plano.getNome() + "' cadastrado com sucesso!");}

    // --- Métodos de Lógica de Negócio ---
    public boolean agendarConsulta(Paciente paciente, Medico medico, String dataHora) {
        for (Consulta consultaExistente : this.consultas) {
            if (consultaExistente.getMedico() == medico && consultaExistente.getDataHora().equals(dataHora) && consultaExistente.isAtiva()) {
                System.out.println("ERRO: O médico " + medico.getNome() + " já possui uma consulta neste horário.");
                return false;
            }
        }
        Consulta novaConsulta = new Consulta(paciente, medico, dataHora);
        this.consultas.add(novaConsulta);
        System.out.println("Sucesso! Consulta agendada para " + paciente.getNome() + " com " + medico.getNome() + ".");
        return true;
    }

    public boolean realizarInternacao(Paciente paciente, Medico medico, Quarto quarto, String dataEntrada) {
        if (quarto.isOcupado()) {
            System.out.println("ERRO: O quarto " + quarto.getNumero() + " já está ocupado.");
            return false;
        }
        quarto.ocupar();
        Internacao novaInternacao = new Internacao(paciente, medico, quarto, dataEntrada);
        this.internacoes.add(novaInternacao);
        System.out.println("Sucesso! Paciente " + paciente.getNome() + " internado no quarto " + quarto.getNumero() + ".");
        return true;
    }

    public double calcularCustoConsulta(Consulta consulta) {
        double custoBase = consulta.getMedico().getCustoConsulta();
        double custoFinal = custoBase;
        Paciente paciente = consulta.getPaciente();

        if (paciente instanceof PacienteEspecial) {
            PacienteEspecial pacienteEspecial = (PacienteEspecial) paciente;
            PlanoDeSaude plano = pacienteEspecial.getPlano();
            String especialidade = consulta.getMedico().getEspecialidade();
            double percentualDesconto = plano.getDesconto(especialidade);
            custoFinal -= (custoBase * percentualDesconto);
        }

        if (paciente.getIdade() > 60) {
            custoFinal *= 0.90; //desconto para idosos 60+
        }
        return custoFinal;
    }

    public double calcularCustoInternacao(Internacao internacao) {
        if (internacao.getDataSaida() == null || internacao.getDataSaida().isEmpty()) {
            return 0;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate entrada = LocalDate.parse(internacao.getDataEntrada(), formatter);
            LocalDate saida = LocalDate.parse(internacao.getDataSaida(), formatter);
            long dias = ChronoUnit.DAYS.between(entrada, saida);
            if (dias == 0) dias = 1;

            if (internacao.getPaciente() instanceof PacienteEspecial) {
                PacienteEspecial pe = (PacienteEspecial) internacao.getPaciente();
                if (pe.getPlano().isOfereceInternacaoGratuitaCurta() && dias < 7) {
                    return 0.0;
                }
            }
            return dias * CUSTO_DIARIA_INTERNACAO;
        } catch (Exception e) {
            System.out.println("Erro ao calcular custo da internação. Verifique o formato das datas (dd/MM/yyyy).");
            return 0;
        }
    }

    //metodos para os relatorios
    public List<Consulta> getConsultasFuturas() {
        return consultas.stream()
                .filter(Consulta::isAtiva)
                .collect(Collectors.toList());
    }

    public List<Internacao> getInternacoesAtivas() {
        return internacoes.stream()
                .filter(i -> i.getStatus().equals("Ativa"))
                .collect(Collectors.toList());
    }

    //getters
    public List<Paciente> getPacientes() { return pacientes; }
    public List<Medico> getMedicos() { return medicos; }
    public List<Quarto> getQuartos() { return quartos; }
    public List<PlanoDeSaude> getPlanos() { return planos; }
    public List<Consulta> getConsultas() { return consultas; }
    public List<Internacao> getInternacoes() { return internacoes; }
}