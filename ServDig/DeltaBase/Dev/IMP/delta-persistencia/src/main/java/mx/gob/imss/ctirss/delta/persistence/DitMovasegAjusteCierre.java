package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_MOVASEG_AJUSTE_CIERRE database table.
 * 
 */
@Entity
@Table(name="DIT_MOVASEG_AJUSTE_CIERRE")
public class DitMovasegAjusteCierre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MOVASEG_AJUSTE", nullable=false, precision=22)
	private long cveIdMovasegAjuste;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional one-to-one association to DitMovasegAjuste
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MOVASEG_AJUSTE", nullable=false, insertable=false, updatable=false)
	private DitMovasegAjuste ditMovasegAjuste;

	//bi-directional many-to-one association to DicCausaBajaAseg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CAUSA_BAJA_ASEG")
	private DicCausaBajaAseg dicCausaBajaAseg;

    public DitMovasegAjusteCierre() {
    }

	public long getCveIdMovasegAjuste() {
		return this.cveIdMovasegAjuste;
	}

	public void setCveIdMovasegAjuste(long cveIdMovasegAjuste) {
		this.cveIdMovasegAjuste = cveIdMovasegAjuste;
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

	public DitMovasegAjuste getDitMovasegAjuste() {
		return this.ditMovasegAjuste;
	}

	public void setDitMovasegAjuste(DitMovasegAjuste ditMovasegAjuste) {
		this.ditMovasegAjuste = ditMovasegAjuste;
	}
	
	public DicCausaBajaAseg getDicCausaBajaAseg() {
		return this.dicCausaBajaAseg;
	}

	public void setDicCausaBajaAseg(DicCausaBajaAseg dicCausaBajaAseg) {
		this.dicCausaBajaAseg = dicCausaBajaAseg;
	}
	
}