package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_MOVASEG_AJUSTE database table.
 * 
 */
@Entity
@Table(name="DIT_MOVASEG_AJUSTE")
public class DitMovasegAjuste implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MOVASEG_AJUSTE", nullable=false, precision=22)
	private long cveIdMovasegAjuste;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_EXTEMPORANEO", length=50)
	private String indExtemporaneo;

	//bi-directional many-to-one association to DitMovimientoAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MOVIMIENTO_ASEGURADO")
	private DitMovimientoAsegurado ditMovimientoAsegurado;

	//bi-directional many-to-one association to DicOrigenMovtoAjuste
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ORIGEN_MOVTO_AJUSTE")
	private DicOrigenMovtoAjuste dicOrigenMovtoAjuste;

	//bi-directional many-to-one association to DicTipoMovtoAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_MOVTO_ASEGURADO")
	private DicTipoMovtoAsegurado dicTipoMovtoAsegurado;

	//bi-directional many-to-one association to DitAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO")
	private DitAsegurado ditAsegurado;

	//bi-directional one-to-one association to DitMovasegAjusteAbierto
	@OneToOne(mappedBy="ditMovasegAjuste", fetch=FetchType.LAZY)
	private DitMovasegAjusteAbierto ditMovasegAjusteAbierto;

	//bi-directional one-to-one association to DitMovasegAjusteCierre
	@OneToOne(mappedBy="ditMovasegAjuste", fetch=FetchType.LAZY)
	private DitMovasegAjusteCierre ditMovasegAjusteCierre;

    public DitMovasegAjuste() {
    }

	public long getCveIdMovasegAjuste() {
		return this.cveIdMovasegAjuste;
	}

	public void setCveIdMovasegAjuste(long cveIdMovasegAjuste) {
		this.cveIdMovasegAjuste = cveIdMovasegAjuste;
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

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public String getIndExtemporaneo() {
		return this.indExtemporaneo;
	}

	public void setIndExtemporaneo(String indExtemporaneo) {
		this.indExtemporaneo = indExtemporaneo;
	}

	public DitMovimientoAsegurado getDitMovimientoAsegurado() {
		return this.ditMovimientoAsegurado;
	}

	public void setDitMovimientoAsegurado(DitMovimientoAsegurado ditMovimientoAsegurado) {
		this.ditMovimientoAsegurado = ditMovimientoAsegurado;
	}
	
	public DicOrigenMovtoAjuste getDicOrigenMovtoAjuste() {
		return this.dicOrigenMovtoAjuste;
	}

	public void setDicOrigenMovtoAjuste(DicOrigenMovtoAjuste dicOrigenMovtoAjuste) {
		this.dicOrigenMovtoAjuste = dicOrigenMovtoAjuste;
	}
	
	public DicTipoMovtoAsegurado getDicTipoMovtoAsegurado() {
		return this.dicTipoMovtoAsegurado;
	}

	public void setDicTipoMovtoAsegurado(DicTipoMovtoAsegurado dicTipoMovtoAsegurado) {
		this.dicTipoMovtoAsegurado = dicTipoMovtoAsegurado;
	}
	
	public DitAsegurado getDitAsegurado() {
		return this.ditAsegurado;
	}

	public void setDitAsegurado(DitAsegurado ditAsegurado) {
		this.ditAsegurado = ditAsegurado;
	}
	
	public DitMovasegAjusteAbierto getDitMovasegAjusteAbierto() {
		return this.ditMovasegAjusteAbierto;
	}

	public void setDitMovasegAjusteAbierto(DitMovasegAjusteAbierto ditMovasegAjusteAbierto) {
		this.ditMovasegAjusteAbierto = ditMovasegAjusteAbierto;
	}
	
	public DitMovasegAjusteCierre getDitMovasegAjusteCierre() {
		return this.ditMovasegAjusteCierre;
	}

	public void setDitMovasegAjusteCierre(DitMovasegAjusteCierre ditMovasegAjusteCierre) {
		this.ditMovasegAjusteCierre = ditMovasegAjusteCierre;
	}
	
}