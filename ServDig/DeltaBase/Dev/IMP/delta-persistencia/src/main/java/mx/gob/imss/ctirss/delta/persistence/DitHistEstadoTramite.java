package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_HIST_ESTADO_TRAMITE database table.
 * 
 */
@Entity
@Table(name="DIT_HIST_ESTADO_TRAMITE")
public class DitHistEstadoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_HIST_ESTADO_TRAMITE_CVEIDHISTESTADOTRAMITE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_HIST_ESTADO_TRAMITE_CVEIDHISTESTADOTRAMITE_GENERATOR")
	@Column(name="CVE_ID_HIST_ESTADO_TRAMITE")
	private long cveIdHistEstadoTramite;

	@Column(name="CVE_CUENTA_USUARIO")
	private String cveCuentaUsuario;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicGrupoEstadoTramite
	@ManyToOne
	@JoinColumn(name="CVE_ID_GRUPO_ESTADO_TRAMITE")
	private DicGrupoEstadoTramite dicGrupoEstadoTramite;

	//bi-directional many-to-one association to DitTramite
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE")
	private DitTramite ditTramite;

	public DitHistEstadoTramite() {
	}

	public long getCveIdHistEstadoTramite() {
		return this.cveIdHistEstadoTramite;
	}

	public void setCveIdHistEstadoTramite(long cveIdHistEstadoTramite) {
		this.cveIdHistEstadoTramite = cveIdHistEstadoTramite;
	}

	public String getCveCuentaUsuario() {
		return this.cveCuentaUsuario;
	}

	public void setCveCuentaUsuario(String cveCuentaUsuario) {
		this.cveCuentaUsuario = cveCuentaUsuario;
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

	public DicGrupoEstadoTramite getDicGrupoEstadoTramite() {
		return this.dicGrupoEstadoTramite;
	}

	public void setDicGrupoEstadoTramite(DicGrupoEstadoTramite dicGrupoEstadoTramite) {
		this.dicGrupoEstadoTramite = dicGrupoEstadoTramite;
	}

	
	public DitTramite getDitTramite() {
		return this.ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	
}