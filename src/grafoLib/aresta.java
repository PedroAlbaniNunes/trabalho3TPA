package grafoLib;

public class aresta<T> {
    private vertice<T> origem;
    private vertice<T> destino;
    private double peso; // Opcional: pode ser removido se o grafo não for ponderado

    /**
     * Construtor para criar uma aresta.
     * @param origem O vértice de partida.
     * @param destino O vértice de chegada.
     * @param peso O peso associado à aresta (padrão é 1,0 se não for ponderado).
     */

    public aresta(vertice<T> origem, vertice<T> destino, double peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    // Se o grafo não for ponderado, você pode usar este construtor:

    public aresta(vertice<T> origem, vertice<T> destino) {
        this(origem, destino, 1.0); // Peso padrão 1.0
    }

    // --- Getters e Setters ---

    public vertice<T> getOrigem() {
        return origem;
    }

    public vertice<T> getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }


    @Override
    public String toString() {
        return origem.getValor() + " --(" + peso + ")--> " + destino.getValor();
    }
}