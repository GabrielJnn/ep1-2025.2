public class PacienteEspecial extends Paciente {

    //extra
    private PlanoDeSaude plano;

    public PacienteEspecial(String nome, String cpf, int idade, PlanoDeSaude plano) {
        // A primeira coisa a fazer é chamar o construtor da classe "pai" (Paciente)
        // para que ela guarde o nome, cpf e idade.
        // super classe
        super(nome, cpf, idade);

        // Depois de o pai fazer a parte dele, o filho guarda a informação extra que só ele tem.
        this.plano = plano;
    }

    //getter

    public PlanoDeSaude getPlano() {
        return plano;
    }
}