package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_MODULO database table.
 * 
 */
@Entity
@Table(name="DIC_MODULO")
public class DicModulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MODULO", nullable=false, precision=22)
	private long cveIdModulo;

	@Column(name="DES_MODULO", length=20)
	private String desModulo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicTipoTramite
	@ManyToMany(mappedBy="dicModulos")
	private List<DicTipoTramite> dicTipoTramites;
	
	//bi-directional many-to-one association to DitNotificacion
	@OneToMany(mappedBy="dicModuloOrigen")
	private List<DitNotificacion> ditNotificacionesOrigen;

	//bi-directional many-to-one association to DitNotificacion
	@OneToMany(mappedBy="dicModuloNotificar")
	private List<DitNotificacion> ditNotificacionesNotificar;

    public DicModulo() {
    }

	public long getCveIdModulo() {
		return this.cveIdModulo;
	}

	public void setCveIdModulo(long cveIdModulo) {
		this.cveIdModulo = cveIdModulo;
	}

	public String getDesModulo() {
		return this.desModulo;
	}

	public void setDesModulo(String desModulo) {
		this.desModulo = desModulo;
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

	public List<DicTipoTramite> getDicTipoTramites() {
		return this.dicTipoTramites;
	}

	public void setDicTipoTramites(List<DicTipoTramite> dicTipoTramites) {
		this.dicTipoTramites = dicTipoTramites;
	}

	public List<DitNotificacion> getDitNotificacionesOrigen() {
		return ditNotificacionesOrigen;
	}

	public void setDitNotificacionesOrigen(List<DitNotificacion> ditNotificacionesOrigen) {
		this.ditNotificacionesOrigen = ditNotificacionesOrigen;
	}

	public List<DitNotificacion> getDitNotificacionesNotificar() {
		return ditNotificacionesNotificar;
	}

	public void setDitNotificacionesNotificar(
			List<DitNotificacion> ditNotificacionesNotificar) {
		this.ditNotificacionesNotificar = ditNotificacionesNotificar;
	}
	
}