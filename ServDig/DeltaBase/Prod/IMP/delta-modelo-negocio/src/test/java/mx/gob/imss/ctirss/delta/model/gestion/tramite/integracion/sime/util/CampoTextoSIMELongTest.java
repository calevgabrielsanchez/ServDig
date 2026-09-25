package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

import org.junit.Test;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.apache.commons.lang.StringUtils.repeat;

public class CampoTextoSIMELongTest {

    private static final Logger log = LoggerFactory.getLogger(CampoTextoSIMELongTest.class);

    private CampoTextoSIMELong campoTextoSIMELong;

    @Before
    public void setUp(){
        campoTextoSIMELong = new CampoTextoSIMELong();
    }

    @Test
    public void testResultContainsNumber() {
        assertTrue(campoTextoSIMELong.generarValorFormateado().contains("0"));
    }

    @Test
    public void testResultContainsValorCampo() {
        campoTextoSIMELong.setValorCampo(42L);
        assertTrue(campoTextoSIMELong.generarValorFormateado().contains("42"));
    }

    @Test
    public void testResultEsDeLongitudCaractres() {
        int longitud = 9;
        campoTextoSIMELong.setLongitud(longitud);
        campoTextoSIMELong.setValorCampo(1234567890L);
        String value = campoTextoSIMELong.generarValorFormateado();
        log.info("valor regresado CampoTextoSIMELong: {}", value);
        assertTrue("Longitud inesperada", value.length() == 9);
    }


    @Test
    public void testCadenaDeLongitudEscasaEsRellenadaConCeros() {
        int longitud = 11;
        long valorLong = 42L;
        int expectedZeros = longitud - String.valueOf(valorLong).length();
        campoTextoSIMELong.setLongitud(longitud);
        campoTextoSIMELong.setValorCampo(valorLong);

        String cadenaGenerada = campoTextoSIMELong.generarValorFormateado();
        String startCadena = repeat("0", expectedZeros);
        log.info("Ceros esperados al principio de la cadena {}", startCadena);
        log.info("cadena generada: {}", cadenaGenerada);

        assertFalse("Longitud insuficiente", longitud > cadenaGenerada.length());
        assertTrue("No contiene suficientes ceros", cadenaGenerada.contains(startCadena));
        assertFalse("Longitud excedida", longitud < cadenaGenerada.length());
    }
}
