package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HistorialUltimoSeguroCotizadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long idUltimoTrabajo;
    private String cveNss;
    private String refRegistroPatronal;
    private String cveEntInegi;
    private String cveMunInegi;
    private Long cveIdMunicipioImss;
    private Date fecConsulta;
    private String cveMunicipioImss;
    private Integer numAnioUltimoTrabajo;
    private Integer numMesUltimoTrabajo;
    private Integer cveModalidad;
    private BigDecimal salarioUltimoTrabajo;
    private Integer numSemanasRoUlt5anios;
    private Integer indPension;
    private Integer indTrabajadorImss;
    private Date stpAlta;
    private String cveUsuarioAlta;
    private Date stpModifica;
    private String cveUsuarioModifica;
    private Date stpBaja;
    private String cveUsuarioBaja;
    private String cveCurp;
    private String cveRfcAsegurado;
    private String nomAsegurado;
    private Date fecBajaUltimoTrabajo;

    public long getIdUltimoTrabajo() { return idUltimoTrabajo; }
    public void setIdUltimoTrabajo(long value) { this.idUltimoTrabajo = value; }
    public String getCveNss() { return cveNss; }
    public void setCveNss(String value) { this.cveNss = value; }
    public String getRefRegistroPatronal() { return refRegistroPatronal; }
    public void setRefRegistroPatronal(String value) { this.refRegistroPatronal = value; }
    public String getCveEntInegi() { return cveEntInegi; }
    public void setCveEntInegi(String value) { this.cveEntInegi = value; }
    public String getCveMunInegi() { return cveMunInegi; }
    public void setCveMunInegi(String value) { this.cveMunInegi = value; }
    public Long getCveIdMunicipioImss() { return cveIdMunicipioImss; }
    public void setCveIdMunicipioImss(Long value) { this.cveIdMunicipioImss = value; }
    public Date getFecConsulta() { return fecConsulta; }
    public void setFecConsulta(Date value) { this.fecConsulta = value; }
    public String getCveMunicipioImss() { return cveMunicipioImss; }
    public void setCveMunicipioImss(String value) { this.cveMunicipioImss = value; }
    public Date getStpAlta() { return stpAlta; }
    public void setStpAlta(Date value) { this.stpAlta = value; }
    public String getCveUsuarioAlta() { return cveUsuarioAlta; }
    public void setCveUsuarioAlta(String value) { this.cveUsuarioAlta = value; }
    public Date getStpModifica() { return stpModifica; }
    public void setStpModifica(Date value) { this.stpModifica = value; }
    public String getCveUsuarioModifica() { return cveUsuarioModifica; }
    public void setCveUsuarioModifica(String value) { this.cveUsuarioModifica = value; }
    public Date getStpBaja() { return stpBaja; }
    public void setStpBaja(Date value) { this.stpBaja = value; }
    public String getCveUsuarioBaja() { return cveUsuarioBaja; }
    public void setCveUsuarioBaja(String value) { this.cveUsuarioBaja = value; }
    public String getCveCurp() { return cveCurp; }
    public void setCveCurp(String value) { this.cveCurp = value; }
    public String getCveRfcAsegurado() { return cveRfcAsegurado; }
    public void setCveRfcAsegurado(String value) { this.cveRfcAsegurado = value; }
    public String getNomAsegurado() { return nomAsegurado; }
    public void setNomAsegurado(String value) { this.nomAsegurado = value; }
    public Date getFecBajaUltimoTrabajo() { return fecBajaUltimoTrabajo; }
    public void setFecBajaUltimoTrabajo(Date value) { this.fecBajaUltimoTrabajo = value; }
	public Integer getNumAnioUltimoTrabajo() {
		return numAnioUltimoTrabajo;
	}
	public void setNumAnioUltimoTrabajo(Integer numAnioUltimoTrabajo) {
		this.numAnioUltimoTrabajo = numAnioUltimoTrabajo;
	}
	public Integer getNumMesUltimoTrabajo() {
		return numMesUltimoTrabajo;
	}
	public void setNumMesUltimoTrabajo(Integer numMesUltimoTrabajo) {
		this.numMesUltimoTrabajo = numMesUltimoTrabajo;
	}
	public Integer getCveModalidad() {
		return cveModalidad;
	}
	public void setCveModalidad(Integer cveModalidad) {
		this.cveModalidad = cveModalidad;
	}
	public BigDecimal getSalarioUltimoTrabajo() {
		return salarioUltimoTrabajo;
	}
	public void setSalarioUltimoTrabajo(BigDecimal salarioUltimoTrabajo) {
		this.salarioUltimoTrabajo = salarioUltimoTrabajo;
	}
	public Integer getNumSemanasRoUltSanios() {
		return numSemanasRoUlt5anios;
	}
	public void setNumSemanasRoUlt5anios(Integer numSemanasRoUltSanios) {
		this.numSemanasRoUlt5anios = numSemanasRoUltSanios;
	}
	public Integer getIndPension() {
		return indPension;
	}
	public void setIndPension(Integer indPension) {
		this.indPension = indPension;
	}
	public Integer getIndTrabajadorImss() {
		return indTrabajadorImss;
	}
	public void setIndTrabajadorImss(Integer indTrabajadorImss) {
		this.indTrabajadorImss = indTrabajadorImss;
	}
    
}
