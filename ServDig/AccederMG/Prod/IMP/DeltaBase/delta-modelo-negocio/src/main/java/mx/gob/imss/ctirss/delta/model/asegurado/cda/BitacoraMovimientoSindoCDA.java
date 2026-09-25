package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class BitacoraMovimientoSindoCDA extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String folio;
	private Long cveDelegacion;
	private Long cveSubdelegacion;
	private String nombreAsegurado;
	private String resultado;
	private String nss;
	private Integer origen;
	private String observacion;
	private Date fecInicioMovimiento;
	private Date fecFinMovimiento;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Long getCveDelegacion() {
		return cveDelegacion;
	}

	public void setCveDelegacion(Long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public Long getCveSubdelegacion() {
		return cveSubdelegacion;
	}

	public void setCveSubdelegacion(Long cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public String getNombreAsegurado() {
		return nombreAsegurado;
	}

	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public Integer getOrigen() {
		return origen;
	}

	public void setOrigen(Integer origen) {
		this.origen = origen;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFecInicioMovimiento() {
		return fecInicioMovimiento;
	}

	public void setFecInicioMovimiento(Date fecInicioMovimiento) {
		this.fecInicioMovimiento = fecInicioMovimiento;
	}

	public Date getFecFinMovimiento() {
		return fecFinMovimiento;
	}

	public void setFecFinMovimiento(Date fecFinMovimiento) {
		this.fecFinMovimiento = fecFinMovimiento;
	}

}
