package grafoLib;

import java.util.*;

//contextualizando algoritmo de prim:
// seleciona-se um nó -> identifica o nó conectado mais próximo e
// com menos aresta(custo) até que todos os nós estejam conectados

public class Prim<T>{

    //classe aux similar a de dijkstra q guarda as infos dos vertices
    private class Infos {
        Vertice<T> vertice; // O vértice real do grafo (referencia ao objeto)
        float menorAresta; //menor custo da arestas p chegar ate ele
        Vertice<T> pai; //vertice de entrada, de onde o vertice veio
        boolean incluso;

        //construtor: cria ficha de infos para o vertice
        Infos(Vertice<T> v){
            this.vertice = v;
            this.menorAresta = Float.MAX_VALUE;
            this.pai = null;
            this.incluso = false;
        }
    }

    //percorre a tabela procurando pelo vertice alvo
    private Infos obterInfo(Vertice<T> alvo, ArrayList<Infos> tabela){
        for (Infos info : tabela) {
            if(info.vertice.equals(alvo)){
                return info;
            }
        }
        return null;
    }

    //retorna o vertice ainda nao incluso com a menor menorAresta (peso da menor aresta q liga o vertice a arvore atual)
    private Infos menorArestaNaoInclusa(ArrayList<Infos> tabela){
        Infos menor = null; //variavel inicia como nula
        float min = Float.MAX_VALUE; //inicia com maior valor possivel 
        //como a gente precisa procurar o menor valor, a gente inicia com o maior que existe

        for (Infos info : tabela){ //percorre a tabela
            if (!info.incluso && info.menorAresta < min){ //o vertice ainda n esta incluso e a menorAresta dele é menor q o menor encontrado ate agora
                min = info.menorAresta; //att os valores; agora o menor valor passa a ser info.menorAresta
                menor = info;   //armazena o menor valor encontrado da aresta
            }
        }
        return menor;
    }


    //algoritmo de Prim
    public void executarPrim(Grafo<T> grafo, T origem){

        ArrayList<Vertice<T>> vertices = grafo.getVertices(); //lista todos os vertice guardados no grafo
        ArrayList<Infos> tabela = new ArrayList<>(); //cria uma especie de tabela (vertice com dados)
        Vertice<T> verticeOrigem = null; //var p guardar vertice de origem

        // identifica o vertice de origem e inicializa a tabela de infos
        for (Vertice<T> v : vertices){ //percorre todos os vertices do grafo
            if (v.getDado().equals(origem)){ //procura o vertice de origem
                verticeOrigem = v; //guarda o vertice de origem
            }
            tabela.add(new Infos(v)); //inclui o vertice na tabela de infos
        }

        if(verticeOrigem == null){ //se n encontrou o vertice de origem algoritmo encerra (n tem o q procurar)
            System.out.println("Vértice de origem não encontrado no grafo.");
            return;
        }

        //define origem com menorAresta 0
        Infos infoOrigem = obterInfo(verticeOrigem, tabela); //procura na tabela a info do vertice de origem
        infoOrigem.menorAresta = 0; //define a menorAresta do vertice de origem como 0 (pq é o ponto inicial)

        //inicializa o algoritmo
        //processa ate incluir todos os vertices
        for (int i = 0; i < vertices.size(); i++){
            Infos menor = menorArestaNaoInclusa(tabela); //escolhe o vertice com a menorAresta q ainda n foi incluido na arvore
            if (menor == null) //n encontrou mais nada, termina
                break;

            menor.incluso = true; // retorna que o vertice ja foi incluido na arvore 

            //verifica se encontrou algum caminho melhor para alcançar um vertice a partir de outro (Relaxamento)
            for (Aresta<T> aresta : menor.vertice.getDestinos()){
                Vertice<T> vizinho = aresta.getDestino();//pega o vertice vizinho
                Infos infoVizinho = obterInfo(vizinho, tabela); //pega a info do vertice vizinho na tabela

                if (infoVizinho != null &&                      //se o vertice vizinho existe e
                        !infoVizinho.incluso &&                 //n foi incluido na arvore e
                        aresta.getPeso() < infoVizinho.menorAresta){  //a aresta q liga o vertice atual ao vizinho é menor q a menorAresta atual do vizinho

                    infoVizinho.menorAresta = aresta.getPeso(); //atualiza a aresta com o novo melhor custo
                    infoVizinho.pai = menor.vertice; //atualiza o vertice pai (de entrada) do vizinho como o vertice atual
                }

            }
        }

        imprimirAGM(tabela, origem); //exibe a AGM
    }

    //imprimir a arvore geradora minima (AGM)

    private void imprimirAGM(ArrayList<Infos> tabela, T origem){
        System.out.println("Arvore Geradora Minima");
        System.out.println("Origem: " + origem);

        float total = 0;

        for(Infos info : tabela){
            if (info.pai != null){ //se o vertice tem um pai (n é o vertice inicial)
                System.out.println(info.pai.getDado() + " -- " + info.vertice.getDado() + " (peso: " + info.menorAresta + ")"); //exibe a conexao entre o vertice pai e o vertice atual com o peso da aresta

                total += info.menorAresta; //soma o peso da aresta ao custo total
            }
        }

        System.out.println("Custo total da AGM = " + total); //exibe o custo total da AGM
    }
}