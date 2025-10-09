import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Sistema de Gestão Hospitalar ---");
            System.out.println("--- CADASTROS E GESTÃO ---");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Cadastrar Médico");
            System.out.println("3. Cadastrar Quarto");
            System.out.println("4. Cadastrar Plano de Saúde");
            System.out.println("5. Adicionar Desconto a Plano");
            System.out.println("\n--- AÇÕES ---");
            System.out.println("6. Agendar Consulta");
            System.out.println("7. Realizar Internação");
            System.out.println("8. Finalizar Consulta");
            System.out.println("9. Finalizar Internação (Dar Alta)");
            System.out.println("\n--- RELATÓRIOS ---");
            System.out.println("10. Listar Pacientes");
            System.out.println("11. Listar Médicos");
            System.out.println("12. Ver Detalhes de Consulta");
            System.out.println("13. Relatório de Pacientes Internados");
            System.out.println("14. Relatório de Consultas Agendadas");
            System.out.println("0. Sair e Salvar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Cadastro de Novo Paciente ---");
                    System.out.print("Digite o nome: ");
                    String nomePac = scanner.nextLine();
                    System.out.print("Digite o CPF: ");
                    String cpfPac = scanner.nextLine();
                    System.out.print("Digite a idade: ");
                    int idadePac = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("O paciente tem plano de saúde? (s/n): ");
                    String temPlano = scanner.nextLine();
                    if (temPlano.equalsIgnoreCase("s")) {
                        List<PlanoDeSaude> planosDisponiveis = hospital.getPlanos();
                        if (planosDisponiveis.isEmpty()) {
                            System.out.println("Nenhum plano de saúde cadastrado! Cadastre um plano primeiro (opção 4).");
                            break;
                        }
                        System.out.println("\n--- Planos de Saúde Disponíveis ---");
                        for (int i = 0; i < planosDisponiveis.size(); i++) {
                            System.out.println((i + 1) + ". " + planosDisponiveis.get(i).getNome());
                        }
                        System.out.print("Escolha o número do plano: ");
                        int escolhaPlano = scanner.nextInt() - 1;
                        scanner.nextLine();
                        if (escolhaPlano >= 0 && escolhaPlano < planosDisponiveis.size()) {
                            PlanoDeSaude planoEscolhido = planosDisponiveis.get(escolhaPlano);
                            PacienteEspecial novoPaciente = new PacienteEspecial(nomePac, cpfPac, idadePac, planoEscolhido);
                            hospital.cadastrarPaciente(novoPaciente);
                        } else {
                            System.out.println("Opção de plano inválida!");
                        }
                    } else {
                        Paciente novoPaciente = new Paciente(nomePac, cpfPac, idadePac);
                        hospital.cadastrarPaciente(novoPaciente);
                    }
                    break;
                case 2:
                    System.out.println("\n--- Cadastro de Novo Médico ---");
                    System.out.print("Digite o nome do médico: ");
                    String nomeMed = scanner.nextLine();
                    System.out.print("Digite o CPF do médico: ");
                    String cpfMed = scanner.nextLine();
                    System.out.print("Digite o CRM do médico: ");
                    String crm = scanner.nextLine();
                    System.out.print("Digite a especialidade: ");
                    String especialidadeMed = scanner.nextLine();
                    System.out.print("Digite o custo da consulta (ex: 250,50): ");
                    String custoStr = scanner.nextLine().replace(",", ".");
                    double custo = Double.parseDouble(custoStr);
                    Medico novoMedico = new Medico(nomeMed, cpfMed, crm, especialidadeMed, custo);
                    hospital.cadastrarMedico(novoMedico);
                    break;
                case 3:
                    System.out.println("\n--- Cadastro de Novo Quarto ---");
                    System.out.print("Digite o número do quarto: ");
                    int numeroQuarto = scanner.nextInt();
                    scanner.nextLine();
                    Quarto novoQuarto = new Quarto(numeroQuarto);
                    hospital.cadastrarQuarto(novoQuarto);
                    break;
                case 4:
                    System.out.println("\n--- Cadastro de Novo Plano de Saúde ---");
                    System.out.print("Digite o nome do plano: ");
                    String nomePlano = scanner.nextLine();
                    PlanoDeSaude novoPlano = new PlanoDeSaude(nomePlano);
                    hospital.cadastrarPlanoDeSaude(novoPlano);
                    break;
                case 5:
                    System.out.println("\n--- Adicionar Desconto a Plano de Saúde ---");
                    if (hospital.getPlanos().isEmpty()) {
                        System.out.println("Nenhum plano cadastrado. Cadastre um plano primeiro.");
                        break;
                    }
                    System.out.println("Planos disponíveis:");
                    List<PlanoDeSaude> planos = hospital.getPlanos();
                    for (int i = 0; i < planos.size(); i++) {
                        System.out.println((i + 1) + ". " + planos.get(i).getNome());
                    }
                    System.out.print("Escolha o número do plano para gerir: ");
                    int idxPlano = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (idxPlano >= 0 && idxPlano < planos.size()) {
                        PlanoDeSaude planoEscolhido = planos.get(idxPlano);
                        System.out.print("Digite a especialidade para o desconto (ex: Cardiologia): ");
                        String especialidade = scanner.nextLine();
                        System.out.print("Digite o percentual de desconto (ex: 20 para 20%): ");
                        String percentualStr = scanner.nextLine().replace(",", ".");
                        double percentual = Double.parseDouble(percentualStr);

                        planoEscolhido.definirDesconto(especialidade, percentual);
                        System.out.println("Desconto de " + percentual + "% para " + especialidade + " adicionado ao plano '" + planoEscolhido.getNome() + "'.");
                    } else {
                        System.out.println("Opção de plano inválida.");
                    }
                    break;
                case 6:
                    System.out.println("\n--- Agendamento de Consulta ---");
                    if (hospital.getPacientes().isEmpty() || hospital.getMedicos().isEmpty()) {
                        System.out.println("ERRO: É necessário ter pelo menos um paciente e um médico cadastrados.");
                        break;
                    }
                    System.out.println("\nPacientes disponíveis (digite 0 para cancelar):");
                    List<Paciente> pacientesAgendamento = hospital.getPacientes();
                    for (int i = 0; i < pacientesAgendamento.size(); i++) { System.out.println((i + 1) + ". " + pacientesAgendamento.get(i).getNome()); }
                    System.out.print("Escolha o número do paciente: ");
                    int idxPacienteAgendamento = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if(idxPacienteAgendamento == -1) { System.out.println("Operação cancelada."); break; }

                    System.out.println("\nMédicos disponíveis (digite 0 para cancelar):");
                    List<Medico> medicosAgendamento = hospital.getMedicos();
                    for (int i = 0; i < medicosAgendamento.size(); i++) { System.out.println((i + 1) + ". " + medicosAgendamento.get(i).getNome() + " (" + medicosAgendamento.get(i).getEspecialidade() + ")");}
                    System.out.print("Escolha o número do médico: ");
                    int idxMedicoAgendamento = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if(idxMedicoAgendamento == -1) { System.out.println("Operação cancelada."); break; }

                    System.out.print("Digite a data e hora da consulta (ex: 10/10/2025 14:30): ");
                    String dataHora = scanner.nextLine();
                    hospital.agendarConsulta(pacientesAgendamento.get(idxPacienteAgendamento), medicosAgendamento.get(idxMedicoAgendamento), dataHora);
                    break;
                case 7:
                    System.out.println("\n--- Realizar Internação ---");
                    if (hospital.getPacientes().isEmpty() || hospital.getMedicos().isEmpty() || hospital.getQuartos().isEmpty()) {
                        System.out.println("ERRO: É necessário ter pelo menos um paciente, um médico E um quarto cadastrados.");
                        break;
                    }
                    System.out.println("\nPacientes disponíveis (digite 0 para cancelar):");
                    List<Paciente> pacientesInternacao = hospital.getPacientes();
                    for (int i = 0; i < pacientesInternacao.size(); i++) { System.out.println((i + 1) + ". " + pacientesInternacao.get(i).getNome()); }
                    System.out.print("Escolha o número do paciente para internar: ");
                    int idxPacienteInternacao = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if(idxPacienteInternacao == -1) { System.out.println("Operação cancelada."); break; }

                    System.out.println("\nMédicos disponíveis (digite 0 para cancelar):");
                    List<Medico> medicosInternacao = hospital.getMedicos();
                    for (int i = 0; i < medicosInternacao.size(); i++) { System.out.println((i + 1) + ". " + medicosInternacao.get(i).getNome()); }
                    System.out.print("Escolha o número do médico responsável: ");
                    int idxMedicoInternacao = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if(idxMedicoInternacao == -1) { System.out.println("Operação cancelada."); break; }

                    System.out.println("\nQuartos DISPONÍVEIS (digite 0 para cancelar):");
                    List<Quarto> quartosDisponiveis = new ArrayList<>();
                    for (Quarto quarto : hospital.getQuartos()) { if (!quarto.isOcupado()) { quartosDisponiveis.add(quarto); } }
                    if (quartosDisponiveis.isEmpty()) { System.out.println("Nenhum quarto disponível no momento."); break; }
                    for (int i = 0; i < quartosDisponiveis.size(); i++) { System.out.println((i + 1) + ". Quarto N° " + quartosDisponiveis.get(i).getNumero()); }
                    System.out.print("Escolha o número do quarto: ");
                    int idxQuarto = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if(idxQuarto == -1) { System.out.println("Operação cancelada."); break; }

                    System.out.print("Digite a data de entrada (ex: 08/10/2025): ");
                    String dataEntrada = scanner.nextLine();
                    hospital.realizarInternacao(pacientesInternacao.get(idxPacienteInternacao), medicosInternacao.get(idxMedicoInternacao), quartosDisponiveis.get(idxQuarto), dataEntrada);
                    break;
                case 8:
                    System.out.println("\n--- Finalizar Consulta ---");
                    List<Consulta> consultasAgendadas = hospital.getConsultasFuturas();
                    if(consultasAgendadas.isEmpty()) { System.out.println("Nenhuma consulta agendada para finalizar."); break; }
                    System.out.println("Qual consulta deseja finalizar?");
                    for (int i = 0; i < consultasAgendadas.size(); i++) { System.out.println((i + 1) + ". Paciente: " + consultasAgendadas.get(i).getPaciente().getNome() + " com Dr(a). " + consultasAgendadas.get(i).getMedico().getNome()); }
                    System.out.print("Escolha o número da consulta: ");
                    int idxConsultaFinalizar = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if(idxConsultaFinalizar >= 0 && idxConsultaFinalizar < consultasAgendadas.size()){
                        System.out.print("Digite o diagnóstico: ");
                        String diagnostico = scanner.nextLine();
                        System.out.print("Digite a prescrição: ");
                        String prescricao = scanner.nextLine();
                        consultasAgendadas.get(idxConsultaFinalizar).finalizar(diagnostico, prescricao);
                        System.out.println("Consulta finalizada com sucesso.");
                    } else { System.out.println("Opção inválida."); }
                    break;
                case 9:
                    System.out.println("\n--- Finalizar Internação (Dar Alta) ---");
                    List<Internacao> internacoesAtivas = hospital.getInternacoesAtivas();
                    if (internacoesAtivas.isEmpty()) { System.out.println("Nenhuma internação ativa no momento."); break; }
                    System.out.println("Qual internação deseja finalizar?");
                    for (int i = 0; i < internacoesAtivas.size(); i++) { System.out.println((i + 1) + ". Paciente: " + internacoesAtivas.get(i).getPaciente().getNome() + " (Quarto: " + internacoesAtivas.get(i).getQuarto().getNumero() + ")"); }
                    System.out.print("Escolha o número da internação: ");
                    int idxInternacao = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if(idxInternacao >= 0 && idxInternacao < internacoesAtivas.size()){
                        System.out.print("Digite a data de saída (ex: 15/10/2025): ");
                        String dataSaida = scanner.nextLine();
                        Internacao internacaoFinalizar = internacoesAtivas.get(idxInternacao);
                        internacaoFinalizar.finalizar(dataSaida);
                        internacaoFinalizar.getQuarto().desocupar();
                        System.out.println("Paciente recebeu alta. Quarto liberado.");
                        double custoInternacao = hospital.calcularCustoInternacao(internacaoFinalizar);
                        System.out.println("Custo total da internação: R$ " + String.format("%.2f", custoInternacao));
                    } else { System.out.println("Opção inválida."); }
                    break;
                case 10:
                    System.out.println("\n--- Lista de Pacientes Cadastrados ---");
                    List<Paciente> listaDePacientes = hospital.getPacientes();
                    if (listaDePacientes.isEmpty()) { System.out.println("Nenhum paciente cadastrado."); }
                    else {
                        for (Paciente p : listaDePacientes) {
                            System.out.println("------------------------------------");
                            System.out.println("Nome: " + p.getNome());
                            System.out.println("CPF: " + p.getCpf());
                            System.out.println("Idade: " + p.getIdade());
                            if (p instanceof PacienteEspecial) {
                                System.out.println("Plano de Saúde: " + ((PacienteEspecial) p).getPlano().getNome());
                            } else {
                                System.out.println("Plano de Saúde: Nenhum");
                            }
                        }
                    }
                    break;
                case 11:
                    System.out.println("\n--- Lista de Médicos Cadastrados ---");
                    List<Medico> listaDeMedicos = hospital.getMedicos();
                    if (listaDeMedicos.isEmpty()) { System.out.println("Nenhum médico cadastrado."); }
                    else {
                        for (Medico m : listaDeMedicos) {
                            System.out.println("------------------------------------");
                            System.out.println("Nome: " + m.getNome());
                            System.out.println("CRM: " + m.getCrm());
                            System.out.println("Especialidade: " + m.getEspecialidade());
                            System.out.println("Custo da Consulta: R$ " + m.getCustoConsulta());
                        }
                    }
                    break;
                case 12:
                    System.out.println("\n--- Detalhes da Consulta ---");
                    List<Consulta> todasAsConsultas = hospital.getConsultas();
                    if (todasAsConsultas.isEmpty()) { System.out.println("Nenhuma consulta agendada no sistema."); break; }
                    System.out.println("Consultas agendadas:");
                    for (int i = 0; i < todasAsConsultas.size(); i++) {
                        Consulta c = todasAsConsultas.get(i);
                        System.out.println((i + 1) + ". Paciente: " + c.getPaciente().getNome() + " | Médico: " + c.getMedico().getNome() + " | Data: " + c.getDataHora() + " | Status: " + c.getStatus());
                    }
                    System.out.print("Escolha o número da consulta para ver os detalhes: ");
                    int idxConsulta = scanner.nextInt() - 1;
                    scanner.nextLine();
                    if (idxConsulta >= 0 && idxConsulta < todasAsConsultas.size()) {
                        Consulta consultaEscolhida = todasAsConsultas.get(idxConsulta);
                        double custoFinal = hospital.calcularCustoConsulta(consultaEscolhida);
                        System.out.println("\n--- Detalhes da Consulta Selecionada ---");
                        System.out.println("Paciente: " + consultaEscolhida.getPaciente().getNome());
                        System.out.println("Médico: " + consultaEscolhida.getMedico().getNome());
                        System.out.println("Data/Hora: " + consultaEscolhida.getDataHora());
                        System.out.println("Status: " + consultaEscolhida.getStatus());
                        if (consultaEscolhida.getStatus().equals("Concluída")) {
                            System.out.println("Diagnóstico: " + consultaEscolhida.getDiagnostico());
                            System.out.println("Prescrição: " + consultaEscolhida.getPrescricao());
                        }
                        System.out.println("CUSTO FINAL CALCULADO: R$ " + String.format("%.2f", custoFinal));
                    } else { System.out.println("Opção de consulta inválida."); }
                    break;
                case 13:
                    System.out.println("\n--- Relatório de Pacientes Internados ---");
                    List<Internacao> relatorioInternacoes = hospital.getInternacoesAtivas();
                    if(relatorioInternacoes.isEmpty()){ System.out.println("Nenhum paciente internado no momento."); }
                    else{
                        for(Internacao i : relatorioInternacoes){
                            System.out.println("- Paciente: " + i.getPaciente().getNome() + " (Quarto: " + i.getQuarto().getNumero() + ")");
                        }
                    }
                    break;
                case 14:
                    System.out.println("\n--- Relatório de Consultas Agendadas ---");
                    List<Consulta> relatorioConsultas = hospital.getConsultasFuturas();
                    if(relatorioConsultas.isEmpty()){ System.out.println("Nenhuma consulta agendada no momento."); }
                    else{
                        for(Consulta c : relatorioConsultas){
                            System.out.println("- Paciente: " + c.getPaciente().getNome() + " com Dr(a). " + c.getMedico().getNome() + " em " + c.getDataHora());
                        }
                    }
                    break;
                case 0:
                    System.out.println("A salvar todos os dados...");
                    hospital.salvarDados();
                    System.out.println("Sistema finalizado.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida! Por favor, tente novamente.");
                    break;
            }
        }
    }
}

