public class Quarto {

    private int numero;
    private boolean ocupado;

    public Quarto(int numeroDoQuarto) {
        this.numero = numeroDoQuarto;
        // os Quartos começam desocupados
        this.ocupado = false;
    }
//getters
    public int getNumero() {
        return this.numero;
    }
// não precisa de setters porque não será preciso mudar o numero de um quarto.


    public boolean info() {
        return this.ocupado; //saber se está vazio ou ocupado
    }

    public void ocupar() {
        this.ocupado = true; // mudar valor para ocupado
    }

    public void desocupar() {
        this.ocupado = false;
    } // mudar valor para desocupado
}