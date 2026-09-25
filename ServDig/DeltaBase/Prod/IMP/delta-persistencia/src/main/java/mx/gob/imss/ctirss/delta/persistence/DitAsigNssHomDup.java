package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_ASIG_NSS_HOM_DUP database table.
 * 
 */
@Entity
@Table(name="DIT_ASIG_NSS_HOM_DUP")
public class DitAsigNssHomDup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ASIG_NSS_HOM_DUP", nullable=false, precision=22)
	private long cveIdAsigNssHomDup;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_INACTIVIDAD", precision=22)
	private BigDecimal indInactividad;

	//bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASIG_NSS_DESTINO")
	private DitAsignacionNss ditAsignacionNss1;

	//bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASIG_NSS_ORIGEN")
	private DitAsignacionNss ditAsignacionNss2;

    public DitAsigNssHomDup() {
    }

	public long getCveIdAsigNssHomDup() {
		return this.cveIdAsigNssHomDup;
	}

	public void setCveIdAsigNssHomDup(long cveIdAsigNssHomDup) {
		this.cveIdAsigNssHomDup = cveIdAsigNssHomDup;
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

	public BigDecimal getIndInactividad() {
		return this.indInactividad;
	}

	public void setIndInactividad(BigDecimal indInactividad) {
		this.indInactividad = indInactividad;
	}

	public DitAsignacionNss getDitAsignacionNss1() {
		return this.ditAsignacionNss1;
	}

	public void setDitAsignacionNss1(DitAsignacionNss ditAsignacionNss1) {
		this.ditAsignacionNss1 = ditAsignacionNss1;
	}
	
	public DitAsignacionNss getDitAsignacionNss2() {
		return this.ditAsignacionNss2;
	}

	public void setDitAsignacionNss2(DitAsignacionNss ditAsignacionNss2) {
		this.ditAsignacionNss2 = ditAsignacionNss2;
	}
	
}