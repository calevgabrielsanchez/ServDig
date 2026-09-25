/**
 * 
 */
package mx.gob.imss.ctirss.admonusuarios.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author cesarAgustin
 *
 */
@Entity
@Table(name="DIT_USUARIO")
@NamedQueries(
	    {
	        @NamedQuery(name = "Usuario.findId", query = "select u from Usuario u where u.idUsuario = :id")	       
	    }
)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "CVE_ID_USUARIO", nullable = false, updatable = false)
	private Long idUsuario;
	
	@Column(name="NOM_USUARIO_SISTEMA", length=20)
	private String nombreSistema;
		
	@Column(name="REF_PASSWORD", length=100)
	private String refPassword;
		
	@Column(name="NOM_NOMBRE", length=100)
	private String nombre;
	
	@Column(name="NOM_PATERNO", length=100)
	private String paterno;
	
	@Column(name="NOM_MATERNO", length=100)
	private String materno;
	
	@Column(name="CVE_ID_PERSONA")
	private Long idPersona;
	
	@Column(name="TIP_USUARIO")
	private Long tipUsuario;
	
	@Column(name="CVE_ID_PERFIL_USUARIO")
	private Long idPerfil;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date registroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date registroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date registroActualizado;
	
	@Column(name="CURP")
	private String curp;

	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the nombreSistema
	 */
	public String getNombreSistema() {
		return nombreSistema;
	}
	/**
	 * @param nombreSistema the nombreSistema to set
	 */
	public void setNombreSistema(String nombreSistema) {
		this.nombreSistema = nombreSistema;
	}

	/**
	 * @return the refPassword
	 */
	public String getRefPassword() {
		return refPassword;
	}
	/**
	 * @param refPassword the refPassword to set
	 */
	public void setRefPassword(String refPassword) {
		this.refPassword = refPassword;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the paterno
	 */
	public String getPaterno() {
		return paterno;
	}
	/**
	 * @param paterno the paterno to set
	 */
	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	/**
	 * @return the materno
	 */
	public String getMaterno() {
		return materno;
	}
	/**
	 * @param materno the materno to set
	 */
	public void setMaterno(String materno) {
		this.materno = materno;
	}

	/**
	 * @return the idPersona
	 */
	public Long getIdPersona() {
		return idPersona;
	}
	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the tipUsuario
	 */
	public Long getTipUsuario() {
		return tipUsuario;
	}
	/**
	 * @param tipUsuario the tipUsuario to set
	 */
	public void setTipUsuario(Long tipUsuario) {
		this.tipUsuario = tipUsuario;
	}

	/**
	 * @return the idPerfil
	 */
	public Long getIdPerfil() {
		return idPerfil;
	}
	/**
	 * @param idPerfil the idPerfil to set
	 */
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	/**
	 * @return the registroAlta
	 */
	public Date getRegistroAlta() {
		return registroAlta;
	}
	/**
	 * @param registroAlta the registroAlta to set
	 */
	public void setRegistroAlta(Date registroAlta) {
		this.registroAlta = registroAlta;
	}

	/**
	 * @return the registroBaja
	 */
	public Date getRegistroBaja() {
		return registroBaja;
	}
	/**
	 * @param registroBaja the registroBaja to set
	 */
	public void setRegistroBaja(Date registroBaja) {
		this.registroBaja = registroBaja;
	}

	/**
	 * @return the registroActualizado
	 */
	public Date getRegistroActualizado() {
		return registroActualizado;
	}
	/**
	 * @param registroActualizado the registroActualizado to set
	 */
	public void setRegistroActualizado(Date registroActualizado) {
		this.registroActualizado = registroActualizado;
	}		
	
	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}
	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}	
}
