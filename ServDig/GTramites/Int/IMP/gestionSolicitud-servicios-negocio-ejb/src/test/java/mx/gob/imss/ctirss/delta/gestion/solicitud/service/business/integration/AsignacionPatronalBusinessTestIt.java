package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import java.util.Date;
import java.util.Calendar;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AsignacionPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.asignacion.MovimientoAsignacionType;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.asignacion.util.MovimientoAsignacionTypeBuilder;
import org.junit.Test;
import org.apache.commons.lang.time.DateUtils;

public class AsignacionPatronalBusinessTestIt {

    private AsignacionPatronalBusinessRemote ejb = EjbLocator.getAsignacionPatronalBusiness();

    @Test
    public void testEncolarMovimientoAsignacion() {
        Date date = new Date();
        date = DateUtils.setMonths(date, Calendar.FEBRUARY);
        date = DateUtils.setDays(date, 25);
        MovimientoAsignacionType movimientoSINDO = new MovimientoAsignacionTypeBuilder()
            .withCizOrigen(1)
            .withDelOrigen(15)
            .withSubdelOrigen(54)
            .withCodEnvio(1)
            //.withCodRetorno(3)
            //.withCondicion(4)
            .withTpMovto(1)
            //.withOpcionMovto44(0)
            .withNss("0314629003")
            .withDigver(0)
            .withNombre("LYDIE MARIE-CLAUDE")
            .withPrimerApellido("LE MAITRE EP. GALA")
            .withSegundoApellido("")
            .withSexo(2)
            .withMesNac(6)
            .withLugarNac(35)
            .withIdUsuario("DEAS091")
            .withUmf(93)
            .withOrigen(1)
            .withFechaMovto(date)
            .withCurp("MAXL620606MNETXY03")
            .build();

        ejb.encolarMovimientoAsignacion(movimientoSINDO);

    }

}
