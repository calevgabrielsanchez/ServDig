package mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EntidadFederativa;

@XmlRootElement
public class MunicipioImss implements Serializable  {


	/**
	 * 
	 */
	private static final long serialVersionUID = -990470521917520428L;
	private Long idMunicipioImss;
	private String cveMunicipioImss;
	private String nomMunicipioImss;
	private EntidadFederativa entidadFederativa;

	
	public Long getIdMunicipioImss() {
		return idMunicipioImss;
	}
	public void setIdMunicipioImss(Long idMunicipioImss) {
		this.idMunicipioImss = idMunicipioImss;
	}
	public String getCveMunicipioImss() {
		return cveMunicipioImss;
	}
	public void setCveMunicipioImss(String cveMunicipioImss) {
		this.cveMunicipioImss = cveMunicipioImss;
	}
	public String getNomMunicipioImss() {
		return nomMunicipioImss;
	}
	public void setNomMunicipioImss(String nomMunicipioImss) {
		this.nomMunicipioImss = nomMunicipioImss;
	}
	public EntidadFederativa getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(EntidadFederativa entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
		
	
	
}
