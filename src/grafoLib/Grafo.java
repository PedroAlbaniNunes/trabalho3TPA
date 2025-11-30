package grafoLib;

import java.util.ArrayList;

public class Grafo<T> {
    private final ArrayList<Vertice<T>> vertices;

    public Grafo() {
        this.vertices = new ArrayList<>();
    }


    public ArrayList<Vertice<T>> getVertices() {
        return this.vertices;
    }

    //(i) Adicionar um vértice ao grafo: receba um objeto do tipo genérico T e, caso não exista no grafo um vértice com o valor passado, acrescente um vértice ao grafo contendo o objeto recebido;
    public Vertice<T> adicionarVertice(T valor) {
        Vertice<T> novoVertice = new Vertice<>(valor);
        if (!vertices.contains(novoVertice)) { //verifica se o vértice já existe no grafo, se não existir, adiciona
            this.vertices.add(novoVertice);
            return novoVertice;
        }
        else {
            return null; //retorna null se o vértice já existir
        }
    }


    //(ii) Adicionar uma aresta no grafo: receba dois objetos do tipo T (origem e destino) e um valor float, obtenha os vértices referentes aos valores passados e crie uma aresta entre os dois vértices obtidos e tendo o peso passado.
    private Vertice<T> obterVertice (T valor){
        for (Vertice<T> vertice : vertices) {
            if (vertice.getDado().equals(valor)) {
                return vertice;
            }
        }
        return null; // Retorna null se o vértice não for encontrado
    }

    public void adicionarAresta(T valorOrigem, T valorDestino, float peso){
        Vertice<T> verticeOrigem = obterVertice(valorOrigem); //obtém o vértice de origem
        if (verticeOrigem == null) { //verifica se o vértice de origem existe
            verticeOrigem = adicionarVertice(valorOrigem); //se o vértice de origem não existir, cria um novo vértice
        }

        //mesma lógica para o vértice de destino
        Vertice<T> verticeDestino = obterVertice(valorDestino);
        if (verticeDestino == null) {
            verticeDestino = adicionarVertice(valorDestino);
        }

        verticeOrigem.adicionarDestino(new Aresta<>(verticeDestino, peso)); //adiciona a aresta na lista de adjacência do vértice de origem, apontando para o vértice de destino
    }

    //(iii) Fazer o caminhamento em largura no grafo.
    public String buscaEmLargura() {
        if (this.vertices.isEmpty()) return "Grafo vazio";

        ArrayList<Vertice<T>> marcados = new ArrayList<>();
        ArrayList<Vertice<T>> fila = new ArrayList<>();
        StringBuilder resultado = new StringBuilder("Busca em Largura: ");

        // Começa pelo primeiro vértice adicionado (índice 0), conforme Slide 33
        Vertice<T> atual = this.vertices.getFirst(); //pega o primeiro vértice adicionado ao grafo
        marcados.add(atual); //marca o vértice atual como visitado
        fila.add(atual); //adiciona o vértice na fila

        resultado.append(atual.getDado()).append(" "); //adiciona o valor do vértice atual ao resultado pra printar

        while (!fila.isEmpty()) { //mesma coisa que fila.size() > 0 do slide
            atual = fila.getFirst(); //atual recebe o primeiro vértice da fila
            fila.removeFirst(); //remove o vértice atual pra fila andar e analisar o próximo vértice que estiver na fila

            ArrayList<Aresta<T>> destinos = atual.getDestinos(); //pega a lista de adjacência do vértice atual (do que foi removido da fila)

            for (Aresta<T> aresta : destinos) { //percorre todas as arestas da lista de adjacência do vértice atual
                Vertice<T> proximo = aresta.getDestino(); //pega o vértice de destino da aresta atual

                if (!marcados.contains(proximo)) { //verifica se o vértice de destino já foi visitado
                    marcados.add(proximo); //adiciona o vértice que está sendo visitado na lista de marcados pra não visitar novamente
                    fila.add(proximo); //adiciona o vértice na fila pra ser analisado depois
                    resultado.append(proximo.getDado()).append(" ");
                }
            }
        }
        return resultado.toString().trim(); //retorna o resultado da busca em largura
    }
}//principal
