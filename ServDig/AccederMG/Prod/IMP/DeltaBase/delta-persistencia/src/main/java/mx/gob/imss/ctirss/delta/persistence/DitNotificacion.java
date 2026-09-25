package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIT_NOTIFICACION database table.
 * 
 */
@Entity
@Table(name = "DIT_NOTIFICACION")
public class DitNotificacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_NOTIFICACION_CVEIDNOTIFICACION_GENERATOR", sequenceName="SEQ_DITNOTIFICACION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_NOTIFICACION_CVEIDNOTIFICACION_GENERATOR")
	@Column(name="CVE_ID_NOTIFICACION")
	private long cveIdNotificacion;

	// bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_TRAMITE")
	private DitTramite ditTramite;

	// bi-directional many-to-one association to DicModulo (módulo de origen)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_MODULO_ORIGEN")
	private DicModulo dicModuloOrigen;

	// bi-directional many-to-one association to DicModulo (módulo a notificar)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_MODULO_NOTIFICAR")
	private DicModulo dicModuloNotificar;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public DitNotificacion() {
	}
	
	public DitNotificacion(long cveIdNotificacion, Long cveIdTramite,
			DitDetalleTramite ditDetalleTramite, Long cveIdTipoTramite,
			String desTipoTramite, Date fecPresentacion, Date fecEfecto,
			Date fecConclusion, DicModulo dicModuloOrigen,
			DicModulo dicModuloNotificar, Date fecRegistroAlta,
			Date fecRegistroActualizado, Date fecRegistroBaja) {
		super();
		this.cveIdNotificacion = cveIdNotificacion;
		DicTipoTramite dicTipoTramite = new DicTipoTramite(cveIdTipoTramite,
				desTipoTramite);
		this.ditTramite = new DitTramite(cveIdTramite, ditDetalleTramite,
				dicTipoTramite, fecPresentacion, fecEfecto, fecConclusion);
		this.dicModuloOrigen = dicModuloOrigen;
		this.dicModuloNotificar = dicModuloNotificar;
		this.fecRegistroAlta = fecRegistroAlta;
		this.fecRegistroActualizado = fecRegistroActualizado;
		this.fecRegistroBaja = fecRegistroBaja;
	}

	/**
	 * @return the cveIdNotificacion
	 */
	public long getCveIdNotificacion() {
		return cveIdNotificacion;
	}

	/**
	 * @param cveIdNotificacion the cveIdNotificacion to set
	 */
	public void setCveIdNotificacion(long cveIdNotificacion) {
		this.cveIdNotificacion = cveIdNotificacion;
	}

	/**
	 * @return the ditTramite
	 */
	public DitTramite getDitTramite() {
		return ditTramite;
	}

	/**
	 * @param ditTramite the ditTramite to set
	 */
	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	/**
	 * @return the dicModuloOrigen
	 */
	public DicModulo getDicModuloOrigen() {
		return dicModuloOrigen;
	}

	/**
	 * @param dicModuloOrigen the dicModuloOrigen to set
	 */
	public void setDicModuloOrigen(DicModulo dicModuloOrigen) {
		this.dicModuloOrigen = dicModuloOrigen;
	}

	/**
	 * @return the dicModuloNotificar
	 */
	public DicModulo getDicModuloNotificar() {
		return dicModuloNotificar;
	}

	/**
	 * @param dicModuloNotificar the dicModuloNotificar to set
	 */
	public void setDicModuloNotificar(DicModulo dicModuloNotificar) {
		this.dicModuloNotificar = dicModuloNotificar;
	}

	/**
	 * @return the fecRegistroAlta
	 */
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	/**
	 * @param fecRegistroAlta the fecRegistroAlta to set
	 */
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	/**
	 * @return the fecRegistroActualizado
	 */
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	/**
	 * @param fecRegistroActualizado the fecRegistroActualizado to set
	 */
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	/**
	 * @return the fecRegistroBaja
	 */
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	/**
	 * @param fecRegistroBaja the fecRegistroBaja to set
	 */
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	
}