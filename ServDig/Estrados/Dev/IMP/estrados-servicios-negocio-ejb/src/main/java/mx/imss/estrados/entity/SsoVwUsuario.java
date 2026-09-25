package mx.imss.estrados.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name="SSO_VW_USUARIO")
public class SsoVwUsuario {
	
	public SsoVwUsuario() {
	}
	
	
	@Column(name="CVE_SSOAREANORMA")
	private Integer cveSsoAreaNorma;
	
	@Column(name="DES_AREANORMA")
	private String desAreaNorma;
	
	@Column(name="CLAVE_DELEGACION")
	private String cveDelegacion;
	
	@Column(name="DES_DELEG")
	private String desDelegacion;
	
	@Column(name="CLAVE_SUBDELEGACION")
	private String cveSubdelegacion;
	
	
	@Column(name="DES_SUBDELEGACION")
	private String desSubdelegacion;
	
	@Column(name="CVE_SSODEPTO")
	private Integer cveSSODepto;
	
	@Column(name="DES_DEPARTAMENTO")
	private String desDepartamento;
	
	@Column(name="CVE_SSOPUESTO")
	private Integer cveSSOPuesto;
	
	@Column(name="DES_PUESTO")
	private String desPuesto;
	
	@Column(name="CVE_SSOESTATUS")
	private Integer cveSSOEstatus;
	
	@Column(name="DES_ESTATUS")
	private String desEstatus;
	
	@Id
	@Column(name="DES_USR_CURP")
	private String desUsrCURP;
	
	@Column(name="NOM_NOMBRE")
	private String nomNombre;
	
	@Column(name="NOM_PATERNO")
	private String nomPaterno;
	
	@Column(name="NOM_MATERNO")
	private String nomMaterno;
	
	@Column(name="REF_CORREO_ELECTRONICO")
	private String refCorreoElectronico;

	
	@Column(name="CVE_ID_DELEGACION")
	private Integer cveIdDelegacion;
	
	@Column(name="CVE_ID_SUBDELEGACION")
	private Integer cveIdSubdelegacion;
	
	
	@Column(name="DES_CLAVEPRESUPUESTAL")
	private String desClavePresupuestal;
	
	public Integer getCveSsoAreaNorma() {
		return cveSsoAreaNorma;
	}

	public void setCveSsoAreaNorma(Integer cveSsoAreaNorma) {
		this.cveSsoAreaNorma = cveSsoAreaNorma;
	}

	public String getDesAreaNorma() {
		return desAreaNorma;
	}

	public void setDesAreaNorma(String desAreaNorma) {
		this.desAreaNorma = desAreaNorma;
	}

	public String getCveDelegacion() {
		return cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getDesDelegacion() {
		return desDelegacion;
	}

	public void setDesDelegacion(String desDelegacion) {
		this.desDelegacion = desDelegacion;
	}

	public String getCveSubdelegacion() {
		return cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public String getDesSubdelegacion() {
		return desSubdelegacion;
	}

	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
	}

	public Integer getCveSSODepto() {
		return cveSSODepto;
	}

	public void setCveSSODepto(Integer cveSSODepto) {
		this.cveSSODepto = cveSSODepto;
	}

	public String getDesDepartamento() {
		return desDepartamento;
	}

	public void setDesDepartamento(String desDepartamento) {
		this.desDepartamento = desDepartamento;
	}

	public Integer getCveSSOPuesto() {
		return cveSSOPuesto;
	}

	public void setCveSSOPuesto(Integer cveSSOPuesto) {
		this.cveSSOPuesto = cveSSOPuesto;
	}

	public String getDesPuesto() {
		return desPuesto;
	}

	public void setDesPuesto(String desPuesto) {
		this.desPuesto = desPuesto;
	}

	public Integer getCveSSOEstatus() {
		return cveSSOEstatus;
	}

	public void setCveSSOEstatus(Integer cveSSOEstatus) {
		this.cveSSOEstatus = cveSSOEstatus;
	}

	public String getDesEstatus() {
		return desEstatus;
	}

	public void setDesEstatus(String desEstatus) {
		this.desEstatus = desEstatus;
	}

	public String getDesUsrCURP() {
		return desUsrCURP;
	}

	public void setDesUsrCURP(String desUsrCURP) {
		this.desUsrCURP = desUsrCURP;
	}

	public String getNomNombre() {
		return nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPaterno() {
		return nomPaterno;
	}

	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	public String getNomMaterno() {
		return nomMaterno;
	}

	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	public String getRefCorreoElectronico() {
		return refCorreoElectronico;
	}

	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}

	public Integer getCveIdDelegacion() {
		return cveIdDelegacion;
	}

	public void setCveIdDelegacion(Integer cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	public Integer getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(Integer cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public String getDesClavePresupuestal() {
		return desClavePresupuestal;
	}

	public void setDesClavePresupuestal(String desClavePresupuestal) {
		this.desClavePresupuestal = desClavePresupuestal;
	}
	
	
	

	
}
