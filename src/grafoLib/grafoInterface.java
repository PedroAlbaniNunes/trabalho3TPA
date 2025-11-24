package grafoLib;
import java.util.Comparator;

public interface grafoInterface<T> {

    public void adicionarVertice(T novoValor);


    public void adicionarAresta(T novoValor);

    public String caminhaEmLargura();
}
