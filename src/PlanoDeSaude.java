import java.util.HashMap;
import java.util.Map;

public class PlanoDeSaude {

    //atributos
    private String nome;


    private Map<String, Double> descontosPorEspecialidade;

    private boolean ofereceInternacaoGratuitaCurta;

    //construtor
    public PlanoDeSaude(String nome) {
        this.nome = nome;
        // Sempre que um novo plano é criado, a lista de descontos começa vazia
        this.descontosPorEspecialidade = new HashMap<>();
        this.ofereceInternacaoGratuitaCurta = false; // Por padrão, não oferece
    }

    //métodos

    public void definirDesconto(String especialidade, double percentual) {
        // Armazena o desconto como um multiplicador para facilitar os cálculos depois
        this.descontosPorEspecialidade.put(especialidade.toLowerCase(), percentual / 100.0);
    }


    public double getDesconto(String especialidade) {
        //se não encontrar nada ele volta zero com esse métodos do java
        return this.descontosPorEspecialidade.getOrDefault(especialidade.toLowerCase(), 0.0);
    }

    //getter

    public String getNome() {
        return nome;
    }

    public boolean isOfereceInternacaoGratuitaCurta() {
        return ofereceInternacaoGratuitaCurta;
    }

    public void setOfereceInternacaoGratuitaCurta(boolean valor) {
        this.ofereceInternacaoGratuitaCurta = valor;
    }

    public Map<String, Double> getDescontosPorEspecialidade() {
        return descontosPorEspecialidade;
    }
}