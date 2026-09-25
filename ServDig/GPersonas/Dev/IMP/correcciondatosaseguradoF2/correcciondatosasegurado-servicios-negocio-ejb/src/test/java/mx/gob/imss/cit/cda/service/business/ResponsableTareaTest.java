package mx.gob.imss.cit.cda.service.business;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.base.Ambiente;
import mx.gob.imss.base.EjbLocator;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

import org.junit.Test;

public class ResponsableTareaTest {

	
	
	@Test
	public void confirmarCorreccionDatosTest(){
		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudIMSS = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		  
		  solicitudIMSS.setSolicitudId(Long.parseLong("40202931"));
		  
		  EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		  //estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		  estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
		  
		  solicitudIMSS.setEstadoSolicitud(estadoSolicitud);
		  
		  List <Tramite> lstTramite = new ArrayList<Tramite>();
		  Tramite tramite = new Tramite();
		  EstadoTramite estadoTramite = new EstadoTramite();
		  
		  //estadoTramite.setDescripcion(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getDescripcion());
		  estadoTramite.setDescripcion(EstadoTramiteEnum.CANCELADO.getDescripcion());
		  estadoTramite.setIdEstadoTramitePersona(EstadoSolicitudEnum.CANCELADA.getCodigo());
		  //estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo());
		  tramite.setTramiteId(Long.parseLong("44924897"));
		  tramite.setEstadoTramite(estadoTramite);
		  lstTramite.add(tramite);
		  solicitudIMSS.setTramites(lstTramite);
		  
		  try {
			  //EjbLocator.find(ResponsableTareaRemote.class, Ambiente.PRUEBAS).cancelarTarea(solicitudIMSS, null,null,Boolean.FALSE);
			//respoTarea.avanzarTareaResponsable(solicitudIMSS, "42");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
