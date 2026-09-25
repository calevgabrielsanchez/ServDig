package mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado;

import java.io.Serializable;

import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;

public class DatosGeneralesAseguradoMarcaAfiliatoria extends DerechohabienteDTO
		implements Serializable {

	private static final long serialVersionUID = -4571864490572481143L;

	public Boolean getCubeta() {
		return cubeta;
	}

	public void setCubeta(Boolean cubeta) {
		this.cubeta = cubeta;
	}

	public Boolean getBajaNss() {
		return bajaNss;
	}

	public void setBajaNss(Boolean bajaNss) {
		this.bajaNss = bajaNss;
	}
	
	private Boolean cubeta;
	private Boolean bajaNss;

}
