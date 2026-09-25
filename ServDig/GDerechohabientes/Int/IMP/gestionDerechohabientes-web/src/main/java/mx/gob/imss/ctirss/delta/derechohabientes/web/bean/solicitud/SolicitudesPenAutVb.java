package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudesPendientesAutorizacionDto;

public class SolicitudesPenAutVb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 250790091683766894L;
	public  static final String SES_NAME="solicitudesPenAutVb";

	SolicitudesPendientesAutorizacionDto solicitudesPendientesAutorizacionDto;


	public SolicitudesPendientesAutorizacionDto getSolicitudesPendientesAutorizacionDto() {
		return solicitudesPendientesAutorizacionDto;
	}

	public void setSolicitudesPendientesAutorizacionDto(
			SolicitudesPendientesAutorizacionDto solicitudesPendientesAutorizacionDto) {
		this.solicitudesPendientesAutorizacionDto = solicitudesPendientesAutorizacionDto;
	}
	
}
