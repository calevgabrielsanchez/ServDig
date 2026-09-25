package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_LLAVE_ASEGURADO database table.
 * 
 */
@Entity
@Table(name = "DIT_LLAVE_ASEGURADO")
public class DitLlaveAsegurado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "REF_BUSCA", unique = true, nullable = false, precision = 11)
	private String refBusca;

	// bi-directional many-to-one association to DitAseguradoPension
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_ASEGURADO_PENSION")
	private DitAseguradoPension ditAseguradoPension;

	// bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_ASIGNACION_NSS")
	private DitAsignacionNss ditAsignacionNss;

	// bi-directional many-to-one association to DitBalancePrestacion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_BALANCE_PRESTACION")
	private DitBalancePrestacion ditBalancePrestacion;

	// bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA")
	private DitPersona ditPersona;

	public DitLlaveAsegurado() {
	}

	public DitAseguradoPension getDitAseguradoPension() {
		return ditAseguradoPension;
	}

	public void setDitAseguradoPension(DitAseguradoPension ditAseguradoPension) {
		this.ditAseguradoPension = ditAseguradoPension;
	}

	public DitAsignacionNss getDitAsignacionNss() {
		return ditAsignacionNss;
	}

	public void setDitAsignacionNss(DitAsignacionNss ditAsignacionNss) {
		this.ditAsignacionNss = ditAsignacionNss;
	}

	public DitBalancePrestacion getDitBalancePrestacion() {
		return ditBalancePrestacion;
	}

	public void setDitBalancePrestacion(
			DitBalancePrestacion ditBalancePrestacion) {
		this.ditBalancePrestacion = ditBalancePrestacion;
	}

	public DitPersona getDitPersona() {
		return ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	public String getRefBusca() {
		return this.refBusca;
	}

	public void setRefBusca(String refBusca) {
		this.refBusca = refBusca;
	}
}