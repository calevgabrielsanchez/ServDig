package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import java.io.Serializable;
import java.util.Date;

public class DatosAfiliacionBeneficiarioEstDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9187821958055265354L;
	
	private Date fecIniCicloEsc;
	
	private Date fecFinCicloEsc;
	
	private String nivelEstudios;
	
	private String detNivelEstudios;
	
	private String institucionEducativa;
	
	private String cveInstitucionEducativa;
	
	private String numIncorporacionSEP;
	
	private String gradoEscolar;
	
	private Date fecExpedicion;

	public Date getFecIniCicloEsc() {
		return fecIniCicloEsc;
	}

	public void setFecIniCicloEsc(Date fecIniCicloEsc) {
		this.fecIniCicloEsc = fecIniCicloEsc;
	}


	public Date getFecFinCicloEsc() {
		return fecFinCicloEsc;
	}

	public void setFecFinCicloEsc(Date fecFinCicloEsc) {
		this.fecFinCicloEsc = fecFinCicloEsc;
	}

	public String getNivelEstudios() {
		return nivelEstudios;
	}

	public void setNivelEstudios(String nivelEstudios) {
		this.nivelEstudios = nivelEstudios;
	}

	public String getDetNivelEstudios() {
		return detNivelEstudios;
	}

	public void setDetNivelEstudios(String detNivelEstudios) {
		this.detNivelEstudios = detNivelEstudios;
	}

	public String getInstitucionEducativa() {
		return institucionEducativa;
	}

	public void setInstitucionEducativa(String institucionEducativa) {
		this.institucionEducativa = institucionEducativa;
	}

	public String getCveInstitucionEducativa() {
		return cveInstitucionEducativa;
	}

	public void setCveInstitucionEducativa(String cveInstitucionEducativa) {
		this.cveInstitucionEducativa = cveInstitucionEducativa;
	}

	public String getNumIncorporacionSEP() {
		return numIncorporacionSEP;
	}

	public void setNumIncorporacionSEP(String numIncorporacionSEP) {
		this.numIncorporacionSEP = numIncorporacionSEP;
	}

	public String getGradoEscolar() {
		return gradoEscolar;
	}

	public void setGradoEscolar(String gradoEscolar) {
		this.gradoEscolar = gradoEscolar;
	}

	public Date getFecExpedicion() {
		return fecExpedicion;
	}

	public void setFecExpedicion(Date fecExpedicion) {
		this.fecExpedicion = fecExpedicion;
	}
	

}
