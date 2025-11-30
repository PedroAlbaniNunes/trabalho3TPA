package grafoLib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorArquivo {
    /**
     * Lê um arquivo de texto e popula o grafo fornecido
     * Formato esperado das linhas: origem;destino;peso (tem que ser separado por ;)
     * Exemplo: Vitoria;Vila Velha;5.0
     *
     * @param caminhoArquivo  Caminho pro arquivo
     * @param grafo  Objeto Grafo onde os dados serão inseridos
     *
     * @return void
     * @throws IOException Se ocorrer um erro ao ler o arquivo
     */
    public void carregarGrafo(String caminhoArquivo, Grafo<String> grafo) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            String config = br.readLine();

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue; //ignora linhas nmulas

                //quebra a linha em partes usando o ponto e vírgula como separador, por isso tem que deixar o arquivo nesse formato
                //(vila velha; vitoria; 5.0)
                // vira um array {"vila velha", "vitoria", "5.0"}
                String[] partes = linha.split(";");

                if (partes.length == 3) { //{origem, destino, peso}
                    String origem = partes[0].trim();
                    String destino = partes[1].trim();

                    try {
                        float peso = Float.parseFloat(partes[2].trim()); //conversão de string para float

                        grafo.adicionarAresta(origem, destino, peso); //adiciona a ida

                        if (config.equals("0")){ //se >>NÃO<< for direcionado, adiciona a volta
                                grafo.adicionarAresta(destino, origem, peso);
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao ler peso na linha: " + linha + " -> Peso inválido.");
                    }
                } else {
                    System.err.println("Linha com formato inválido ignorada: " + linha);
                }
            }
            System.out.println("Arquivo lido com sucesso! Grafo carregado.");

        } catch (IOException e) {
            System.err.println("Erro ao abrir o arquivo: " + e.getMessage());
        }
    }
}