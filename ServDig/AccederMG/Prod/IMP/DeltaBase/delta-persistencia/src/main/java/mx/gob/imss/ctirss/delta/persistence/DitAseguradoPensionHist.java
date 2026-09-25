package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_ASEGURADO_PENSION_HIST database table.
 * 
 */
@Entity
@Table(name="DIT_ASEGURADO_PENSION_HIST")
public class DitAseguradoPensionHist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ASEGURADO_PENSION_HIST", nullable=false, precision=22)
	private long cveIdAseguradoPensionHist;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Column(name="TIP_ESTATUS", precision=22)
	private BigDecimal tipEstatus;

	//bi-directional many-to-one association to DitAseguradoPension
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO_PENSION")
	private DitAseguradoPension ditAseguradoPension;

    public DitAseguradoPensionHist() {
    }

	public long getCveIdAseguradoPensionHist() {
		return this.cveIdAseguradoPensionHist;
	}

	public void setCveIdAseguradoPensionHist(long cveIdAseguradoPensionHist) {
		this.cveIdAseguradoPensionHist = cveIdAseguradoPensionHist;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
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

	public BigDecimal getTipEstatus() {
		return this.tipEstatus;
	}

	public void setTipEstatus(BigDecimal tipEstatus) {
		this.tipEstatus = tipEstatus;
	}

	public DitAseguradoPension getDitAseguradoPension() {
		return this.ditAseguradoPension;
	}

	public void setDitAseguradoPension(DitAseguradoPension ditAseguradoPension) {
		this.ditAseguradoPension = ditAseguradoPension;
	}
	
}