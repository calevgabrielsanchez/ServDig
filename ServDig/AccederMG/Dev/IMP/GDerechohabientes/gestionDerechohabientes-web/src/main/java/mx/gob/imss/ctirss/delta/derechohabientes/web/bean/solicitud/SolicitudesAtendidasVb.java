package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudesAtendidasDto;

public class SolicitudesAtendidasVb implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  static final String SES_NAME="solicitudesAtendidasVb";

	SolicitudesAtendidasDto solicitudesAtendidasDto;

	/**
	 * @return the solicitudesAtendidasDto
	 */
	public SolicitudesAtendidasDto getSolicitudesAtendidasDto() {
		return solicitudesAtendidasDto;
	}

	/**
	 * @param solicitudesAtendidasDto the solicitudesAtendidasDto to set
	 */
	public void setSolicitudesAtendidasDto(
			SolicitudesAtendidasDto solicitudesAtendidasDto) {
		this.solicitudesAtendidasDto = solicitudesAtendidasDto;
	}

	
}
