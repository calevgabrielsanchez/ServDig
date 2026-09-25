package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.junit.Test;

public class TipoTramiteTest extends GestionClasifEmpresasTestCaseBase {
	
	/**
	 * Actualiza el tipo de trámite de la solicitud
	 * @throws Exception
	 */
	@Test
	public void testActualizarTipoTramite() throws Exception {
		final Clase clase = new Clase();
		clase.setClave(2L);
		clase.setDescripcion("ClaseII");
		
		final Division division = new Division();
		division.setId(4L);
		division.setDescripcion("INDUSTRIAS DE TRANSFORMACIÓN");
		division.setNumDivision("3");
		
	    final Grupo grupo = new Grupo();
	    grupo.setId(26L);
	    grupo.setDescripcion("Fabricación, ensamble y/o reparación de maquinaria, equipo y sus partes; excepto los eléctricos");
	    grupo.setDivision(division);
	    grupo.setNumGrupo("36");
	    
		final Fraccion fraccion = new Fraccion();
		fraccion.setId(111L);
		fraccion.setDescripcion("Fabricación y/o ensamble de máquinas de coser, oficina, cómputo y sus partes");
		fraccion.setGrupo(grupo);
		fraccion.setClase(clase);
		fraccion.setPrimaSRT(BigDecimal.valueOf(1.1306));
		fraccion.setNumFraccion("364");
		
		final Object object = initialContext.lookup("solicitudPatronalBusiness");
		assertNotNull(object);
		assertTrue(object instanceof SolicitudServiceBusinessRemote);
		final SolicitudServiceBusinessRemote solicitudServiceBusinessRemote = (SolicitudServiceBusinessRemote) object;
		assertNotNull(solicitudServiceBusinessRemote);
		final Solicitud solicitud = solicitudServiceBusinessRemote.consultarSolicitudPorId(26001696L); //547, 1213, 1496
		assertNotNull(solicitud);
		//((TramiteSujetoObligado)solicitud.getTramites().get(0)).getSujetoObligado().getClasificacion().setFraccion(fraccion);
		solicitud.getTramites().get(0).getTipoTramite().setIdTipoTramite(1);
		solicitud.getTramites().get(0).getTipoTramite().setDescripcion("ALTA E INSCRIPCIÓN INICIAL EN EL SEGURO DE RIESGOS DE TRABAJO (ALTA PATRONAL)");
		solicitudServiceBusinessRemote.actualizarTramites(solicitud);
	}

}
