package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the FDT_CARTA_PRESENT_DOM database table.
 * 
 */
@Entity
@Table(name="FDT_CARTA_PRESENT_DOM")
public class FdtCartaPresentDom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CARTA_PRESENT_DOM", nullable=false, precision=22)
	private long cveIdCartaPresentDom;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to FdtCartaPresentacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DICTAMEN")
	private FdtCartaPresentacion fdtCartaPresentacion;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID")
	private DgDomicilioGeografico dgDomicilioGeografico;

    public FdtCartaPresentDom() {
    }

	public long getCveIdCartaPresentDom() {
		return this.cveIdCartaPresentDom;
	}

	public void setCveIdCartaPresentDom(long cveIdCartaPresentDom) {
		this.cveIdCartaPresentDom = cveIdCartaPresentDom;
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

	public FdtCartaPresentacion getFdtCartaPresentacion() {
		return this.fdtCartaPresentacion;
	}

	public void setFdtCartaPresentacion(FdtCartaPresentacion fdtCartaPresentacion) {
		this.fdtCartaPresentacion = fdtCartaPresentacion;
	}
	
	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
}