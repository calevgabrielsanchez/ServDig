package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

import java.util.Date;
import org.junit.Test;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CampoTextoSIMEDateTest {

    private static final Logger log = LoggerFactory.getLogger(CampoTextoSIMEDateTest.class);

    private CampoTextoSIMEDate campoTextoSIMEDate;

    @Before
    public void setUp(){
        campoTextoSIMEDate = new CampoTextoSIMEDate();
    }

    @Test(expected = RuntimeException.class)
    public void testSetLongitudThrowsRuntimeException() {
        campoTextoSIMEDate.setLongitud(42);
    }

    @Test
    public void testNullDevuelveCeros() {
        String expected = repeat("0", campoTextoSIMEDate.getLongitud());
        assertTrue(campoTextoSIMEDate.generarValorFormateado().equals(expected));
    }

    @Test
    public void testCurrentDateDevuelveNumeros() {
        String notExpected = repeat("0", campoTextoSIMEDate.getLongitud());
        campoTextoSIMEDate.setValorCampo(new Date());
        String val = campoTextoSIMEDate.generarValorFormateado();
        log.info("******************************");
        log.info(val);
        assertFalse(val.equals(notExpected));
        assertTrue(val.matches("\\d{8}"));
    }

    @Test
    public void testCurrentDateContainsYearMonthAndDate() {
        campoTextoSIMEDate.setValorCampo(new Date());
        String val = campoTextoSIMEDate.generarValorFormateado();

        Date today = new Date();

        String year = String.format("%tY", today);
        log.info("year: {}", year);
        String month = String.format("%tm", today);
        log.info("month: {}", month);
        String date = String.format("%td", today);
        log.info("date: {}", date);
        log.info("Checando formato de fecha: {}", val);
        assertTrue(val.contains(year));
        assertTrue(val.contains(month));
        assertTrue(val.contains(date));
    }

}
