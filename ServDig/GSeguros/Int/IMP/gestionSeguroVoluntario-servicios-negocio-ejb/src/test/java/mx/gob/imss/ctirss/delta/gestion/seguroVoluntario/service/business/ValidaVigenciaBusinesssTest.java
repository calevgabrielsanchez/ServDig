package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.Ambiente;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test.EjbLocator;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidaVigenciaBusinesssTest {

    @Test
    public void validaVigenciaSeguroFamiliarAplicaCuestionarioTest() {

        VigenciaSeguroFamiliar vigenciaSeguroFamiliar = new VigenciaSeguroFamiliar();

        vigenciaSeguroFamiliar.setClaveError(0);
        vigenciaSeguroFamiliar.setMensajeError("Consulta Exitosa");

        vigenciaSeguroFamiliar.setResultado(new ResultadoVigenciaSeguroFamiliar());
        vigenciaSeguroFamiliar.getResultado().setEstadoVigencia("0");
        vigenciaSeguroFamiliar.getResultado().setIndPension("0");
        vigenciaSeguroFamiliar.getResultado().setIndTrabajadorIMSS("0");
        vigenciaSeguroFamiliar.getResultado().setFecUltimaBajaObligatorio(null);


        //Si semanas > 52 y fecha menor a un año, aplicaCuestionario = false

        vigenciaSeguroFamiliar.getResultado().setFecUltimaBajaMod33("2018-07-31");
        vigenciaSeguroFamiliar.getResultado().setSemanasCotizadas("53");

        RespuestaValidacionTrabajador respuestaValidacionTrabajador = EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.LOCAL).validaVigenciaSeguroFamiliar(vigenciaSeguroFamiliar);
        assertNotNull(respuestaValidacionTrabajador);
        assertFalse(respuestaValidacionTrabajador.getAplicaCuestionario());


        //Si semanas > 52 y fecha mayor a un año, aplicaCuestionario = false

        vigenciaSeguroFamiliar.getResultado().setFecUltimaBajaMod33("2017-07-31");

        respuestaValidacionTrabajador = EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.LOCAL).validaVigenciaSeguroFamiliar(vigenciaSeguroFamiliar);
        assertNotNull(respuestaValidacionTrabajador);
        assertFalse(respuestaValidacionTrabajador.getAplicaCuestionario());


        //Si semanas < 52 y fecha menor a un año, aplicaCuestionario = false

        vigenciaSeguroFamiliar.getResultado().setFecUltimaBajaMod33("2018-07-31");
        vigenciaSeguroFamiliar.getResultado().setSemanasCotizadas("51");

        respuestaValidacionTrabajador = EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.LOCAL).validaVigenciaSeguroFamiliar(vigenciaSeguroFamiliar);
        assertNotNull(respuestaValidacionTrabajador);
        assertFalse(respuestaValidacionTrabajador.getAplicaCuestionario());



        //Si semanas < 52 y fecha mayor a un año, aplicaCuestionario = false

        vigenciaSeguroFamiliar.getResultado().setFecUltimaBajaMod33("2017-07-31");
        vigenciaSeguroFamiliar.getResultado().setSemanasCotizadas("51");

        respuestaValidacionTrabajador = EjbLocator.find(ValidaVigenciaRemote.class, Ambiente.LOCAL).validaVigenciaSeguroFamiliar(vigenciaSeguroFamiliar);
        assertNotNull(respuestaValidacionTrabajador);
        assertTrue(respuestaValidacionTrabajador.getAplicaCuestionario());


    }

}
