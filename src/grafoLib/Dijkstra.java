package grafoLib;

import java.util.*;

/**
 * Implementação do Algoritmo de Dijkstra para encontrar caminhos mínimos em grafos.
 * @param <T> O tipo de dado armazenado nos vértices (ex: String, Integer, Cidade, etc).
 */
public class Dijkstra<T> {


    // CLASSE INTERNA

    /**
     * Classe auxiliar 'Infos' (ou Tabela de Roteamento).
     * Armazena o estado de cada vértice durante a execução do algoritmo.
     * Funciona como uma linha na tabela de cálculo do Dijkstra.
     */
    private class Infos {
        Vertice<T> vertice; // O vértice real do grafo
        float distancia;    // A menor distância encontrada até agora da origem até este vértice
        Vertice<T> pai;     // O vértice anterior no caminho (usado para reconstruir a rota)
        boolean visitado;   // Flag para saber se já processamos este vértice (fechado)

        public Infos(Vertice<T> vertice){
            this.vertice = vertice;
            // Inicializa com infinito (Float.MAX_VALUE), pois ainda não conhecemos o caminho
            this.distancia = Float.MAX_VALUE;
            this.pai = null;
            this.visitado = false;
        }
    }

    // MÉTODOS AUXILIARES

    /**
     * Busca linear para encontrar o objeto 'Infos' associado a um vértice específico.
     * Necessário para atualizar distâncias e verificar status de visitado.
     */
    private Infos obterInformacao(Vertice<T> alvo, ArrayList<Infos> listaInformacoes){
        for (Infos info : listaInformacoes){
            if (info.vertice == alvo) {
                return info;
            }
        }
        return null;
    }

    /**
     * Converte o dado genérico (ex: "A", "São Paulo") no objeto Vertice real do grafo.
     */
    private Vertice<T> encontrarVerticeInicial(T valor, ArrayList<Vertice<T>> vertices){
        for (Vertice<T> v : vertices){
            if (v.getDado().equals(valor)){
                return v;
            }
        }
        return null;
    }

    // ALGORITMO PRINCIPAL

    /**
     * Executa a lógica core do Dijkstra.
     * Retorna a tabela completa com as menores distâncias da origem para TODOS os nós.
     */
    private ArrayList<Infos> executarDijkstra(Grafo<T> grafo, T origem){
        ArrayList<Vertice<T>> verticesDoGrafo = grafo.getVertices();
        ArrayList<Infos> tabela = new ArrayList<>();

        // 1. Validação: Verifica se a origem existe no grafo
        Vertice<T> verticeOrigem = encontrarVerticeInicial(origem, verticesDoGrafo);
        if (verticeOrigem == null){
            System.out.println("Vértice " + origem + " não encontrado.");
            return null;
        }

        // 2. Inicialização: Cria a tabela de informações para todos os vértices
        // Todos começam com distância Infinita (definido no construtor de Infos)
        for (Vertice<T> v : verticesDoGrafo){
            tabela.add(new Infos(v));
        }

        // 3. Configura a Origem: A distância da origem para ela mesma é sempre 0
        Infos infoOrigem = obterInformacao(verticeOrigem, tabela);
        infoOrigem.distancia = 0;

        // Loop Principal: Continua enquanto houver vértices alcançáveis não visitados
        while(true) {

            // --- Passo A: Seleção (Greedy/Guloso) ---
            // Busca o vértice não visitado com a MENOR distância acumulada até o momento.
            Infos menor = null;
            float menorDistancia = Float.MAX_VALUE;

            for (Infos info : tabela){
                if (!info.visitado && info.distancia < menorDistancia){
                    menorDistancia = info.distancia;
                    menor = info;
                }
            }

            // Critério de Parada:
            // Se 'menor' for null, todos os vértices acessíveis já foram visitados.
            // Se a distância for MAX_VALUE, os vértices restantes são inalcançáveis (grafo desconexo).
            if (menor == null || menor.distancia == Float.MAX_VALUE){
                break;
            }

            // Marca o vértice atual como visitado (Fechado)
            menor.visitado = true;
            Vertice<T> atual = menor.vertice;

            // --- Passo B: Relaxamento (Relaxation) ---
            // Verifica todos os vizinhos do vértice atual
            for(Aresta<T> aresta : atual.getDestinos()){
                Vertice<T> vizinho = aresta.getDestino();
                Infos infoVizinho = obterInformacao(vizinho, tabela);

                // Só processa se o vizinho ainda não foi "fechado"
                if (infoVizinho != null && !infoVizinho.visitado){

                    // Calcula a nova distância potencial:
                    // Distância até o atual + Peso da aresta até o vizinho
                    float novaDistancia = menor.distancia + aresta.getPeso();

                    // Se encontrou um caminho mais curto que o conhecido anteriormente, atualiza!
                    if (novaDistancia < infoVizinho.distancia){
                        infoVizinho.distancia = novaDistancia;
                        infoVizinho.pai = atual; // Guarda de onde viemos para reconstruir o caminho depois
                    }
                }
            }
        }

        return tabela;
    }

