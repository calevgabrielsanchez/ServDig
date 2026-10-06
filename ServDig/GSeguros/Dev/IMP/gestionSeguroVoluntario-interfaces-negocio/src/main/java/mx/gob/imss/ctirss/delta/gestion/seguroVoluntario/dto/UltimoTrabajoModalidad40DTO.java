package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;

public class UltimoTrabajoModalidad40DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cveCurp;
    private Integer cveModalidad;
    private String cveNss;
    private String cveRfcAsegurado;
    private String nomAsegurado;
    private String refRegistroPatronal;
    private String tipoMovObligatorio;
    private String fechaUltimoTrabajo;
    private Float salarioUltimoTrabajo;
    private Integer semanasCotizadas;
    private Integer indPension;
    private Integer indTrabajadorImss;
    private Long cveMunicipioImss;

    public String getCveCurp() { return cveCurp; }
    public void setCveCurp(String cveCurp) { this.cveCurp = cveCurp; }
    public Integer getCveModalidad() { return cveModalidad; }
    public void setCveModalidad(Integer cveModalidad) { this.cveModalidad = cveModalidad; }
    public String getCveNss() { return cveNss; }
    public void setCveNss(String cveNss) { this.cveNss = cveNss; }
    public String getCveRfcAsegurado() { return cveRfcAsegurado; }
    public void setCveRfcAsegurado(String cveRfcAsegurado) { this.cveRfcAsegurado = cveRfcAsegurado; }
    public String getNomAsegurado() { return nomAsegurado; }
    public void setNomAsegurado(String nomAsegurado) { this.nomAsegurado = nomAsegurado; }
    public String getRefRegistroPatronal() { return refRegistroPatronal; }
    public void setRefRegistroPatronal(String refRegistroPatronal) { this.refRegistroPatronal = refRegistroPatronal; }
    public String getTipoMovObligatorio() { return tipoMovObligatorio; }
    public void setTipoMovObligatorio(String tipoMovObligatorio) { this.tipoMovObligatorio = tipoMovObligatorio; }
    public String getFechaUltimoTrabajo() { return fechaUltimoTrabajo; }
    public void setFechaUltimoTrabajo(String fechaUltimoTrabajo) { this.fechaUltimoTrabajo = fechaUltimoTrabajo; }
    public Float getSalarioUltimoTrabajo() { return salarioUltimoTrabajo; }
    public void setSalarioUltimoTrabajo(Float salarioUltimoTrabajo) { this.salarioUltimoTrabajo = salarioUltimoTrabajo; }
    public Integer getSemanasCotizadas() { return semanasCotizadas; }
    public void setSemanasCotizadas(Integer semanasCotizadas) { this.semanasCotizadas = semanasCotizadas; }
    public Integer getIndPension() { return indPension; }
    public void setIndPension(Integer indPension) { this.indPension = indPension; }
    public Integer getIndTrabajadorImss() { return indTrabajadorImss; }
    public void setIndTrabajadorImss(Integer indTrabajadorImss) { this.indTrabajadorImss = indTrabajadorImss; }
	public Long getCveMunicipioImss() {
		return cveMunicipioImss;
	}
	public void setCveMunicipioImss(Long cveMunicipioImss) {
		this.cveMunicipioImss = cveMunicipioImss;
	}

	@Override
	public String toString() {
	return "UltimoTrabajoModalidad40DTO [cveCurp=" + cveCurp
	+ ", cveModalidad=" + cveModalidad
	+ ", cveNss=" + cveNss
	+ ", cveRfcAsegurado=" + cveRfcAsegurado
	+ ", nomAsegurado=" + nomAsegurado
	+ ", refRegistroPatronal=" + refRegistroPatronal
	+ ", tipoMovObligatorio=" + tipoMovObligatorio
	+ ", fechaUltimoTrabajo=" + fechaUltimoTrabajo
	+ ", salarioUltimoTrabajo=" + salarioUltimoTrabajo
	+ ", semanasCotizadas=" + semanasCotizadas
	+ ", indPension=" + indPension
	+ ", indTrabajadorImss=" + indTrabajadorImss
	+ ", cveMunicipioImss=" + cveMunicipioImss
	+ "]";
	}
}
