package mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MunicipioInegi implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5864353065109965449L;
	private String cveMunicipio;
	private String nomMunicipio;
	private String cveEntidadFed;
	public String getCveMunicipio() {
		return cveMunicipio;
	}
	public void setCveMunicipio(String cveMunicipio) {
		this.cveMunicipio = cveMunicipio;
	}
	public String getNomMunicipio() {
		return nomMunicipio;
	}
	public void setNomMunicipio(String nomMunicipio) {
		this.nomMunicipio = nomMunicipio;
	}
	public String getCveEntidadFed() {
		return cveEntidadFed;
	}
	public void setCveEntidadFed(String cveEntidadFed) {
		this.cveEntidadFed = cveEntidadFed;
	}
	
	
	
	
}
