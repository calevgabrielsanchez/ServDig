package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_RAZON_CANCELACION database table.
 * 
 */
@Entity
@Table(name="DIC_RAZON_CANCELACION")
@OnSearchLlavePrimaria(atributos="cveIdRazonCancelacion")
@ComponentComboCampoDescripcion(atributo="desRazonCancelacion")
public class DicRazonCancelacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_RAZON_CANCELACION", nullable=false, precision=22)
	private Long cveIdRazonCancelacion;

	@Column(name="DES_RAZON_CANCELACION", nullable=false, length=255)
	private String desRazonCancelacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitSolicitud
	@OneToMany(mappedBy="dicRazonCancelacion")
	private List<DitSolicitud> ditSolicituds;

    public DicRazonCancelacion() {
    }

	public Long getCveIdRazonCancelacion() {
		return this.cveIdRazonCancelacion;
	}

	public void setCveIdRazonCancelacion(Long cveIdRazonCancelacion) {
		this.cveIdRazonCancelacion = cveIdRazonCancelacion;
	}

	public String getDesRazonCancelacion() {
		return this.desRazonCancelacion;
	}

	public void setDesRazonCancelacion(String desRazonCancelacion) {
		this.desRazonCancelacion = desRazonCancelacion;
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

	public List<DitSolicitud> getDitSolicituds() {
		return this.ditSolicituds;
	}

	public void setDitSolicituds(List<DitSolicitud> ditSolicituds) {
		this.ditSolicituds = ditSolicituds;
	}
	
}