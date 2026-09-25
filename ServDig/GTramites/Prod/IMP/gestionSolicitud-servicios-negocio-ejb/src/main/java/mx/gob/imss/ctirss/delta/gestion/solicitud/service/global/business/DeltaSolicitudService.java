package mx.gob.imss.ctirss.delta.gestion.solicitud.service.global.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.global.interfaces.DeltaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.global.utility.DeltaSolicitudConversorLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.global.model.SolicitudTO;
import mx.gob.imss.ctirss.delta.global.model.TipoTramiteTO;
import mx.gob.imss.ctirss.delta.global.model.TramiteTO;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Stateless(name="deltaSolicitudService", mappedName="deltaSolicitudService")
public class DeltaSolicitudService implements DeltaSolicitudServiceRemote{
	
	@EJB
	SolicitudBusinessRemote solicitudService;
	@EJB
	DeltaSolicitudConversorLocal deltaSolicitudConversor;
	
	@Override
	public SolicitudTO consultarDatosGenerales(String folioSolicitud) throws SolicitudNoEncontradaException {
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioSolicitud);
		solicitud = solicitudService.consultarFolio(solicitud);
		
		SolicitudTO solicitudGlobalModel = deltaSolicitudConversor.transformarSolicitudAModeloGlobal(solicitud);
		
		
		return solicitudGlobalModel;
	}

	@Override
	public void testOne(TramiteTO tramite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testTwo(TipoTramiteTO tipoTramite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testThree(SolicitudTO solicitud) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarErrorProcesamiento(Long idSolicitud,String folio,
			String mensajeError) {
		solicitudService.reportarErrorProcesamiento(idSolicitud, folio, mensajeError);		
	}
	
	@Override
	public void actualizarMensajeNotificacion(String folio, String mensaje){
		solicitudService.actualizarMensajeNotificacion(folio, mensaje);
	}

}
