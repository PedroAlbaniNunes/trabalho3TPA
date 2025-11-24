package grafoLib;

public class vertice<T> {
    private T valor;

    /**
     * Construtor para criar um vértice com um dado específico.
     * @param valor O dado/rótulo do vértice.
     */

    public vertice(T valor) {
        this.valor = valor;
    }

    // --- Getters e Setters ---

    public T getValor() {
        return valor;
    }

    public void setDado(T valor) {
        this.valor = valor;
    }


    @Override
    public String toString() {
        return "V(" + valor + ")";
    }
}
