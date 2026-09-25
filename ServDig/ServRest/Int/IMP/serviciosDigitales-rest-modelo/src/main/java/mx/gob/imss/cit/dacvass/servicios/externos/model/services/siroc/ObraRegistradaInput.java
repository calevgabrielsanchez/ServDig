package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObraRegistradaInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3902991203337871852L;

	public Integer getIdDelegacion() {
		return idDelegacion;
	}

	public void setIdDelegacion(Integer idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	private Integer idDelegacion;
	private Integer idMunicipio;

}
