package mx.gob.imss.ctirss.correccion.login.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * The persistent class for the SEG_USUARIO database table.
 * 
 */
@Entity
@Table(name = "SEG_USUARIO")
@OnSearchLlavePrimaria(atributos = "cveIdUsuario")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SegUsuario extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3845237177512664450L;

	@Id
	@SequenceGenerator(name="CVE_ID_USUARIO_GENERATOR", sequenceName="CRS_CVE_ID_USUARIO")
	@GeneratedValue(generator="CVE_ID_USUARIO_GENERATOR")
	@Column(name = "CVE_ID_USUARIO")
	private long cveIdUsuario;

	@Column(name = "CVE_ID_PERSONA")
	private Long cveIdPersona;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name = "NOM_MATERNO")
	private String nomMaterno;

	@Column(name = "NOM_NOMBRE")
	private String nomNombre;

	@Column(name = "NOM_PATERNO")
	private String nomPaterno;

	@Column(name = "NOM_USUARIO_SISTEMA")
	private String nomUsuarioSistema;

	@Column(name = "REF_PASSWORD")
	private String refPassword;

	@Column(name = "TIP_USUARIO")
	private BigDecimal tipUsuario;

	@Column(name = "NUM_MATRICULA")
	private String numMatricula;

	@Column(name = "NUM_NSS")
	private String numNss;

	// bi-directional many-to-one association to SegPerfilUsuario
	@OneToMany(mappedBy = "segUsuario")
	private Set<SegPerfilUsuario> segPerfilUsuarios;

	// bi-directional many-to-one association to SegUsuarioFuncionario
	@OneToMany(mappedBy = "segUsuario")
	private Set<SegUsuarioFuncionario> segUsuarioFuncionarios;
	@Transient
	private Long idPerfil;
	@Transient
	private Map<Long, String> perfilesDisponibles = new LinkedHashMap<Long, String>();

	@Transient
	private SegUsuarioFuncionario usuarioFuncionario;

	@Transient
	private String total;

	@Transient
	private Long subDelegacion;
	
	@Transient
	private Long cveAuditorAsignado;	
	
	@Transient
	private String nombreCompleto;
	
	
	@Transient
	private String curpUsuario;

	public SegUsuario() {
	}

	public long getCveIdUsuario() {
		return this.cveIdUsuario;
	}

	public void setCveIdUsuario(long cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}

	public Long getCveIdPersona() {
		return this.cveIdPersona;
	}

	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
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

	public Set<SegPerfilUsuario> getSegPerfilUsuarios() {
		return this.segPerfilUsuarios;
	}

	public void setSegPerfilUsuarios(Set<SegPerfilUsuario> segPerfilUsuarios) {
		this.segPerfilUsuarios = segPerfilUsuarios;
	}

	public Set<SegUsuarioFuncionario> getSegUsuarioFuncionarios() {
		return this.segUsuarioFuncionarios;
	}

	public void setSegUsuarioFuncionarios(
			Set<SegUsuarioFuncionario> segUsuarioFuncionarios) {
		this.segUsuarioFuncionarios = segUsuarioFuncionarios;
	}

	/**
	 * @return the idPerfil
	 */
	public Long getIdPerfil() {
		return idPerfil;
	}

	/**
	 * @param idPerfil
	 *            the idPerfil to set
	 */
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	/**
	 * @return the perfilesDisponibles
	 */
	public Map<Long, String> getPerfilesDisponibles() {
		return perfilesDisponibles;
	}

	/**
	 * @param perfilesDisponibles
	 *            the perfilesDisponibles to set
	 */
	public void setPerfilesDisponibles(Map<Long, String> perfilesDisponibles) {
		this.perfilesDisponibles = perfilesDisponibles;
	}

	/**
	 * @return the usuarioFuncionario
	 */
	public SegUsuarioFuncionario getUsuarioFuncionario() {
		return usuarioFuncionario;
	}

	/**
	 * @param usuarioFuncionario
	 *            the usuarioFuncionario to set
	 */
	public void setUsuarioFuncionario(SegUsuarioFuncionario usuarioFuncionario) {
		this.usuarioFuncionario = usuarioFuncionario;
	}

	/**
	 * @return the numMatricula
	 */
	public String getNumMatricula() {
		return numMatricula;
	}

	/**
	 * @param numMatricula
	 *            the numMatricula to set
	 */
	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}

	/**
	 * @return the numNss
	 */
	public String getNumNss() {
		return numNss;
	}

	/**
	 * @param numNss
	 *            the numNss to set
	 */
	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the subDelegacion
	 */
	public Long getSubDelegacion() {
		return subDelegacion;
	}

	/**
	 * @param subDelegacion
	 *            the subDelegacion to set
	 */
	public void setSubDelegacion(Long subDelegacion) {
		this.subDelegacion = subDelegacion;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		final StringBuffer cad = new StringBuffer();
		if (this.getNomNombre() != null) {
			cad.append(this.getNomNombre());
		}
		if (this.getNomPaterno() != null) {
			cad.append(" ");
			cad.append(this.getNomPaterno());
		}
		if (this.getNomMaterno() != null) {
			cad.append(" ");
			cad.append(this.getNomMaterno());
		}
		return cad.toString();
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the cveAuditorAsignado
	 */
	public Long getCveAuditorAsignado() {
		return cveAuditorAsignado;
	}

	/**
	 * @param cveAuditorAsignado the cveAuditorAsignado to set
	 */
	public void setCveAuditorAsignado(Long cveAuditorAsignado) {
		this.cveAuditorAsignado = cveAuditorAsignado;
	}

	public String getCurpUsuario() {
		return curpUsuario;
	}

	public void setCurpUsuario(String curpUsuario) {
		this.curpUsuario = curpUsuario;
	}
	
	
}