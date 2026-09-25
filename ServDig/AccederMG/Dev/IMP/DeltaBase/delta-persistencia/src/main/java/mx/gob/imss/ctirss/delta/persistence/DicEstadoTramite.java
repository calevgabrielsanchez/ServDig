package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_ESTADO_TRAMITE database table.
 * 
 */
@Entity
@Table(name="DIC_ESTADO_TRAMITE")
public class DicEstadoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ESTADO_TRAMITE", nullable=false, precision=22)
	private Long cveIdEstadoTramite;

	@Column(name="DES_ESTADO_TRAMITE", nullable=false, length=255)
	private String desEstadoTramite;

	//@Column(name="REF_SIGLA", nullable=true)
	//private String refSigla;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitTramite
	@OneToMany(mappedBy="dicEstadoTramite" , fetch=FetchType.LAZY)
	private List<DitTramite> ditTramites;
	
	/*
	//bi-directional many-to-one association to DitTramite
	@OneToMany(mappedBy="dicEstadoTramite")
	private List<DitBitacoraSegTramite> ditBistacoraSegTramite;
	*/
    public DicEstadoTramite() {
    }

	public Long getCveIdEstadoTramite() {
		return this.cveIdEstadoTramite;
	}

	public void setCveIdEstadoTramite(Long cveIdEstadoTramite) {
		this.cveIdEstadoTramite = cveIdEstadoTramite;
	}

	public String getDesEstadoTramite() {
		return this.desEstadoTramite;
	}

	public void setDesEstadoTramite(String desEstadoTramite) {
		this.desEstadoTramite = desEstadoTramite;
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

	public List<DitTramite> getDitTramites() {
		return this.ditTramites;
	}

	public void setDitTramites(List<DitTramite> ditTramites) {
		this.ditTramites = ditTramites;
	}

	/**
	 * @return the ditBistacoraSegTramite
	 */
	/*
	public List<DitBitacoraSegTramite> getDitBistacoraSegTramite() {
		return ditBistacoraSegTramite;
	}
	*/
	/**
	 * @param ditBistacoraSegTramite the ditBistacoraSegTramite to set
	 */
	/*
	public void setDitBistacoraSegTramite(
			List<DitBitacoraSegTramite> ditBistacoraSegTramite) {
		this.ditBistacoraSegTramite = ditBistacoraSegTramite;
	}
	*/
	/*
	public String getRefSigla() {
		return refSigla;
	}

	public void setRefSigla(String refSigla) {
		this.refSigla = refSigla;
	}
	*/
	
}