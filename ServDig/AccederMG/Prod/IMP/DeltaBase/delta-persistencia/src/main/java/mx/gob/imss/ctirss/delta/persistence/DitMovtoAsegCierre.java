package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_MOVTO_ASEG_CIERRE database table.
 * 
 */
@Entity
@Table(name="DIT_MOVTO_ASEG_CIERRE")
public class DitMovtoAsegCierre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MOVIMIENTO_ASEGURADO", nullable=false, precision=22)
	private long cveIdMovimientoAsegurado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional one-to-one association to DitMovimientoAsegurado
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MOVIMIENTO_ASEGURADO", nullable=false, insertable=false, updatable=false)
	private DitMovimientoAsegurado ditMovimientoAsegurado;

	//bi-directional many-to-one association to DicCausaBajaAseg
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CAUSA_BAJA_ASEG")
	private DicCausaBajaAseg dicCausaBajaAseg;

    public DitMovtoAsegCierre() {
    }

	public long getCveIdMovimientoAsegurado() {
		return this.cveIdMovimientoAsegurado;
	}

	public void setCveIdMovimientoAsegurado(long cveIdMovimientoAsegurado) {
		this.cveIdMovimientoAsegurado = cveIdMovimientoAsegurado;
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

	public DitMovimientoAsegurado getDitMovimientoAsegurado() {
		return this.ditMovimientoAsegurado;
	}

	public void setDitMovimientoAsegurado(DitMovimientoAsegurado ditMovimientoAsegurado) {
		this.ditMovimientoAsegurado = ditMovimientoAsegurado;
	}
	
	public DicCausaBajaAseg getDicCausaBajaAseg() {
		return this.dicCausaBajaAseg;
	}

	public void setDicCausaBajaAseg(DicCausaBajaAseg dicCausaBajaAseg) {
		this.dicCausaBajaAseg = dicCausaBajaAseg;
	}
	
}