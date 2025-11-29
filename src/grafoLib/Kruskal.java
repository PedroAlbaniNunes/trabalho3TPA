package grafoLib;

import java.util.*;

public class Kruskal<T> {


    //representar arestas do grafo (origem, destino, peso)
    private class EntradaAresta{
        Vertice<T> origem;
        Vertice<T> destino;
        float peso;

        EntradaAresta(Vertice<T> origem, Vertice<T> destino, float peso){
            this.origem = origem;
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public String toString() {
            return String.format("%s - %s (%.2f)", origem.getDado(), destino.getDado(), peso);   
        }
    }








}



