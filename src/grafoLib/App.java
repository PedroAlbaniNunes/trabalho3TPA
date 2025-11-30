package grafoLib;

import java.util.Scanner;

public class App {
    // Cores e Estilos ANSI
    public static final String RESET = "\u001B[0m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String WHITE_BOLD = "\033[1;37m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String PURPLE = "\033[0;35m";

    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();

        System.out.println(YELLOW + "=====================================================" + RESET);
        System.out.println(RED_BOLD + " [IMPORTANTE] Configuração do Grafo (grafo.txt)" + RESET);
        System.out.println(YELLOW + " Verifique a primeira linha do arquivo:" + RESET);
        System.out.println(" -> Digite " + RED_BOLD + "0" + RESET + " para Não Direcionado");
        System.out.println(" -> Digite " + RED_BOLD + "1" + RESET + " para Direcionado");
        System.out.println(YELLOW + "=====================================================" + RESET);

        LeitorArquivo leitor = new LeitorArquivo();
        try {
            System.out.println(PURPLE + "🔄 Carregando arquivo 'grafo.txt'..." + RESET);
            leitor.carregarGrafo("grafo.txt", grafo);
            System.out.println(GREEN_BOLD + "✅ Grafo carregado com sucesso!\n" + RESET);
        } catch (Exception e) {
            System.out.println(RED_BOLD + "❌ Erro: 'grafo.txt' não encontrado. Iniciando vazio." + RESET);
        }

        exibirMenu(grafo);
    }

    private static void exibirMenu(Grafo<String> grafo) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println(CYAN_BOLD + "\n╔══════════════════════════════════════════╗");
            System.out.println("║                APP GPV                   ║");
            System.out.println("╠══════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN_BOLD + "║" + RESET + " Gerenciamento de Rotas e Grafos          " + CYAN_BOLD + "║" + RESET);
            System.out.println(CYAN_BOLD + "╚══════════════════════════════════════════╝" + RESET);

            System.out.println(WHITE_BOLD + " Escolha uma operação:" + RESET);
            System.out.println(YELLOW + " [1]" + RESET + " 🔍 Busca em Largura " + CYAN_BOLD + "(BFS)" + RESET);
            System.out.println(YELLOW + " [2]" + RESET + " 📍 Dijkstra " + CYAN_BOLD + "(Menor Caminho)" + RESET);
            System.out.println(YELLOW + " [3]" + RESET + " 🌲 Prim " + CYAN_BOLD + "(Árvore Geradora Mínima)" + RESET);
            System.out.println(YELLOW + " [0]" + RESET + " 🚪 Sair");

            System.out.println(CYAN_BOLD + "────────────────────────────────────────────" + RESET);
            System.out.print(GREEN_BOLD + " 👉 Opção: " + RESET);

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n" + BLUE_BOLD + "=== 🔍 RESULTADO DA BUSCA EM LARGURA ===" + RESET);
                    System.out.println(grafo.buscaEmLargura());
                    System.out.println(BLUE_BOLD + "========================================" + RESET);
                    break;

                case 2:
                    executarDijkstra(grafo, scanner);
                    break;

                case 3:
                    executarPrim(grafo, scanner);
                    break;

                case 0:
                    System.out.println("\n" + PURPLE + "👋 Encerrando o sistema. Até logo!" + RESET);
                    break;

                default:
                    System.out.println("\n" + RED_BOLD + "❌ Opção inválida! Tente novamente." + RESET);
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void executarDijkstra(Grafo<String> grafo, Scanner scanner) {
        Dijkstra<String> dij = new Dijkstra<>();

        System.out.println("\n" + CYAN_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        System.out.println(WHITE_BOLD + "       📍 CONFIGURAÇÃO DIJKSTRA" + RESET);
        System.out.println(CYAN_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        System.out.println(YELLOW + " [1]" + RESET + " Relatório Geral (De Origem para Todos)");
        System.out.println(YELLOW + " [2]" + RESET + " Rota Específica (Origem -> Destino)");
        System.out.print(GREEN_BOLD + " 👉 Escolha o tipo: " + RESET);

        int tipo;
        try {
            tipo = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(RED_BOLD + "❌ Entrada inválida." + RESET);
            return;
        }

        System.out.print(GREEN_BOLD + " 🚩 Digite a Origem: " + RESET);
        String origem = scanner.nextLine().trim();

        System.out.println(CYAN_BOLD + "────────────────────────────────────────" + RESET);

        if (tipo == 2) {
            System.out.print(GREEN_BOLD + " 🏁 Digite o Destino: " + RESET);
            String destino = scanner.nextLine().trim();
            System.out.println("\n" + BLUE_BOLD + "CALCULANDO ROTA..." + RESET);
            dij.calcularCaminhoUnico(grafo, origem, destino);
        } else {
            System.out.println("\n" + BLUE_BOLD + "GERANDO RELATÓRIO COMPLETO..." + RESET);
            dij.calcularTodosOsCaminhos(grafo, origem);
        }
        System.out.println(CYAN_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" + RESET);
    }

    private static void executarPrim(Grafo<String> grafo, Scanner scanner) {
        System.out.println("\n" + CYAN_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);
        System.out.println(WHITE_BOLD + "       🌲 ALGORITMO DE PRIM" + RESET);
        System.out.println(CYAN_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" + RESET);

        System.out.print(GREEN_BOLD + " 🌱 Digite o vértice inicial da árvore: " + RESET);
        String origem = scanner.nextLine().trim();

        Prim<String> prim = new Prim<>();

        System.out.println("\n" + BLUE_BOLD + "CONSTRUINDO ÁRVORE GERADORA MÍNIMA..." + RESET);
        prim.executarPrim(grafo, origem);

        System.out.println(CYAN_BOLD + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" + RESET);
    }
}