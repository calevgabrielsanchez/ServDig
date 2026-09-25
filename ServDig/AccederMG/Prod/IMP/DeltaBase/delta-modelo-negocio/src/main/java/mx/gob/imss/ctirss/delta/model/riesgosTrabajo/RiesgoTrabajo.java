package mx.gob.imss.ctirss.delta.model.riesgosTrabajo;

import java.io.Serializable;
import java.util.Date;
public class RiesgoTrabajo implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3050807439523409854L;

	private String dv;
	private String rfc;
	private String consec;
	private String numSegSocial;
	private String curp;
	private String nombreAsegurado;
	private String recaidaRevaluacion;
	private Date fechaAccidente;
	private String tipoRiesgo;
	private String diasSubsidiados;
	private String porcentajeIncapac;
	private String defuncion;
	private Date fechaAlta;
	private String rpReg;
	private String modalidad;
	private String dvRp;
	
	public String getConsec() {
		return consec;
	}
	public void setConsec(String consec) {
		this.consec = consec;
	}
	public String getDv() {
        return dv;
    }
    public void setDv(String dv) {
        this.dv = dv;
    }
	public String getNumSegSocial() {
		return numSegSocial;
	}
	public void setNumSegSocial(String numSegSocial) {
		this.numSegSocial = numSegSocial;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getNombreAsegurado() {
		return nombreAsegurado;
	}
	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}
	public String getRecaidaRevaluacion() {
		return recaidaRevaluacion;
	}
	public void setRecaidaRevaluacion(String recaidaRevaluacion) {
		this.recaidaRevaluacion = recaidaRevaluacion;
	}
	public Date getFechaAccidente() {
		return fechaAccidente;
	}
	public void setFechaAccidente(Date fechaAccidente) {
		this.fechaAccidente = fechaAccidente;
	}
	public String getTipoRiesgo() {
		return tipoRiesgo;
	}
	public void setTipoRiesgo(String tipoRiesgo) {
		this.tipoRiesgo = tipoRiesgo;
	}
	public String getDiasSubsidiados() {
		return diasSubsidiados;
	}
	public void setDiasSubsidiados(String diasSubsidiados) {
		this.diasSubsidiados = diasSubsidiados;
	}
	public String getPorcentajeIncapac() {
		return porcentajeIncapac;
	}
	public void setPorcentajeIncapac(String porcentajeIncapac) {
		this.porcentajeIncapac = porcentajeIncapac;
	}
	public String getDefuncion() {
		return defuncion;
	}
	public void setDefuncion(String defuncion) {
		this.defuncion = defuncion;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getRpReg() {
		return rpReg;
	}
	public void setRpReg(String rpReg) {
		this.rpReg = rpReg;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public String getDvRp() {
		return dvRp;
	}
	public void setDvRp(String dvRp) {
		this.dvRp = dvRp;
	}
}
