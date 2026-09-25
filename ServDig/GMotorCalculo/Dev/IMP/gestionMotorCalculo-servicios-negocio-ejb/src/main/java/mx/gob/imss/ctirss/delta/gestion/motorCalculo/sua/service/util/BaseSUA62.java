/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase utilitaria para calcular la cdificacion de valores en base 62 SUA
 * 
 * @author NOVUTECK1
 * 
 */
public abstract class BaseSUA62 {

    /**
     * Alfabeto que se utiliza para la codificacion de variables
     */
    private static final String ALFABETO = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * Arreglo de caracteres con el alfabeto de la codificacion
     */
    private static final char[] ALFABETO_CHAR = ALFABETO.toCharArray();
    /**
     * Base de la codificacion
     */
    private static final int BASE = 62;
    /**
     * Maximo de caracteres que se obtiene en la codificacion SUA
     */
    private static final int MAX_CARACTERES = 3;
    /**
     * Numero de decimales en la cantidad a codificar
     */
    private static final int NUM_DECIMALES = 2;
    /**
     * Cifra de control para obtener las cantidades a dos decimales
     */
    private static final BigDecimal PARTE_DECIMAL = new BigDecimal(100);

    /**
     * Codifica una cantidad a representacion en base 62 SUA
     * 
     * @param cantidad El valor numerico a codificar
     * @return cadena representativa de la canidad en baseSua62
     */
    public static final String codifica(BigDecimal cantidad) {
        BigDecimal valor = cantidad.setScale(NUM_DECIMALES, RoundingMode.HALF_UP);
        int convertir = valor.multiply(PARTE_DECIMAL).intValue();
        StringBuilder valorCodificado = new StringBuilder();
        for (int i = 1; i <= MAX_CARACTERES; i++) {
            int baseCod = (int) Math.pow(BASE, (MAX_CARACTERES - i));
            char caracter = ALFABETO_CHAR[(int) (convertir / baseCod)];
            valorCodificado.append(caracter);
            convertir = convertir % baseCod;
        }
        return valorCodificado.toString();
    }

    /**
     * decodifica un valor en base 62 sua a su representacion decimal
     * 
     * @param valor Cadena codificada en baseSUa62
     * @return LA candidad que se represento en la cadena codificada
     */
    public static final BigDecimal decodifica(String valor) {
        int cantidad = 0;
        for (int i = 1; i <= MAX_CARACTERES; i++) {
            int posicion = ALFABETO.indexOf(valor.substring(i - 1, i));
            int baseCod = (int) Math.pow(BASE, (MAX_CARACTERES - i));
            cantidad = cantidad + (posicion * baseCod);
        }
        BigDecimal cantidadTotal = new BigDecimal(cantidad);
        return cantidadTotal.divide(PARTE_DECIMAL);
    }

}
