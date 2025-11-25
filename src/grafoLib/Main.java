package grafoLib;

public class Main {
    public static void main(String[] args) {

        Grafo<String> grafo = new Grafo<>();

        grafo.adicionarVertice("A");
        grafo.adicionarVertice("B");
        grafo.adicionarVertice("C");
        grafo.adicionarVertice("D");
        grafo.adicionarVertice("E");
        grafo.adicionarVertice("F");

        // A conecta em B e C
        grafo.adicionarAresta("A", "B", 1.0f);
        grafo.adicionarAresta("A", "C", 2.0f);

        // B conecta em D
        grafo.adicionarAresta("B", "D", 4.0f);

        // C conecta em D e E
        grafo.adicionarAresta("C", "D", 1.0f);
        grafo.adicionarAresta("C", "E", 3.0f);

        // D conecta em F
        grafo.adicionarAresta("D", "F", 2.0f);

        // E conecta em F
        grafo.adicionarAresta("E", "A", 1.0f);

        System.out.println(grafo.buscaEmLargura());
    }
}