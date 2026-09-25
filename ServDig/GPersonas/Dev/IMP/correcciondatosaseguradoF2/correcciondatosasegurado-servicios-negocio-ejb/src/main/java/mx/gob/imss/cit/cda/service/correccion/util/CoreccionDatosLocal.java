package mx.gob.imss.cit.cda.service.correccion.util;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleCorreccionNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.OrigenInformacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ResumenCorrecion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoAclaracion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface CoreccionDatosLocal {
	
	
	
	
	/**
	 * Crear los datos necesarios para enviar 
	 * la solicitud al autorizador 
	 * 
	 * @param solicitud
	 * @param usuario
	 */
	Solicitud crearSolicitudPorAutorizar(Solicitud solicitud, String usuario, String asignado) ;
	ResumenCorrecion obtenerResumenorreccion(OrigenInformacion renapo,CorreccionDatos correccionDatos);
	List<DetalleNss> validaInfoOrigen(OrigenInformacion renapo,CorreccionDatos correccionDatos,Integer index);
	List<DetalleNss> setCubetaInformacion( OrigenInformacion renapo, CorreccionDatos correccionDatos,DetalleNss detalle);
	List<DetalleCorreccionNss> detalleCorreccion(OrigenInformacion renapo,OrigenInformacion cubetaInformacion);
	DetalleCorreccionNss compararDato(String renapoInfo,String cubetaInfo,String tipoFuente,String tipoDato);
	DetalleCorreccionNss setDatosDetalle(String fuente,String tipoDato,String cubetaInfo,String renapoInfo);
	DetalleCorreccionNss setDetalleOrigenDatos(String fuente,OrigenInformacion dato);
	String obtenerRegulaciones(TipoAclaracion tipoAclaracion);
	Solicitud crearSolicitudInformacionAdicional(Solicitud solicitud, String usuario, String asignado);
	
}
