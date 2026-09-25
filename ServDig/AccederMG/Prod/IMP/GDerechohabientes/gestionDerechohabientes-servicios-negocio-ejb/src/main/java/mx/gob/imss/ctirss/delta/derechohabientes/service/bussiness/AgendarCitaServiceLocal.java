package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;


import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;

@Local
public interface AgendarCitaServiceLocal {
	/**
	 * Regresa El Umf turno Y la Fecha de solicitud Disponibles de acuerdo al Domicilio 
	 * proporcionado
	 * 
	 * @param domicilio deve contener:
	 * 		domicilio.asentamiento.clave
	 * 		domicilio.asentamiento.localidad.clave
	 * 		domicilio.asentamiento.localidad.municipio.clave
	 * 		domicilio.asentamiento.localidad.municipio.entidadFederativa.clave
	 * @return solicitud con:
	 * 		solicitud.umfTurno
	 * 		solicitud.umfTurno.turno
	 * 		solicitud.solicitud.umfTurno.umf
	 * 		solicitud.fechaCita
	 */
	public CitaSolicitud getCita(Domicilio domicilio) throws DerechohabientesBusinessException, Exception;

	public CitaSolicitud getCitaByUmf(Long idUmf) throws DerechohabientesBusinessException, Exception;
	List<Date> getFechasInhabiles() throws DerechohabientesBusinessException,Exception;	
}