package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;

public class MedioContactoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3656371115784795230L;

	private TipoMedioContacto tipoMedioContacto;

	private String descMedioContacto;

	public TipoMedioContacto getTipoMedioContacto() {
		return tipoMedioContacto;
	}

	public void setTipoMedioContacto(TipoMedioContacto tipoMedioContacto) {
		this.tipoMedioContacto = tipoMedioContacto;
	}

	public String getDescMedioContacto() {
		return descMedioContacto;
	}

	public void setDescMedioContacto(String descMedioContacto) {
		this.descMedioContacto = descMedioContacto;
	}


	
}
