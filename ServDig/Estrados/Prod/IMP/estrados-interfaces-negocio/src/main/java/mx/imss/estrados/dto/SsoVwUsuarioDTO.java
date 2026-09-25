package mx.imss.estrados.dto;

import java.io.Serializable;

public class SsoVwUsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4297795363355567322L;
	/**
	 * 
	 */
	

	private Integer cveSsoAreaNorma;
	private String desAreaNorma;
	private String cveDelegacion;
	private String desDelegacion;
	private String cveSubdelegacion;
	private String desSubdelegacion;
	private Integer cveSSODepto;
	private String desDepartamento;
	private Integer cveSSOPuesto;
	private String desPuesto;
	private Integer cveSSOEstatus;
	private String desEstatus;
	private String desUsrCURP;
	private String nomNombre;
	private String nomPaterno;
	private String nomMaterno;
	private String refCorreoElectronico;
	private String fechaSistema;
	private Integer idDelegacion;
	private Integer idSubdelegacion;
	private Integer cveIdDelegacion;
	private Integer cveIdSubdelegacion;
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
		if (cveDelegacion!=null && cveDelegacion.length() < 2) {
			String tem = cveDelegacion;
			cveDelegacion = "0" + tem;
		}
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
		if (cveSubdelegacion!=null && cveSubdelegacion.length() < 2) {
			String tem = cveSubdelegacion;
			cveSubdelegacion = "0" + tem;
		}
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

	public String getFechaSistema() {
		return fechaSistema;
	}

	public void setFechaSistema(String fechaSistema) {
		this.fechaSistema = fechaSistema;
	}

	public Integer getIdDelegacion() {
		return idDelegacion;
	}

	public void setIdDelegacion(Integer idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	public Integer getIdSubdelegacion() {
		return idSubdelegacion;
	}

	public void setIdSubdelegacion(Integer idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
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
