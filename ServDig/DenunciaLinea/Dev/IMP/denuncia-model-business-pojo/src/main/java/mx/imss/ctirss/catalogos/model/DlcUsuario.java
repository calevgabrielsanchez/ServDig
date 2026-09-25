package mx.imss.ctirss.catalogos.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import mx.imss.ctirss.catalogos.base.model.AbstractDlcUsuario;
import mx.imss.ctirss.framework.annotations.IgnoreAtributosEnCriteria;
import mx.imss.ctirss.framework.base.model.AbstractModel;




/**
 * The persistent class for the DLC_USUARIO database table.
 * 
 */
@Entity
@Table(name="DLC_USUARIO")
public class DlcUsuario extends AbstractModel {
	private static final long serialVersionUID = 2L;
	
	

	@Id
	@SequenceGenerator(name="DLC_USUARIO_CVEIDUSUARIO_GENERATOR", sequenceName="SEQ_CVE_USUARIODEN")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DLC_USUARIO_CVEIDUSUARIO_GENERATOR")
	@Column(name="CVE_ID_USUARIO")
	private long cveIdUsuario;

	@Column(name="CVE_ID_PERSONA")
	private Long cveIdPersona;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name="NOM_MATERNO")
	private String nomMaterno;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NOM_PATERNO")
	private String nomPaterno;

	@Column(name="NOM_USUARIO_SISTEMA")
	private String nomUsuarioSistema;

	@Column(name="NUM_MATRICULA")
	private String numMatricula;

	@Column(name="NUM_NSS")
	private String numNss;

	@Column(name="REF_PASSWORD")
	private String refPassword;

	@Column(name="TIP_USUARIO")
	private BigDecimal tipUsuario;

	
	@Transient
	private int cveSubdelegacion;
	

	public int getCveSubdelegacion() {
		return cveSubdelegacion;
	}



	public void setCveSubdelegacion(int cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}






	//bi-directional many-to-one association to SegUsuarioFuncionario
	@OneToMany(mappedBy="dlcUsuario")
	private Set<DlcUsuarioFuncionario> dlcUsuarioFuncionarios;
	
    public DlcUsuario() {
    }

    
    
	public Set<DlcUsuarioFuncionario> getDlcUsuarioFuncionarios() {
		return dlcUsuarioFuncionarios;
	}



	public void setDlcUsuarioFuncionarios(
			Set<DlcUsuarioFuncionario> dlcUsuarioFuncionarios) {
		this.dlcUsuarioFuncionarios = dlcUsuarioFuncionarios;
	}



	public long getCveIdUsuario() {
		return this.cveIdUsuario;
	}

	public void setCveIdUsuario(long cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public String getNomMaterno() {
		return this.nomMaterno;
	}

	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPaterno() {
		return this.nomPaterno;
	}

	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	public String getNomUsuarioSistema() {
		return this.nomUsuarioSistema;
	}

	public void setNomUsuarioSistema(String nomUsuarioSistema) {
		this.nomUsuarioSistema = nomUsuarioSistema;
	}

	public String getNumMatricula() {
		return this.numMatricula;
	}

	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}

	public String getNumNss() {
		return this.numNss;
	}

	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}

	public String getRefPassword() {
		return this.refPassword;
	}

	public void setRefPassword(String refPassword) {
		this.refPassword = refPassword;
	}

	public BigDecimal getTipUsuario() {
		return this.tipUsuario;
	}

	public void setTipUsuario(BigDecimal tipUsuario) {
		this.tipUsuario = tipUsuario;
	}



	public Long getCveIdPersona() {
		return cveIdPersona;
	}



	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	
	
	
	

	@Transient
	private String captcha;
	
	@Transient
	private DlcUsuarioFuncionario usuarioFuncionario;
	

	@Transient
	private Map<Long, String> perfilesDisponibles = new LinkedHashMap<Long, String>();
	
	@Transient
	private Long idPerfil;
	
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public DlcUsuarioFuncionario getUsuarioFuncionario() {
		return usuarioFuncionario;
	}

	public void setUsuarioFuncionario(DlcUsuarioFuncionario usuarioFuncionario) {
		this.usuarioFuncionario = usuarioFuncionario;
	}

	public Map<Long, String> getPerfilesDisponibles() {
		return perfilesDisponibles;
	}

	public void setPerfilesDisponibles(Map<Long, String> perfilesDisponibles) {
		this.perfilesDisponibles = perfilesDisponibles;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	
	
	
}