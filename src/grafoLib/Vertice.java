package grafoLib;

import java.util.ArrayList;

public class Vertice<T> {
    private T dado;
    private ArrayList<Aresta<T>> destinos; //lista de adjacência

    /**
     * Construtor para criar um vértice com um dado específico.
     * @param  dado/rótulo do vértice.
     */

    public Vertice(T dado) {
        this.dado = dado;
        this.destinos = new ArrayList<>();
    }

    // --- Getters e Setters ---

    public T getDado() {
        return dado;
    }

    public void setDado(T valor) {
        this.dado = dado;
    }

    public ArrayList<Aresta<T>> getDestinos() {
        return destinos;
    }

    public void adicionarDestino(Aresta<T> aresta) { //adiciona uma aresta à lista de adjacência, o vértice atual vai contar como origem
        this.destinos.add(aresta);
    }

    @Override
    public String toString() {
        return "V(" + dado + ")";
    }
}
