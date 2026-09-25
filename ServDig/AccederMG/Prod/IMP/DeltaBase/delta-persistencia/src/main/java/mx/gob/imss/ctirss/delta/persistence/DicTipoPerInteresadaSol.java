package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIC_TIPO_PER_INTERESADA_SOL database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_PER_INTERESADA_SOL")
public class DicTipoPerInteresadaSol implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@SequenceGenerator(name="SEQ_DICTIPOPERINTERESADASOL" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_DICTIPOPERINTERESADASOL")
	@Column(name="CVE_TIPO_INTERESADA_SOL")
	private long cveTipoInteresadaSol;
	
	@Column(name="DES_TIPO_INTERESADO_SOLICITUD")
	private String desTipoInteresadoSolicitud;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date FecRegistroActualizado;
	
	//bi-directional many-to-one association to DitPersonaInteresadaSol
	@OneToMany(mappedBy="dicTipoPersonaInteresadaSol")
	private Set<DitPersonaInteresadaSol> ditPersonaInteresadaSols;

	/**
	 * 
	 */
	public DicTipoPerInteresadaSol() {
		super();
	}
	
	/**
	 * @return the cveTipoInteresadaSol
	 */
	public long getCveTipoInteresadaSol() {
		return cveTipoInteresadaSol;
	}

	/**
	 * @param cveTipoInteresadaSol the cveTipoInteresadaSol to set
	 */
	public void setCveTipoInteresadaSol(long cveTipoInteresadaSol) {
		this.cveTipoInteresadaSol = cveTipoInteresadaSol;
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

	/**
	 * @return the fecRegistroActualizado
	 */
	public Date getFecRegistroActualizado() {
		return FecRegistroActualizado;
	}

	/**
	 * @param fecRegistroActualizado the fecRegistroActualizado to set
	 */
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		FecRegistroActualizado = fecRegistroActualizado;
	}

	/**
	 * @return the ditPersonaInteresadaSols
	 */
	public Set<DitPersonaInteresadaSol> getDitPersonaInteresadaSols() {
		return ditPersonaInteresadaSols;
	}

	/**
	 * @param ditPersonaInteresadaSols the ditPersonaInteresadaSols to set
	 */
	public void setDitPersonaInteresadaSols(
			Set<DitPersonaInteresadaSol> ditPersonaInteresadaSols) {
		this.ditPersonaInteresadaSols = ditPersonaInteresadaSols;
	}

	public String getDesTipoInteresadoSolicitud() {
		return desTipoInteresadoSolicitud;
	}

	public void setDesTipoInteresadoSolicitud(String desTipoInteresadoSolicitud) {
		this.desTipoInteresadoSolicitud = desTipoInteresadoSolicitud;
	}
	
}
