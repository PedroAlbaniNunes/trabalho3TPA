package grafoLib;

import java.util.*;

public class Dijkstra<T> {


    // Classe auxiliar
    private class Infos {
        Vertice<T> vertice;
        float distancia;
        Vertice<T> pai;
        boolean visitado;

        public Infos(Vertice<T> vertice){
            this.vertice = vertice;
            this.distancia = Float.MAX_VALUE;
            this.pai = null;
            this.visitado = false;
        }
    }

    //Metodos auxiliares

    // Busca na lista de informações qual objeto corresponde ao vértice físico
    private Infos obterInformação(Vertice<T> alvo, ArrayList<Infos> listaInformacoes){
        for (Infos info : listaInformacoes){
            if (info.vertice == alvo) {
                return info;
            }
        }
        return null;
    }

    //Busca o vertice inicial baseado no valor passado
    private Vertice<T> encontrarVerticeInicial(T valor, ArrayList<Vertice<T>> vertices){
        for (Vertice<T> v : vertices){
            if (v.getDado().equals(valor)){
                return v;
            }
        }
        return null;
    }

    //Algoritmo principal

    private ArrayList<Infos> executarDijkstra(Grafo<T> grafo, T origem){
        ArrayList<Vertice<T>> verticesDoGrafo = grafo.getVertices();
        ArrayList<Infos> tabela = new ArrayList<>();


        Vertice<T> verticeOrigem = encontrarVerticeInicial(origem, verticesDoGrafo);
        if (verticeOrigem == null){
            System.out.println("Vertice " + origem + "não encontrado.");
            return null;
        }

        for (Vertice<T> v : verticesDoGrafo){
            tabela.add(new Infos(v));
        }

        Infos infoOrigem = obterInformação(verticeOrigem, tabela);
        infoOrigem.distancia = 0;

        while(true) {

            Infos menor = null;
            float menorDistancia = Float.MAX_VALUE;

            for (Infos info : tabela){
                if (!info.visitado && info.distancia < menorDistancia){
                    menorDistancia = info.distancia;
                    menor = info;
                }
            }

            if (menor == null || menor.distancia == Float.MAX_VALUE){
                break;
            }

            menor.visitado = true;
            Vertice<T> atual = menor.vertice;

            for(Aresta<T> aresta : atual.getDestinos()){
                Vertice<T> vizinho = aresta.getDestino();
                Infos infoVizinho = obterInformação(vizinho, tabela);

                if (infoVizinho != null && !infoVizinho.visitado){
                    float novaDistancia = menor.distancia + aresta.getPeso();

                    if (novaDistancia < infoVizinho.distancia){
                        infoVizinho.distancia = novaDistancia;
                        infoVizinho.pai = atual;
                    }
                }
            }

        }

        return tabela;
    }

    public void calcularTodosOsCaminhos(Grafo<T> grafo, T origem){
        ArrayList<Infos> tabela = executarDijkstra(grafo, origem);
        if (tabela == null){
            System.out.println("Origem não encontrada.");
            return;
        }
        imprimirRelatorio(tabela, origem);
    }

    public void imprimirRelatorio(ArrayList<Infos> tabela, T origem ){
        System.out.println("\n Dijkstra a partir de: " + origem + "===");

        for (Infos info : tabela) {
            String paiString = (info.pai != null) ? info.pai.getDado().toString() : "---";
            String distString = (info.distancia == Float.MAX_VALUE) ? "Infinito" : String.format("%.1f", info.distancia);

            System.out.println("Destino: " + info.vertice.getDado() +
                    " | Distãncia: " + distString +
                    " | Anterior: " + paiString);
        }
        System.out.println("=============================");
    }


    public void calcularCaminhoUnico(Grafo<T> grafo, T origem, T destino){
        ArrayList<Infos> tabela = executarDijkstra(grafo, origem);

        if (tabela == null){
            System.out.println("Erro ao processar grafo.");
            return;
        }

        Vertice<T> verticeDestino = encontrarVerticeInicial(destino, grafo.getVertices());
        Infos infoDestino = obterInformação(verticeDestino, tabela);

        if (infoDestino == null || infoDestino.distancia == Float.MAX_VALUE){
            System.out.println("Não existe caminho entre " + origem + " e " + destino);
            return;
        }

        ArrayList<T> caminho = new ArrayList<>();
        Vertice<T> atual = verticeDestino;

        while (atual != null){
            caminho.add(atual.getDado());

            Infos infoAtual = obterInformação(atual, tabela);
            atual = infoAtual.pai;
        }

        Collections.reverse(caminho);

        System.out.println("\nMenor caminho" + origem + "->" + destino);
        System.out.println("Passos: " + caminho);
        System.out.println("Custo total: " + infoDestino.distancia);
        System.out.println("=====================");
    }
}
