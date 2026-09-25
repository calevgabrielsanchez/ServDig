/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.util;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.util.BaseSUA62;

import org.junit.Assert;
import org.junit.Test;

/**
 * Prueba unitaria para la codificacion de valores segun la base SUA
 * @author NOVUTECK1
 *
 */
public class BaseSUA62Test {
    
    /**
     * Cantidad de control para la codificacion
     */
    private static final BigDecimal CANTIDAD = new BigDecimal("587.99");

    /**
     * Cadena de control para la codificacion SUE
     */
    private static final String CADENA = "FIN";
    
    @Test
    public void testCodificaValor() {
        new BaseSUA62() {
        };
        String cadenaCodificada = BaseSUA62.codifica(CANTIDAD);
        Assert.assertEquals("La cadena generada debe ser igual a la cadena de control", 
                CADENA, cadenaCodificada);
    }
    
    @Test
    public void testDecodificaValor() {
        BigDecimal cantidadDecodificada = BaseSUA62.decodifica(CADENA);
        Assert.assertEquals("La cantidad generada debe ser igual a la cantidad de control", 
                CANTIDAD, cantidadDecodificada);
    }
}
