package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import java.util.Date;
import javax.naming.NamingException;
import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.util.MovimientoPatronalTypeBuilder;

public class MovimientoPatronalBusinessTest {

    private static final Logger log = LoggerFactory.getLogger(MovimientoPatronalBusinessTest.class);
    private MovimientoPatronalBusinessRemote movimientoPatronalBusinessRemote;

    @Before
    public void setUp() throws NamingException {
        movimientoPatronalBusinessRemote = EjbLocator.getMovimientoPatronalBusiness();
    }

    @Ignore
    @Test
    public void testSendingMessage() {
        movimientoPatronalBusinessRemote.enviarModificacionPatronal(new MovimientoPatronalTypeBuilder()
                .withCiz(2)
                .withDelegacionOrigen(42)
                .withSubdelegacionOrigen(7)
                .withTipoMovimiento(4)
                .withNumeroFolio("42")
                .withRegistroPatronal("11111107")
                .withDigitoVerificador(1)
                .withFechaMovimiento(new Date())
                .withGiro("withGiro")
                .withClase(1)
                .withFraccion(1)
                .withDivision(1)
                .withGrupo(1)
                .withPrima(12.13)
                .withCausa(1)
                .withNombrePatron("PATRON TAQUERO")
                .withNombrePatronalC("PATRON TAQUERO INC.")
                .withClaveMunicipio("42")
                .withDomicilioPatron("MEDELLIN 42")
                .withCodigoPostal("06700")
                .withLocalidad("ROMA NORTE")
                .build());
        movimientoPatronalBusinessRemote.enviarModificacionPatronal(new MovimientoPatronalTypeBuilder()
                .withCiz(2)
                .withDelegacionOrigen(42)
                .withSubdelegacionOrigen(7)
                .withTipoMovimiento(5)
                .withNumeroFolio("42")
                .withRegistroPatronal("11111107")
                .withDigitoVerificador(1)
                .withFechaMovimiento(new Date())
                .withGiro("withGiro")
                .withClase(1)
                .withFraccion(1)
                .withDivision(1)
                .withGrupo(1)
                .withPrima(12.13)
                .withCausa(1)
                .withNombrePatron("PATRON TAQUERO")
                .withNombrePatronalC("PATRON TAQUERO INC.")
                .withClaveMunicipio("42")
                .withDomicilioPatron("MEDELLIN 42")
                .withCodigoPostal("06700")
                .withLocalidad("ROMA NORTE")
                .build());
    }
}

