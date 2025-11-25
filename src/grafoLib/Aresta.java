package grafoLib;

public class Aresta<T> {
    private Vertice<T> destino;
    private float peso; // Opcional: pode ser removido se o grafo não for ponderado

    /**
     * Construtor para criar uma aresta.
     * @param destino O vértice de chegada.
     * @param peso O peso associado à aresta (padrão é 1,0 se não for ponderado).
     */

    public Aresta(Vertice<T> destino, float peso) {
        this.destino = destino;
        this.peso = peso;
    }

    // --- Getters e Setters ---


    public Vertice<T> getDestino() {
        return destino;
    }

    public void setDestino(Vertice<T> destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

}