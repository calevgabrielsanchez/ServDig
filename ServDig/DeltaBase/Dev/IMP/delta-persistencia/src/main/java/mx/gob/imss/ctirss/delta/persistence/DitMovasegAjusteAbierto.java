package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_MOVASEG_AJUSTE_ABIERTO database table.
 * 
 */
@Entity
@Table(name="DIT_MOVASEG_AJUSTE_ABIERTO")
public class DitMovasegAjusteAbierto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MOVASEG_AJUSTE", nullable=false, precision=22)
	private long cveIdMovasegAjuste;

	@Column(name="DES_OCUPACION", length=100)
	private String desOcupacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="SALARIO_ART_33", length=20)
	private String salarioArt33;

	@Column(name="SALARIO_BASE", length=20)
	private String salarioBase;

	@Column(name="SALARIO_REAL", length=20)
	private String salarioReal;

	//bi-directional one-to-one association to DitMovasegAjuste
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MOVASEG_AJUSTE", nullable=false, insertable=false, updatable=false)
	private DitMovasegAjuste ditMovasegAjuste;

	//bi-directional many-to-one association to DitTipoTrabModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_TRAB_MODALIDAD")
	private DitTipoTrabModalidad ditTipoTrabModalidad;

	//bi-directional many-to-one association to DitTipoAsegModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_ASEG_MODALIDAD")
	private DitTipoAsegModalidad ditTipoAsegModalidad;

	//bi-directional many-to-one association to DitTipoSalarioModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_SALARIO_MODALIDAD")
	private DitTipoSalarioModalidad ditTipoSalarioModalidad;

	//bi-directional many-to-one association to DitTipoSemanaModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_SEMANA_MODALIDAD")
	private DitTipoSemanaModalidad ditTipoSemanaModalidad;

	//bi-directional many-to-one association to DitTipoJornadaModalidad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_JORNADA_MODALIDAD")
	private DitTipoJornadaModalidad ditTipoJornadaModalidad;

    public DitMovasegAjusteAbierto() {
    }

	public long getCveIdMovasegAjuste() {
		return this.cveIdMovasegAjuste;
	}

	public void setCveIdMovasegAjuste(long cveIdMovasegAjuste) {
		this.cveIdMovasegAjuste = cveIdMovasegAjuste;
	}

	public String getDesOcupacion() {
		return this.desOcupacion;
	}

	public void setDesOcupacion(String desOcupacion) {
		this.desOcupacion = desOcupacion;
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

	public String getSalarioArt33() {
		return this.salarioArt33;
	}

	public void setSalarioArt33(String salarioArt33) {
		this.salarioArt33 = salarioArt33;
	}

	public String getSalarioBase() {
		return this.salarioBase;
	}

	public void setSalarioBase(String salarioBase) {
		this.salarioBase = salarioBase;
	}

	public String getSalarioReal() {
		return this.salarioReal;
	}

	public void setSalarioReal(String salarioReal) {
		this.salarioReal = salarioReal;
	}

	public DitMovasegAjuste getDitMovasegAjuste() {
		return this.ditMovasegAjuste;
	}

	public void setDitMovasegAjuste(DitMovasegAjuste ditMovasegAjuste) {
		this.ditMovasegAjuste = ditMovasegAjuste;
	}
	
	public DitTipoTrabModalidad getDitTipoTrabModalidad() {
		return this.ditTipoTrabModalidad;
	}

	public void setDitTipoTrabModalidad(DitTipoTrabModalidad ditTipoTrabModalidad) {
		this.ditTipoTrabModalidad = ditTipoTrabModalidad;
	}
	
	public DitTipoAsegModalidad getDitTipoAsegModalidad() {
		return this.ditTipoAsegModalidad;
	}

	public void setDitTipoAsegModalidad(DitTipoAsegModalidad ditTipoAsegModalidad) {
		this.ditTipoAsegModalidad = ditTipoAsegModalidad;
	}
	
	public DitTipoSalarioModalidad getDitTipoSalarioModalidad() {
		return this.ditTipoSalarioModalidad;
	}

	public void setDitTipoSalarioModalidad(DitTipoSalarioModalidad ditTipoSalarioModalidad) {
		this.ditTipoSalarioModalidad = ditTipoSalarioModalidad;
	}
	
	public DitTipoSemanaModalidad getDitTipoSemanaModalidad() {
		return this.ditTipoSemanaModalidad;
	}

	public void setDitTipoSemanaModalidad(DitTipoSemanaModalidad ditTipoSemanaModalidad) {
		this.ditTipoSemanaModalidad = ditTipoSemanaModalidad;
	}
	
	public DitTipoJornadaModalidad getDitTipoJornadaModalidad() {
		return this.ditTipoJornadaModalidad;
	}

	public void setDitTipoJornadaModalidad(DitTipoJornadaModalidad ditTipoJornadaModalidad) {
		this.ditTipoJornadaModalidad = ditTipoJornadaModalidad;
	}
	
}