package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;

import java.util.ArrayList;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.GestionClasifEmpresasTestCaseBase;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

import org.junit.Test;

public class CancelarAnalisisTest extends GestionClasifEmpresasTestCaseBase{
	@Test
	public void testCancelaAnalisisPorRPC() throws Exception{
		Object object = initialContext.lookup("solicitudServiceBusiness");
		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud sol=new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		sol.setSolicitudId(51L);
		sol.setTramites(new ArrayList<Tramite>());
		Tramite t=new Tramite();
		t.setTipoTramite(new TipoTramite());
		t.getTipoTramite().setIdTipoTramite(11);
		sol.getTramites().add(t);
		
		assertNotNull(object);
		assertTrue(object instanceof SolicitudServiceBusinessRemote);
		final SolicitudServiceBusinessRemote solServiceBusinessRemote=(SolicitudServiceBusinessRemote)object;
		solServiceBusinessRemote.cancelarAnalisisPorRegistroPatronal("T4039450", EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave()
				, sol);
	}
}
