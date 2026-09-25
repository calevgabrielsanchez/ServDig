package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

import org.junit.Test;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.apache.commons.lang.StringUtils.repeat;

public class CampoTextoSIMEIntTest {

    private static final Logger log = LoggerFactory.getLogger(CampoTextoSIMEIntTest.class);

    private CampoTextoSIMEInt campoTextoSIMEInt;

    @Before
    public void setUp() {
        campoTextoSIMEInt = new CampoTextoSIMEInt();
    }

    @Test
    public void testNullValorCampoDevuelve0() {
        int longitud = 11;
        campoTextoSIMEInt.setLongitud(longitud);
        String valor = campoTextoSIMEInt.generarValorFormateado();
        assertTrue(valor.contains("0"));
        assertTrue(valor.length() == longitud);
    }

    @Test
    public void testCadenaResultadoContieneEntero() {
        int valorCampo = 42;
        campoTextoSIMEInt.setValorCampo(valorCampo);
        String value = campoTextoSIMEInt.generarValorFormateado();
        log.info("cadena obtenida {}", value);
        assertTrue(value.endsWith(String.valueOf(valorCampo)));
    }

    @Test
    public void testValorCampoLargoEsRecortado() {
        int valorCampo = 1777567;
        int longitud = 5;
        campoTextoSIMEInt.setValorCampo(valorCampo);
        campoTextoSIMEInt.setLongitud(longitud);
        String valorFormateado = campoTextoSIMEInt.generarValorFormateado();
        log.info("Valor devuelto campotextoSIMEInt: {}", valorFormateado);
        assertTrue("Longitud no checa", valorFormateado.length() == longitud);
        assertTrue("inicio de cadena inesperado", valorFormateado.startsWith("1"));
        assertTrue("fin de cadena inesperado", valorFormateado.endsWith("5"));
    }


    @Test
    public void testCadenaConLongitudEscasaEsrellenadaConCeros() {
        int longitud = 6;
        int valorInt = 42;
        int expectedZeros = longitud - String.valueOf(valorInt).length();
        campoTextoSIMEInt.setLongitud(longitud);
        campoTextoSIMEInt.setValorCampo(valorInt);

        String cadenaGenerada = campoTextoSIMEInt.generarValorFormateado();
        String startCadena = repeat("0", expectedZeros);
        log.info("Ceros esperados al principio de la cadena {}", startCadena);
        log.info("cadena generada: {}", cadenaGenerada);

        assertFalse("Longitud insuficiente", longitud > cadenaGenerada.length());
        assertTrue("No contiene suficientes ceros", cadenaGenerada.contains(startCadena));
        assertFalse("Longitud excedida", longitud < cadenaGenerada.length());

    }

}
