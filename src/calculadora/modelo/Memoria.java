package calculadora.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Filipe
 */
public class Memoria {

    private enum TipoComando {
        MUDARSINAL, ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA
    };

    private static final Memoria instancia = new Memoria();

    private TipoComando ultimaOperacao = null;
    private String textoAtual = "";
    private String textoBuffer = "";
    private boolean substituir = false;

    private final List<MemoriaObservador> observadores = new ArrayList<>();

    private Memoria() {

    }

    public static Memoria getInstancia() {
        return instancia;
    }

    public void adicionarObservador(MemoriaObservador observador) {
        observadores.add(observador);
    }

    public String getTextoAtual() {
        return textoAtual.isEmpty() ? "0" : textoAtual;
    }

    public void processarComando(String texto) {
        TipoComando tipoComando = detectarTipoComando(texto);

        if (null == tipoComando) {
            return;
        } else {
            switch (tipoComando) {
                case ZERAR -> {
                    textoAtual = "";
                    textoBuffer = "";
                    substituir = false;
                    ultimaOperacao = null;
                }
                case NUMERO, VIRGULA -> {
                    textoAtual = substituir ? texto : textoAtual + texto;
                    substituir = false;
                }
                case MUDARSINAL ->
                    textoAtual = mudarSinal();
                default -> {
                    substituir = true;
                    textoAtual = obterResultadoOperacao();
                    textoBuffer = textoAtual;
                    ultimaOperacao = tipoComando;
                }
            }
        }

        observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
    }

    private String mudarSinal() {
        double resultado = Double.parseDouble(textoAtual.replace(",", "."));
        resultado *= -1;
        String resultadoString = Double.toString(resultado).replace(".", ",");
        boolean inteiro = resultadoString.endsWith(",0");
        return inteiro ? resultadoString.replace(",0", "") : resultadoString;
    }

    private String obterResultadoOperacao() {
        if (ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
            return textoAtual;
        }

        double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
        double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));
        double resultado = 0;

        if (null != ultimaOperacao) {
            switch (ultimaOperacao) {
                case SOMA ->
                    resultado = numeroBuffer + numeroAtual;
                case SUB ->
                    resultado = numeroBuffer - numeroAtual;
                case MULT ->
                    resultado = numeroBuffer * numeroAtual;
                case DIV ->
                    resultado = numeroBuffer / numeroAtual;
                default -> {
                }
            }
        }

        double arredondado = resultado;
        arredondado *= (Math.pow(10, 2));
        arredondado = Math.ceil(arredondado);
        arredondado /= (Math.pow(10, 2));
        String resultadoString = Double.toString(arredondado).replace(".", ",");
        boolean inteiro = resultadoString.endsWith(",0");
        return inteiro ? resultadoString.replace(",0", "") : resultadoString;
    }

    private TipoComando detectarTipoComando(String texto) {
        if (textoAtual.isEmpty() && "0".equals(texto) && textoAtual.startsWith("0,")) {
            return null;
        }
        try {
            Integer.parseInt(texto);
            return TipoComando.NUMERO;
        } catch (NumberFormatException e) {

            switch (texto) {
                case "AC":
                    return TipoComando.ZERAR;

                case "/":
                    return TipoComando.DIV;

                case "*":
                    return TipoComando.MULT;

                case "-":
                    return TipoComando.SUB;

                case "+":
                    return TipoComando.SOMA;

                case ",":
                    if (!textoAtual.contains(",")) {
                        return TipoComando.VIRGULA;
                    }

                case "=":
                    return TipoComando.IGUAL;

                case "±":
                    return TipoComando.MUDARSINAL;

                default:
                    return null;
            }
        }
    }

}
