package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the FDT_CONSTRUCCION_OBRAS_DOM database table.
 * 
 */
@Entity
@Table(name="FDT_CONSTRUCCION_OBRAS_DOM")
public class FdtConstruccionObrasDom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CONSTRUCCION_OBRAS_DOM", nullable=false, precision=22)
	private long cveIdConstruccionObrasDom;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to FdtConstruccionObra
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL"),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN"),
		@JoinColumn(name="NU_OBRA", referencedColumnName="NU_OBRA"),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON")
		})
	private FdtConstruccionObra fdtConstruccionObra;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DOMICILIO_ID")
	private DgDomicilioGeografico dgDomicilioGeografico;

    public FdtConstruccionObrasDom() {
    }

	public long getCveIdConstruccionObrasDom() {
		return this.cveIdConstruccionObrasDom;
	}

	public void setCveIdConstruccionObrasDom(long cveIdConstruccionObrasDom) {
		this.cveIdConstruccionObrasDom = cveIdConstruccionObrasDom;
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

	public FdtConstruccionObra getFdtConstruccionObra() {
		return this.fdtConstruccionObra;
	}

	public void setFdtConstruccionObra(FdtConstruccionObra fdtConstruccionObra) {
		this.fdtConstruccionObra = fdtConstruccionObra;
	}
	
	public DgDomicilioGeografico getDgDomicilioGeografico() {
		return this.dgDomicilioGeografico;
	}

	public void setDgDomicilioGeografico(DgDomicilioGeografico dgDomicilioGeografico) {
		this.dgDomicilioGeografico = dgDomicilioGeografico;
	}
	
}