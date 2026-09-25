package mx.gob.imss.ctirss.delta.model.derechohabiente.dto;

import java.io.Serializable;
import java.util.Date;

public class DatosAfiliacionBeneficiarioDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9187821958055265354L;
	
	private Long cveParentesco;
	
	private String parentesco;
	
	private String situacion;
	
	private String prorroga;
	
	private Date fecIniVigenca;
	
	private Date fecFinVigencia;
	
	public Long getCveParentesco() {
		return cveParentesco;
	}
	public void setCveParentesco(Long cveParentesco) {
		this.cveParentesco = cveParentesco;
	}
	public String getParentesco() {
		return parentesco;
	}
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	public String getSituacion() {
		return situacion;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	public String getProrroga() {
		return prorroga;
	}
	public void setProrroga(String prorroga) {
		this.prorroga = prorroga;
	}
	public Date getFecIniVigenca() {
		return fecIniVigenca;
	}
	public void setFecIniVigenca(Date fecIniVigenca) {
		this.fecIniVigenca = fecIniVigenca;
	}
	public Date getFecFinVigencia() {
		return fecFinVigencia;
	}
	public void setFecFinVigencia(Date fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
	}

}
