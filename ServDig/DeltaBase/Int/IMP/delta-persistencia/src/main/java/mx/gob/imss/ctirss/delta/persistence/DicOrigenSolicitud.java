package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the DIC_ORIGEN_SOLICITUD database table.
 * 
 */
@Entity
@Table(name = "DIC_ORIGEN_SOLICITUD")
@OnSearchLlavePrimaria(atributos="cveIdOrigenSolicitud")
@ComponentComboCampoDescripcion(atributo="desOrigenSolicitud")
public class DicOrigenSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "CVE_ID_ORIGEN_SOLICITUD")
	private long cveIdOrigenSolicitud;

	@Column(name = "DES_ORIGEN_SOLICITUD")
	private String desOrigenSolicitud;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	// bi-directional many-to-one association to DitSolicitud
	@OneToMany(mappedBy = "dicOrigenSolicitud", fetch=FetchType.LAZY)
	private Set<DitSolicitud> ditSolicitudes;

	public DicOrigenSolicitud() {
	}

	public long getCveIdOrigenSolicitud() {
		return this.cveIdOrigenSolicitud;
	}

	public void setCveIdOrigenSolicitud(long cveIdOrigenSolicitud) {
		this.cveIdOrigenSolicitud = cveIdOrigenSolicitud;
	}

	public String getDesOrigenSolicitud() {
		return this.desOrigenSolicitud;
	}

	public void setDesOrigenSolicitud(String desOrigenSolicitud) {
		this.desOrigenSolicitud = desOrigenSolicitud;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Set<DitSolicitud> getDitSolicitudes() {
		return this.ditSolicitudes;
	}

	public void setDitSolicitudes(Set<DitSolicitud> ditSolicitudes) {
		this.ditSolicitudes = ditSolicitudes;
	}

}