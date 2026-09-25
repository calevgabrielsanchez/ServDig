package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoAsignacionSIMEBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util.MovimientoAsignacionSIMETypeBuilder;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.MovimientoAsignacionSIMEType;

public class MovimientoAsignacionSIMEBusinessTest {
    
    private static final Logger log = LoggerFactory.getLogger(MovimientoAsignacionSIMEBusinessTest.class);

    private MovimientoAsignacionSIMEBusinessRemote ejb;

    @Before
    public void setUp(){
        ejb = EjbLocator.getMovimientoAsignacionSIMEBusiness();
    }

    @Test
    public void testProcesarMovimientoAsignacionSIME() {
        log.info("testing procesarMovimientosAsignacionSIME");
        String result = ejb.procesarMovimientoAsignacionSIME(createMovimiento());
        log.info("result --> {}", result);
    }

    @Test
    public void testProcesarMovimientosAsignacionSIME() {
        List<String> result = ejb.procesarMovimientosAsignacionSIME(
                Arrays.<MovimientoAsignacionSIMEType>asList(new MovimientoAsignacionSIMEType[]{
                    createMovimiento(),
                    createMovimiento(),
                    createMovimiento()
                }));

        for (String strMovimiento : result) {
            log.info(strMovimiento);
        }
    }

    private MovimientoAsignacionSIMEType createMovimiento() {
        return new MovimientoAsignacionSIMETypeBuilder()
                .withNss(777777789)
                .withRegistroPatronal("xxxxxxxxxxx")
                .withPrimerApellido("Doe")
                .withSegundoApellido("Doe")
                .withNombre("John")
                .withFechaMovimiento(new Date())
                .withTipoTrabajor(2)
                .withTipoMovimiento(8)
                .withCurp("DOXX800808HMXXXX05")
                .withIdentificadorFormato(9)
                .build();
    }
}
