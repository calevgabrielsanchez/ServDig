package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

import org.junit.Test;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CampoTextoSIMEStringTest {

    private static final Logger log = LoggerFactory.getLogger(CampoTextoSIMEStringTest.class);

    private CampoTextoSIMEString campoTextoSIMEString;

    @Before
    public void setUp(){
        campoTextoSIMEString = new CampoTextoSIMEString();
    }

    @Test
    public void testLongitudSeRespetaCuandoGeneraCadena() {
        campoTextoSIMEString.setLongitud(10);
        assertTrue(campoTextoSIMEString.generarValorFormateado().length() == 10);
        campoTextoSIMEString.setValorCampo("BAR");
        campoTextoSIMEString.setLongitud(42);
        assertTrue(campoTextoSIMEString.generarValorFormateado().length() == 42);
    }

    @Test
    public void testCadenaGeneradaContieneValorCampo() {
        int longitud = 50;
        campoTextoSIMEString.setValorCampo("FOO");
        campoTextoSIMEString.setLongitud(longitud);
        String val = campoTextoSIMEString.generarValorFormateado();
        log.info("--->{}<---", val);
        assertTrue(val.length() == longitud);
        assertTrue(val.contains("FOO"));
        assertTrue(val.matches("^FOO.+"));
    }

    @Test
    public void testCadenaLargaEsCortadaALongitudCaracteres() {
        campoTextoSIMEString.setLongitud(2);
        campoTextoSIMEString.setValorCampo("XYZ");

        String value = campoTextoSIMEString.generarValorFormateado();
        log.info("testCadenaLargaEsCortadaALongitudCaracteres: {}", value);

        assertFalse(value.equals("XYZ"));
        assertTrue(value.equals("XY"));
    }

}
