public class Medico extends Pessoa { // Medico também herda de Pessoa
    private String crm;
    private String especialidade;

    // Construtor do Medico
    public Medico(String nome, String cpf, String crm, String especialidade) {
        // Novamente, mandamos nome e cpf para a classe mãe Pessoa resolver
        super(nome, cpf);
        this.crm = crm; // crm é como se fosse um cpf para médicos
        this.especialidade = especialidade;
    }
// Vai receber nome e cpf porque é uma pessoa.
    // Getters e Setters para crm e especialidade
    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}