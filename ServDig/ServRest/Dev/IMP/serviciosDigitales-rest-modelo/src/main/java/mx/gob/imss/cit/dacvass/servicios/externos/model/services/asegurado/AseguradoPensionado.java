package mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.TipoPension;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;

@XmlRootElement
public class AseguradoPensionado extends DerechohabienteDTO implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2044053842931780278L;
	
	private TipoPension tipoPension;
	
	

	
	public TipoPension getTipoPension() {
		return tipoPension;
	}

	public void setTipoPension(TipoPension tipoPension) {
		this.tipoPension = tipoPension;
	}
	

}
