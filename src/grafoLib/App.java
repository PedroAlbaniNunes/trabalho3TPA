package grafoLib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();

        LeitorArquivo leitor = new LeitorArquivo();
        leitor.carregarGrafo("grafo.txt", grafo);

        exibirMenu(grafo);
    }

    private static void exibirMenu(Grafo<String> grafo) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== APP GPV ===");
            System.out.println("1. Busca em Largura");
            System.out.println("2. Dijkstra (Menor Caminho)");
//            System.out.println("3. Prim (Árvore Geradora Mínima)");
            System.out.println("0. Sair");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n###BUSCA EM LARGURA###");
                    System.out.println(grafo.buscaEmLargura());
                    break;

                case 2:
                    executarDijkstra(grafo, scanner);
                    break;

                case 3:
                    //executarPrim();
                    break;

                case 0:
                    System.out.println("Saindo, bjs");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void executarDijkstra(Grafo<String> grafo, Scanner scanner) {
        Dijkstra<String> dij = new Dijkstra<>();

        System.out.println("\n=== Opções do Dijkstra ===");
        System.out.println("1. Relatório geral (de Origem para todos)");
        System.out.println("2. Rota específica (de Origem para Destino)");
        System.out.print("Opção: ");
        int tipo;
        tipo = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite a Origem: ");
        String origem = scanner.nextLine().trim();

        if (tipo == 2) {
            System.out.print("Digite o Destino: ");
            String destino = scanner.nextLine().trim();
            dij.calcularCaminhoUnico(grafo, origem, destino);
        } else {
            dij.calcularTodosOsCaminhos(grafo, origem);
        }
    }

    private static void executarPrim(Grafo<String> grafo, Scanner scanner) {
        //função do prim
    }
}