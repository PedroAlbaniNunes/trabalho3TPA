package grafoLib;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Collections;

public class grafoDjikstra<T> implements grafoInterface<T> {

    // Estruturas do Grafo
    private Map<T, vertice<T>> vertices;
    private Map<vertice<T>, List<aresta<T>>> listaAdjacencia;

    /**
     * Classe auxiliar interna para a Fila de Prioridade de Dijkstra.
     * Armazena um vértice e sua distância atual a partir da origem.
     */

    private class DistanciaVertice {
        vertice<T> vertice;
        double distancia;

        public DistanciaVertice(vertice<T> vertice, double distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }
    }

    public grafoDjikstra() {
        this.vertices = new HashMap<>();
        this.listaAdjacencia = new HashMap<>();
    }

    // --- Métodos da Interface ---

    @Override
    public void adicionarVertice(T novoValor) {
        if (!vertices.containsKey(novoValor)) {
            vertice<T> novoVertice = new vertice<>(novoValor);
            vertices.put(novoValor, novoVertice);
            listaAdjacencia.put(novoVertice, new LinkedList<>());
        }
    }

    // Método da interface não funcional (mantido para compatibilidade, veja nota no final)
    @Override
    public void adicionarAresta(T novoValor) {
        throw new UnsupportedOperationException("Método 'adicionarAresta(T)' não funcional. Use 'adicionarAresta(T valorOrigem, T valorDestino, double peso)'.");
    }

    /**
     * Adiciona uma aresta (direcionada e ponderada) entre dois vértices existentes.
     */
    public void adicionarAresta(T valorOrigem, T valorDestino, double peso) {
        if (!vertices.containsKey(valorOrigem) || !vertices.containsKey(valorDestino)) {
            throw new IllegalArgumentException("Origem ou destino não existe no grafo.");
        }

        vertice<T> origem = vertices.get(valorOrigem);
        vertice<T> destino = vertices.get(valorDestino);

        aresta<T> novaAresta = new aresta<>(origem, destino, peso);
        listaAdjacencia.get(origem).add(novaAresta);
    }

    @Override
    public String caminhaEmLargura() {
        // Implementação do BFS (Busca em Largura)
        if (vertices.isEmpty()) return "Grafo vazio.";

        // ... lógica de BFS, idêntica à anterior ...
        T valorInicio = vertices.keySet().iterator().next();
        vertice<T> inicio = vertices.get(valorInicio);

        StringBuilder resultado = new StringBuilder("BFS a partir de " + inicio.getValor() + ": ");

        Set<vertice<T>> visitados = new HashSet<>();
        Queue<vertice<T>> fila = new LinkedList<>();

        visitados.add(inicio);
        fila.add(inicio);
        resultado.append(inicio.getValor());

        while (!fila.isEmpty()) {
            vertice<T> vAtual = fila.poll();

            for (aresta<T> aresta : listaAdjacencia.get(vAtual)) {
                vertice<T> vizinho = aresta.getDestino();

                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    fila.add(vizinho);
                    resultado.append(" -> ").append(vizinho.getValor());
                }
            }
        }
        return resultado.toString();
    }

    // --- Algoritmo de Dijkstra ---

    /**
     * Calcula o caminho mais curto da origem para todos os outros vértices.
     * * @param valorInicio O valor (rótulo) do vértice de partida.
     * @return Um mapa com o rótulo do vértice (T) e a distância mais curta (Double) a partir da origem.
     * @throws IllegalArgumentException Se o vértice de início não for encontrado.
     */
    public Map<T, Double> dijkstra(T valorInicio) {
        // 1. Validação do Vértice Inicial
        if (!vertices.containsKey(valorInicio)) {
            throw new IllegalArgumentException("Vértice de início não encontrado: " + valorInicio);
        }
        vertice<T> inicio = vertices.get(valorInicio);

        // 2. Inicialização das Distâncias
        Map<T, Double> distancias = new HashMap<>();
        for (T valor : vertices.keySet()) {
            // Inicializa todos com infinito
            distancias.put(valor, Double.MAX_VALUE);
        }
        // Distância da origem para ela mesma é 0
        distancias.put(valorInicio, 0.0);

        // 3. Fila de Prioridade (Min-Heap)
        // O Comparator garante que o elemento com a menor distância seja sempre o próximo
        PriorityQueue<DistanciaVertice> pq = new PriorityQueue<>(
                Comparator.comparingDouble(dv -> dv.distancia)
        );

        pq.add(new DistanciaVertice(inicio, 0.0));

        // 4. Algoritmo Principal
        while (!pq.isEmpty()) {
            DistanciaVertice atualDV = pq.poll();
            vertice<T> u = atualDV.vertice;
            double distanciaU = atualDV.distancia;

            // Otimização: Ignora se esta distância é maior do que a já encontrada (duplicatas na fila)
            if (distanciaU > distancias.get(u.getValor())) {
                continue;
            }

            // 5. Relaxamento das Arestas (Atualização de Distâncias)
            List<aresta<T>> arestas = listaAdjacencia.getOrDefault(u, Collections.emptyList());

            for (aresta<T> aresta : arestas) {
                vertice<T> v = aresta.getDestino();
                double pesoUV = aresta.getPeso();

                double novaDistancia = distanciaU + pesoUV;

                // Se encontramos um caminho mais curto para 'v'
                if (novaDistancia < distancias.get(v.getValor())) {
                    distancias.put(v.getValor(), novaDistancia);
                    pq.add(new DistanciaVertice(v, novaDistancia));
                }
            }
        }

        return distancias;
    }
}