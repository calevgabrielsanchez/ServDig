package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_GRUPO_ESTADO_TRAMITE database table.
 * 
 */
@Entity
@Table(name="DIC_GRUPO_ESTADO_TRAMITE")
public class DicGrupoEstadoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_GRUPO_ESTADO_TRAMITE_CVEIDGRUPOESTADOTRAMITE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_GRUPO_ESTADO_TRAMITE_CVEIDGRUPOESTADOTRAMITE_GENERATOR")
	@Column(name="CVE_ID_GRUPO_ESTADO_TRAMITE")
	private long cveIdGrupoEstadoTramite;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicEstadoTramite
	@ManyToOne
	@JoinColumn(name="CVE_ID_ESTADO_TRAMITE")
	private DicEstadoTramite dicEstadoTramite;

	//bi-directional many-to-one association to DicTipoTramite
	@ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_TRAMITE")
	private DicTipoTramite dicTipoTramite;

	//bi-directional many-to-one association to DitHistEstadoTramite
	@OneToMany(mappedBy="dicGrupoEstadoTramite")
	private List<DitHistEstadoTramite> ditHistEstadoTramites;

	public DicGrupoEstadoTramite() {
	}

	public long getCveIdGrupoEstadoTramite() {
		return this.cveIdGrupoEstadoTramite;
	}

	public void setCveIdGrupoEstadoTramite(long cveIdGrupoEstadoTramite) {
		this.cveIdGrupoEstadoTramite = cveIdGrupoEstadoTramite;
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

	public DicEstadoTramite getDicEstadoTramite() {
		return this.dicEstadoTramite;
	}

	public void setDicEstadoTramite(DicEstadoTramite dicEstadoTramite) {
		this.dicEstadoTramite = dicEstadoTramite;
	}

	
	public DicTipoTramite getDicTipoTramite() {
		return this.dicTipoTramite;
	}

	public void setDicTipoTramite(DicTipoTramite dicTipoTramite) {
		this.dicTipoTramite = dicTipoTramite;
	}

	
	public List<DitHistEstadoTramite> getDitHistEstadoTramites() {
		return this.ditHistEstadoTramites;
	}

	public void setDitHistEstadoTramites(List<DitHistEstadoTramite> ditHistEstadoTramites) {
		this.ditHistEstadoTramites = ditHistEstadoTramites;
	}

	
	public DitHistEstadoTramite addDitHistEstadoTramites(DitHistEstadoTramite ditHistEstadoTramites) {
		getDitHistEstadoTramites().add(ditHistEstadoTramites);
		ditHistEstadoTramites.setDicGrupoEstadoTramite(this);

		return ditHistEstadoTramites;
	}

	public DitHistEstadoTramite removeDitHistEstadoTramites(DitHistEstadoTramite ditHistEstadoTramites) {
		getDitHistEstadoTramites().remove(ditHistEstadoTramites);
		ditHistEstadoTramites.setDicGrupoEstadoTramite(null);

		return ditHistEstadoTramites;
	}
}