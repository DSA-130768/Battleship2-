// 130768 - Simão Sousa
package battleship;

/**
 * Naming violation fixed: the method name now follows lowerCamelCase.
 */
public class NamingDemo {

    public int calcularPontos(int hits, int misses) {
        return hits * 10 - misses;
    }
}
