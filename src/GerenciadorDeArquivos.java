import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class GerenciadorDeArquivos {

    private static final String ARQUIVO_PACIENTES = "pacientes.csv";
    private static final String ARQUIVO_MEDICOS = "medicos.csv";
    private static final String ARQUIVO_PLANOS = "planos.csv";
    private static final String ARQUIVO_QUARTOS = "quartos.csv";
    private static final String ARQUIVO_CONSULTAS = "consultas.csv";
    private static final String ARQUIVO_INTERNACOES = "internacoes.csv";

    //métodos p/ pacientes
    public void salvarPacientes(List<Paciente> pacientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_PACIENTES))) {
            for (Paciente paciente : pacientes) {
                String tipo = "COMUM";
                String nomePlano = "N/A";
                if (paciente instanceof PacienteEspecial) {
                    tipo = "ESPECIAL";
                    nomePlano = ((PacienteEspecial) paciente).getPlano().getNome();
                }
                String linha = String.join(";", paciente.getCpf(), paciente.getNome(), String.valueOf(paciente.getIdade()), tipo, nomePlano);
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) { System.out.println("Erro ao salvar pacientes: " + e.getMessage()); }
    }

    public List<Paciente> carregarPacientes(List<PlanoDeSaude> planosCadastrados) {
        List<Paciente> pacientes = new ArrayList<>();
        File arquivo = new File(ARQUIVO_PACIENTES);
        if (!arquivo.exists()) return pacientes;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_PACIENTES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                String cpf = dados[0];
                String nome = dados[1];
                int idade = Integer.parseInt(dados[2]);
                String tipo = dados[3];
                if (tipo.equals("ESPECIAL")) {
                    String nomePlano = dados[4];
                    PlanoDeSaude planoEncontrado = null;
                    for (PlanoDeSaude p : planosCadastrados) {
                        if (p.getNome().equals(nomePlano)) {
                            planoEncontrado = p;
                            break;
                        }
                    }
                    if (planoEncontrado != null) {
                        pacientes.add(new PacienteEspecial(nome, cpf, idade, planoEncontrado));
                    }
                } else {
                    pacientes.add(new Paciente(nome, cpf, idade));
                }
            }
        } catch (IOException e) { System.out.println("Erro ao carregar pacientes: " + e.getMessage()); }
        return pacientes;
    }

    //métodos p/ os médicos
    public void salvarMedicos(List<Medico> medicos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_MEDICOS))) {
            for (Medico medico : medicos) {
                String linha = String.join(";", medico.getCrm(), medico.getNome(), medico.getCpf(), medico.getEspecialidade(), String.valueOf(medico.getCustoConsulta()));
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) { System.out.println("Erro ao salvar médicos: " + e.getMessage()); }
    }

    public List<Medico> carregarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        File arquivo = new File(ARQUIVO_MEDICOS);
        if (!arquivo.exists()) return medicos;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_MEDICOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                medicos.add(new Medico(dados[1], dados[2], dados[0], dados[3], Double.parseDouble(dados[4])));
            }
        } catch (IOException e) { System.out.println("Erro ao carregar médicos: " + e.getMessage()); }
        return medicos;
    }

    //métodos p/ os planos de saúde

    public void salvarPlanos(List<PlanoDeSaude> planos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_PLANOS))) {
            for (PlanoDeSaude plano : planos) {
                StringBuilder linha = new StringBuilder();
                linha.append(plano.getNome()).append(";");
                linha.append(plano.isOfereceInternacaoGratuitaCurta());

                //regras do desconto
                for (Map.Entry<String, Double> entry : plano.getDescontosPorEspecialidade().entrySet()) {
                    //25 = 25%
                    double percentual = entry.getValue() * 100;
                    linha.append(";").append(entry.getKey()).append(";").append(percentual);
                }

                writer.write(linha.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar planos de saúde: " + e.getMessage());
        }
    }


    public List<PlanoDeSaude> carregarPlanos() {
        List<PlanoDeSaude> planos = new ArrayList<>();
        File arquivo = new File(ARQUIVO_PLANOS);
        if (!arquivo.exists()) {
            return planos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_PLANOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                String nome = dados[0];
                boolean ofereceGratuidade = Boolean.parseBoolean(dados[1]);

                PlanoDeSaude plano = new PlanoDeSaude(nome);
                plano.setOfereceInternacaoGratuitaCurta(ofereceGratuidade);

                // Carrega as regras de desconto, se existirem
                // Loop começa em 2 e pula de 2 em 2 (especialidade, desconto)
                for (int i = 2; i < dados.length; i += 2) {
                    String especialidade = dados[i];
                    double percentual = Double.parseDouble(dados[i + 1]);
                    plano.definirDesconto(especialidade, percentual);
                }

                planos.add(plano);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar planos de saúde: " + e.getMessage());
        }
        return planos;
    }

    //métodos p/ os quartos
    public void salvarQuartos(List<Quarto> quartos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_QUARTOS))) {
            for (Quarto quarto : quartos) {
                String linha = quarto.getNumero() + ";" + quarto.isOcupado();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) { System.out.println("Erro ao salvar quartos: " + e.getMessage()); }
    }

    public List<Quarto> carregarQuartos() {
        List<Quarto> quartos = new ArrayList<>();
        File arquivo = new File(ARQUIVO_QUARTOS);
        if (!arquivo.exists()) return quartos;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_QUARTOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                Quarto quarto = new Quarto(Integer.parseInt(dados[0]));
                if (Boolean.parseBoolean(dados[1])) quarto.ocupar();
                quartos.add(quarto);
            }
        } catch (IOException e) { System.out.println("Erro ao carregar quartos: " + e.getMessage()); }
        return quartos;
    }

    // métodos p/ as consultas
    public void salvarConsultas(List<Consulta> consultas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONSULTAS))) {
            for (Consulta c : consultas) {
                String linha = String.join(";", c.getPaciente().getCpf(), c.getMedico().getCrm(), c.getDataHora(), c.getStatus(), c.getDiagnostico(), c.getPrescricao());
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) { System.out.println("Erro ao salvar consultas: " + e.getMessage()); }
    }



        public List<Consulta> carregarConsultas(List<Paciente> pacientes, List<Medico> medicos) {
            List<Consulta> consultas = new ArrayList<>();
            File arquivo = new File(ARQUIVO_CONSULTAS);
            if (!arquivo.exists()) {
                return consultas;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONSULTAS))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(";", -1); // Garante que todos os campos sejam lidos

                    String cpfPaciente = dados[0];
                    String crmMedico = dados[1];

                    Paciente pacienteEncontrado = pacientes.stream().filter(p -> p.getCpf().equals(cpfPaciente)).findFirst().orElse(null);
                    Medico medicoEncontrado = medicos.stream().filter(m -> m.getCrm().equals(crmMedico)).findFirst().orElse(null);

                    if (pacienteEncontrado != null && medicoEncontrado != null) {
                        Consulta consulta = new Consulta(pacienteEncontrado, medicoEncontrado, dados[2]);

                        String status = dados[3];
                        if (status.equals("Concluída")) {
                            consulta.finalizar(dados[4], dados[5]);
                        } else if (status.equals("Cancelada")) {
                            consulta.cancelar();
                        }

                        consultas.add(consulta);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Erro ao carregar consultas: " + e.getMessage());
            }

            return consultas;
        }

    // --- MÉTODOS PARA INTERNAÇÕES ---
    public void salvarInternacoes(List<Internacao> internacoes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_INTERNACOES))) {
            for (Internacao i : internacoes) {
                String linha = String.join(";", i.getPaciente().getCpf(), i.getMedicoResponsavel().getCrm(), String.valueOf(i.getQuarto().getNumero()), i.getDataEntrada(), i.getDataSaida(), i.getStatus());
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) { System.out.println("Erro ao salvar internações: " + e.getMessage()); }
    }

    public List<Internacao> carregarInternacoes(List<Paciente> pacientes, List<Medico> medicos, List<Quarto> quartos) {
        List<Internacao> internacoes = new ArrayList<>();
        File arquivo = new File(ARQUIVO_INTERNACOES);
        if (!arquivo.exists()) return internacoes;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_INTERNACOES))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                String cpfPaciente = dados[0];
                String crmMedico = dados[1];
                int numQuarto = Integer.parseInt(dados[2]);
                Paciente pacienteEncontrado = pacientes.stream().filter(p -> p.getCpf().equals(cpfPaciente)).findFirst().orElse(null);
                Medico medicoEncontrado = medicos.stream().filter(m -> m.getCrm().equals(crmMedico)).findFirst().orElse(null);
                Quarto quartoEncontrado = quartos.stream().filter(q -> q.getNumero() == numQuarto).findFirst().orElse(null);
                if (pacienteEncontrado != null && medicoEncontrado != null && quartoEncontrado != null) {
                    Internacao internacao = new Internacao(pacienteEncontrado, medicoEncontrado, quartoEncontrado, dados[3]);
                    if (dados[5].equals("Finalizada")) {
                        internacao.finalizar(dados[4]);
                    }
                    internacoes.add(internacao);
                }
            }
        } catch (IOException e) { System.out.println("Erro ao carregar internações: " + e.getMessage()); }
        return internacoes;
    }
}