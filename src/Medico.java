public class Medico extends Pessoa {
    private String crm;
    private String especialidade;
    private double custoConsulta;


    public Medico(String nome, String cpf, String crm, String especialidade, double custoConsulta) {
        super(nome, cpf);
        this.crm = crm;
        this.especialidade = especialidade;
        this.custoConsulta = custoConsulta;
    }

    //getters e setters

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


    public double getCustoConsulta() {
        return this.custoConsulta;
    }

    public void setCustoConsulta(double custoConsulta) {
        this.custoConsulta = custoConsulta;
    }
}