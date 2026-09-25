package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces;

import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

public interface ReporteBusinessRemote {
	
	byte[] getCredencialNSS(String nss);
	
	byte[] getCredencialNSS(AsignacionNSS asignacion);
	
	byte[] getComprobanteAsignacionSimpleConQR(Solicitud solicitud);
	
	/**
	 * Metodo para obtener el valor vacio
	 * @param folioSolicitud
	 * @return
	 */
	byte[] getComprobanteVacio();
	/**
	 * ESTE METODO SOLO LO USAN LOS TEST
	 * 
	 * @param folioSolicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 */
	byte[] getComprobanteOperacion(Long folioSolicitud)
			throws SolicitudNoEncontradaException;
	
	byte[] getComprobanteAsignacion(Solicitud solicitud)
	throws SolicitudNoEncontradaException;
	
	byte[] getComprobanteRecuperacion(Solicitud solicitud)
	throws SolicitudNoEncontradaException;

	Map<String, Object> getReportModel(Long folioSolicitud)
			throws SolicitudNoEncontradaException;
	
	Map<String, Object> getReportModelRecuperado(TramiteAsegurado tramiteAsegurado,Solicitud solicitud);

	byte[] getComprobanteInterno(Long folioSolicitud)
	throws SolicitudNoEncontradaException;
	/**
	 * 191807 200912 Este metodo es similar al de arriba (el cual procesa los
	 * internos o por ventanilla), solo que este procesa el reporte PDF externo
	 * (por internet)
	 * 
	 * @param folioSolicitud
	 * @return
	 * @throws SolicitudNoEncontradaException
	 */
	Map<String, Object> getReportModelExterno(Long folioSolicitud)
			throws SolicitudNoEncontradaException;
}
