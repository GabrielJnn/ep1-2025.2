// No arquivo Consulta.java

public class Consulta {

    private Paciente paciente;
    private Medico medico;
    private String dataHora;
    private String status;
    private String diagnostico;
    private String prescricao;

// a explicação é que estou pegando o objeto da minha classe Paciente e Médico.

//construtor com parametros
    public Consulta(Paciente paciente, Medico medico, String dataHora) {
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.status = "Agendada"; //já começa agendada
        this.diagnostico = "";    //começa sem nada
        this.prescricao = "";     //começa sem nada
    }

    //métodos
    public void cancelar() {
        this.status = "Cancelada";
    }

    public void finalizar(String diagnostico, String prescricao) {
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.status = "Concluída";
    }
public boolean ativas(){
       return status.equals("Agendada");
}
//vou fazer um contador de ativas na main

// Getters
    /*não terá Setters pois caso seja nescessario mudar alguma informaçao na consulta
    apenas cancela e cria outra*/

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public String getDataHora() {
        return dataHora;
    }

    public String getStatus() {
        return status;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getPrescricao() {
        return prescricao;
    }


}