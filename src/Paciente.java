public class Paciente extends Pessoa { // "extends Pessoa" porque é uma classe filha de PEssoa
    private int idade;

    // Construtor do Paciente
    public Paciente(String nome, String cpf, int idade) {
        // "super(nome, cpf)" chama o construtor da classe mãe (Pessoa)
        // para guardar o nome e o cpf lá.
        super(nome, cpf);
        this.idade = idade;
    }

    // Getter e Setter para a idade
    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}