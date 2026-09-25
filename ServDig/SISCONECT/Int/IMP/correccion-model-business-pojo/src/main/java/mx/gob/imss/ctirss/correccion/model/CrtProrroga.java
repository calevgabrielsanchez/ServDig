package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrtProrroga;


@Entity
@Table(name="CRT_PRORROGA")
public class CrtProrroga extends AbstractCrtProrroga{

	@Transient
	private String fecInicio;
	@Transient
	private String fecFinal;
	@Transient
	private String nuFolio;
	@Transient
	private String razonSocial;
	@Transient
	private String regPatronal;
	@Transient
	private String tipoCorreccion;
	@Transient
	private String fecPeriodoIni;
	@Transient
	private String fecPeriodoFin;
	@Transient
	private String fecLimite;
	@Transient
	private String status;
	
	
	@Transient
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	private List<CrcStatus> lstStatus;
	
	@Transient	
	public String getNuFolio() {
		return nuFolio;
	}

	public void setNuFolio(String nuFolio) {
		this.nuFolio = nuFolio;
	}
	@Transient	
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	@Transient	
	public String getRegPatronal() {
		return regPatronal;
	}

	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}
	@Transient	
	public String getTipoCorreccion() {
		return tipoCorreccion;
	}
	@Transient
	public void setTipoCorreccion(String tipoCorreccion) {
		this.tipoCorreccion = tipoCorreccion;
	}
	@Transient	
	public String getFecPeriodoIni() {
		return fecPeriodoIni;
	}

	public void setFecPeriodoIni(String fecPeriodoIni) {
		this.fecPeriodoIni = fecPeriodoIni;
	}
	@Transient	
	public String getFecPeriodoFin() {
		return fecPeriodoFin;
	}

	public void setFecPeriodoFin(String fecPeriodoFin) {
		this.fecPeriodoFin = fecPeriodoFin;
	}
	@Transient	
	public String getFecLimite() {
		return fecLimite;
	}

	public void setFecLimite(String fecLimite) {
		this.fecLimite = fecLimite;
	}
	@Transient	
	public List<CrcStatus> getLstStatus() {
		return lstStatus;
	}
	@Transient
	public void setLstStatus(List<CrcStatus> lstStatus) {
		this.lstStatus = lstStatus;
	}

	@Transient	
	public String getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(String fecInicio) {
		this.fecInicio = fecInicio;
	}

	@Transient
	public String getFecFinal() {
		return fecFinal;
	}

	public void setFecFinal(String fecFinal) {
		this.fecFinal = fecFinal;
	}


	public String imprimeObjeto(){
		return new StringBuffer().append("CrtProrroga{")
				 .append("cveSolprorroga:").append(this.getCveSolprorroga()).append(";\n")
				 .append("cveSolicitudcorr:").append(this.getCveSolicitudcorr()).append(";\n")
				 .append("cveUser:").append(this.getCveUser()).append(";\n")
				 .append("fecElaborasolpro:").append(this.getFecElaborasolpro()).append(";\n")
				 .append("fecFechareg:").append(this.getFecFechareg()).append(";\n")
				 .append("txLugar:").append(this.getTxLugar()).append(";\n")
				 .append("txMotivorazon:").append(this.getTxMotivorazon()).append(";\n")
				 .append("txRepLegalElab:").append(this.getTxRepLegalElab()).append(";\n")
				 .append("}")
				 .toString();
	}
	
}