    // MÉTODOS PÚBLICOS DE RELATÓRIO

    /**
     * Calcula e imprime as distâncias e predecessores para TODOS os vértices do grafo.
     */
    public void calcularTodosOsCaminhos(Grafo<T> grafo, T origem){
        ArrayList<Infos> tabela = executarDijkstra(grafo, origem);
        if (tabela == null){
            System.out.println("Origem não encontrada.");
            return;
        }
        imprimirRelatorio(tabela, origem);
    }

    /**
     * Formata a saída da tabela de resultados no console.
     */
    public void imprimirRelatorio(ArrayList<Infos> tabela, T origem ){
        System.out.println("\n Dijkstra a partir de: " + origem + " ===");

        for (Infos info : tabela) {
            // Tratamento visual para nulos e infinitos
            String paiString = (info.pai != null) ? info.pai.getDado().toString() : "---";
            String distString = (info.distancia == Float.MAX_VALUE) ? "Infinito" : String.format("%.1f", info.distancia);

            System.out.println("Destino: " + info.vertice.getDado() +
                    " | Distancia: " + distString +
                    " | Anterior: " + paiString);
        }
        System.out.println("=============================");
    }

    /**
     * Calcula o menor caminho especificamente entre DOIS pontos e reconstrói a rota.
     */
    public void calcularCaminhoUnico(Grafo<T> grafo, T origem, T destino){
        // Primeiro executa o Dijkstra completo a partir da origem
        ArrayList<Infos> tabela = executarDijkstra(grafo, origem);

        if (tabela == null){
            System.out.println("Erro ao processar grafo.");
            return;
        }

        // Encontra os dados do destino na tabela gerada
        Vertice<T> verticeDestino = encontrarVerticeInicial(destino, grafo.getVertices());
        Infos infoDestino = obterInformacao(verticeDestino, tabela);

        // Se a distância for infinita, não há caminho possível
        if (infoDestino == null || infoDestino.distancia == Float.MAX_VALUE){
            System.out.println("Não existe caminho entre " + origem + " e " + destino);
            return;
        }

        // --- Reconstrução do Caminho (Backtracking) ---
        ArrayList<T> caminho = new ArrayList<>();
        Vertice<T> atual = verticeDestino;

        // Começa do destino e volta pelos 'pais' até chegar na origem (ou null)
        while (atual != null){
            caminho.add(atual.getDado());
            Infos infoAtual = obterInformacao(atual, tabela);
            atual = infoAtual.pai;
        }

        // Como montamos do fim para o começo, precisamos inverter a lista
        Collections.reverse(caminho);

        System.out.println("\nMenor caminho: " + origem + " -> " + destino);
        System.out.println("Passos: " + caminho);
        System.out.println("Custo total: " + infoDestino.distancia);
        System.out.println("=====================");
    }
}