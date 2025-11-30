package grafoLib;

import java.util.*;

//contextualizando algoritmo de prim:
// seleciona-se um nó -> identifica o nó conectado mais próximo e
// com menos aresta(custo) até que todos os nós estejam conectados
public class Prim<T>{
    //classe aux similar a de dijkstra q armazena infos dos vertices
    private class Infos {
        Vertice<T> vertice;
        //menor peso de arestas
        float chave;
        //vertice de entrada
        Vertice<T> pai;
        boolean incluso;

        Infos(Vertice<T> v){
            this.vertice = v;
            this.chave = Float.MAX_VALUE;
            this.pai = null;
            this.incluso = false;
        }
    }

    private Infos obterInfo(Vertice<T> alvo, ArrayList<Infos> tabela){
        for (Infos info : tabela) {
            if(info.vertice.equals(alvo)){
                return info;
            }
        }
        return null;
    }

    //retorna o vertice ainda nao incluso com a menor chave
    private Infos menorChaveNaoInclusa(ArrayList<Infos> tabela){
        Infos menor = null;
        float min = Float.MAX_VALUE;

        for (Infos info : tabela){
            if (!info.incluso && info.chave < min){
                min = info.chave;
                menor = info;
            }
        }
        return menor;
    }


    //algoritmo de Prim
    public void executarPrim(Grafo<T> grafo, T origem){

        ArrayList<Vertice<T>> vertices = grafo.getVertices();
        ArrayList<Infos> tabela = new ArrayList<>();
        Vertice<T> verticeOrigem = null;

        //cria tabela e identifica o vertice inicial
        for (Vertice<T> v : vertices){
            if (v.getDado().equals(origem)){
                verticeOrigem = v;
            }
            tabela.add(new Infos(v));
        }

        if(verticeOrigem == null){
            System.out.println("Vértice de origem não encontrado no grafo.");
            return;
        }

        //define origem com chave 0
        Infos infoOrigem = obterInfo(verticeOrigem, tabela);
        infoOrigem.chave = 0;

        //processa ate incluir todos os vertices
        for (int i = 0; i < vertices.size(); i++){
            Infos menor = menorChaveNaoInclusa(tabela);
            if (menor == null)
                break;

            menor.incluso = true;

            //verifica se encontrou algum caminho melhor para alcançar um vertice a partir de outro (Relaxamento)
            for (Aresta<T> aresta : menor.vertice.getDestinos()){
                Vertice<T> vizinho = aresta.getDestino();
                Infos infoVizinho = obterInfo(vizinho, tabela);

                if (infoVizinho != null &&
                        !infoVizinho.incluso &&
                        aresta.getPeso() < infoVizinho.chave){

                    infoVizinho.chave = aresta.getPeso();
                    infoVizinho.pai = menor.vertice;
                }

            }
        }

        imprimirAGM(tabela, origem);
    }

    //imprimir a arvore geradora minima (AGM)

    private void imprimirAGM(ArrayList<Infos> tabela, T origem){
        System.out.println("Arvore Geradora Minima");
        System.out.println("Origem: " + origem);

        float total = 0;

        for(Infos info : tabela){
            if (info.pai != null){
                System.out.println(info.pai.getDado() + " -- " + info.vertice.getDado() + " (peso: " + info.chave + ")");

                total += info.chave;
            }
        }

        System.out.println("Custo total da AGM = " + total);
    }
}