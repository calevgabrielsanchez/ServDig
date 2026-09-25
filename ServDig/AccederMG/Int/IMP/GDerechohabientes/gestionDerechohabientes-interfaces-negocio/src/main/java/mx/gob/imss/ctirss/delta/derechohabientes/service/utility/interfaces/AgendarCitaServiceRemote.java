package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;


import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.CitaSolicitud;



@Remote
public interface AgendarCitaServiceRemote {
	/**
	 * Regresa El Umf turno Y la Fecha de solicitud Disponibles de acuerdo al Domicilio 
	 * proporcionado
	 * 
	 * @param domicilio deve contener:
	 * 		domicilio.asentamiento.idAsentamiento
	 * 		domicilio.asentamiento.localidad.clave
	 * 		domicilio.asentamiento.localidad.municipio.clave
	 * 		domicilio.asentamiento.localidad.municipio.entidadFederativa.idEntidadFederativa
	 * @return solicitud con:
	 * 		solicitud.umfTurno
	 * 		solicitud.umfTurno.turno
	 * 		solicitud.solicitud.umfTurno.umf
	 * 		solicitud.fechaCita
	 * @throws DerechohabientesBusinessException 
	 * @throws Exception 
	 */
	public CitaSolicitud getCita(Domicilio domicilio) throws DerechohabientesBusinessException, Exception;
	public CitaSolicitud getCitaByUmf(Long idUmf) throws DerechohabientesBusinessException, Exception;
	List<Date> getFechasInhabiles() throws DerechohabientesBusinessException, Exception;
	
	
}