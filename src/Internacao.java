public class Internacao {

    private Paciente paciente;
    private Medico medicoResponsavel;
    private Quarto quarto;
    private String dataEntrada;
    private String dataSaida;
    private String status;

    //construtor
    public Internacao(Paciente paciente, Medico medico, Quarto quarto, String dataEntrada) {
        this.paciente = paciente;
        this.medicoResponsavel = medico;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.status = "Ativa"; // a internação começa ativa
        this.dataSaida = "";   // na alta
    }

    //metodos
public void finalizar(String dataSaida) {
        this.dataSaida = dataSaida;
        this.status = "Finalizada";//alta
    }

    //getters

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedicoResponsavel() {
        return medicoResponsavel;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public String getStatus() {
        return status;
    }
}