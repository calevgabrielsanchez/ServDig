package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import static org.junit.Assert.assertTrue;

public class CampoTextoSIMEBuilderTest {

    private static final Logger log = LoggerFactory.getLogger(CampoTextoSIMEBuilderTest.class);

    @Test
    public void testCampoDateBuild() {
        CampoTextoSIME<?> campo = new CampoTextoSIMEBuilder()
            .withValor(new Date())
            .build();

        log.info("{}<--------- valor formateado", campo.generarValorFormateado());
        assertTrue(CampoTextoSIMEDate.class.isAssignableFrom(campo.getClass()));
    }

    @Test
    public void testCampoIntBuild() {
        CampoTextoSIME<?> campo = new CampoTextoSIMEBuilder()
            .withValor(42)
            .withLongitud(10)
            .build();

        log.info("{}<----------- valor formateado", campo.generarValorFormateado());
        assertTrue(CampoTextoSIMEInt.class.isAssignableFrom(campo.getClass()));
    }


    @Test
    public void testCampoLongBuild() {
        CampoTextoSIME<?> campo = new CampoTextoSIMEBuilder()
            .withValor(42L)
            .withLongitud(10)
            .build();

        log.info("{}<----------- valor formateado", campo.generarValorFormateado());
        assertTrue(CampoTextoSIMELong.class.isAssignableFrom(campo.getClass()));
    }

    @Test
    public void testCampoStringBuild() {
        CampoTextoSIME<?> campo = new CampoTextoSIMEBuilder()
            .withValor("FOOOOO")
            .withLongitud(12)
            .build();

        log.info("{}<----------- valor formateado", campo.generarValorFormateado());
        assertTrue(CampoTextoSIMEString.class.isAssignableFrom(campo.getClass()));
    }

}
