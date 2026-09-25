package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_PATRON_ESCISION database table.
 * 
 */
@Entity
@Table(name="DIT_PATRON_ESCISION")
public class DitPatronEscision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PATRON_ESCISION", nullable=false, precision=22)
	private long cveIdPatronEscision;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_TRABAJADORES", precision=22)
	private BigDecimal numTrabajadores;

	//bi-directional many-to-one association to DitPatronGeneral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_GENERAL")
	private DitPatronGeneral ditPatronGeneral1;

	//bi-directional many-to-one association to DitPatronGeneral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_ESCINDIDO")
	private DitPatronGeneral ditPatronGeneral2;

    public DitPatronEscision() {
    }

	public long getCveIdPatronEscision() {
		return this.cveIdPatronEscision;
	}

	public void setCveIdPatronEscision(long cveIdPatronEscision) {
		this.cveIdPatronEscision = cveIdPatronEscision;
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

	public BigDecimal getNumTrabajadores() {
		return this.numTrabajadores;
	}

	public void setNumTrabajadores(BigDecimal numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}

	public DitPatronGeneral getDitPatronGeneral1() {
		return this.ditPatronGeneral1;
	}

	public void setDitPatronGeneral1(DitPatronGeneral ditPatronGeneral1) {
		this.ditPatronGeneral1 = ditPatronGeneral1;
	}
	
	public DitPatronGeneral getDitPatronGeneral2() {
		return this.ditPatronGeneral2;
	}

	public void setDitPatronGeneral2(DitPatronGeneral ditPatronGeneral2) {
		this.ditPatronGeneral2 = ditPatronGeneral2;
	}
	
}