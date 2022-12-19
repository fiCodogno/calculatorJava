package calculadora.modelo;

/**
 *
 * @author Filipe
 */
@FunctionalInterface
public interface MemoriaObservador {
    
    public void valorAlterado(String novoValor);
    
}
