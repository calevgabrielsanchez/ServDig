package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import org.junit.Test;
import org.junit.Before;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util.MovimientoAsignacionSIMETypeBuilder;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.MovimientoAsignacionSIMEType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MovimientoAsignacionSIMETest {

    private MovimientoAsignacionSIMEBusiness service;

    @Before
    public void setUp(){
        service = new MovimientoAsignacionSIMEBusiness();
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testFoo() {
        List<String> file = service.procesarMovimientosAsignacionSIME(new ArrayList() {{
                add(createMovimiento());
                add(createMovimiento());}});

        String resumen = file.get(file.size() - 1);
        assertEquals("elementos no son n+1", 3, file.size());
        //Asteriscos               	 AN 	 1   a 13  	 13
        assertTrue(resumen.matches("^\\*{13}(?!\\*).*"));
        //Filler                   	 AN 	 14  a 56  	 43
        assertTrue(resumen.matches("^.{13} {43}(?! ).*"));
        //Total de registros       	 N  	 57  a 62  	 6
        assertTrue(resumen.matches("^.{56}\\d{6}(?!\\d).*"));
        //Filler                   	 A  	 63  a 133 	 71
        assertTrue(resumen.matches("^.{62} {71}(?! ).*"));
        //Núm.de Guía              	 N  	 134 a 138 	 5
        assertTrue(resumen.matches("^.{133}\\d{5}(?!\\d).*"));
        //Filler                   	 A  	 139 a 167 	 29
        assertTrue(resumen.matches("^.{138} {29}(?! ).*"));
        //Identificador de formato 	 N  	 168       	 1
        assertTrue(resumen.matches("^.{167}\\d$"));

        assertEquals("resumen no checa en longitud", resumen.length(), file.get(0).length());

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
                .withGuia(555555)
                .withTipoMovimiento(8)
                .withCurp("DOXX800808HMXXXX05")
                .withIdentificadorFormato(9)
                .build();
    }
}
