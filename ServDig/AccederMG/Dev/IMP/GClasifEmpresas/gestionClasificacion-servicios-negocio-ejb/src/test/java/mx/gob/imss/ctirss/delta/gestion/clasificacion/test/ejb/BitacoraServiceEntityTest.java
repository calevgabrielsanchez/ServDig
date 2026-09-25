package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacora;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosBitacoras;

import org.junit.Test;

public class BitacoraServiceEntityTest extends GestionClasifEmpresasTestCaseBase {
	
	/**
	 * Ejecuta la prueba unitaria del proceso de asignación de solicitud
	 * @throws Exception
	 */
	@Test
	public void testAsignarSolicitud() throws Exception {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final FiltrosBitacoras filtrosBitacoras = new FiltrosBitacoras();
		filtrosBitacoras.setCveIdAnalisis(25L);
		filtrosBitacoras.setDelegacion(39L);
		filtrosBitacoras.setEsInscripcionInicial(Boolean.FALSE);
		filtrosBitacoras.setPeriodoInicio(simpleDateFormat.parse("2012-08-06"));
		filtrosBitacoras.setPeriodoFin(simpleDateFormat.parse("2012-08-06"));
		filtrosBitacoras.setSubDelegacion(138L);
		filtrosBitacoras.setUsuario("G_CLASIF");
		
		final Object object = initialContext.lookup("bitacoraEntityLocal");
		assertNotNull(object);
		assertTrue(object instanceof BitacoraServiceEntityLocal);
		final BitacoraServiceEntityLocal bitacoraServiceEntityLocal = (BitacoraServiceEntityLocal) object;
		assertNotNull(bitacoraServiceEntityLocal);
		final List<ElementoBitacora> registros = bitacoraServiceEntityLocal.buscaRegistros(filtrosBitacoras);
		System.out.println("Número de registros: " + registros.size());
	}

}
