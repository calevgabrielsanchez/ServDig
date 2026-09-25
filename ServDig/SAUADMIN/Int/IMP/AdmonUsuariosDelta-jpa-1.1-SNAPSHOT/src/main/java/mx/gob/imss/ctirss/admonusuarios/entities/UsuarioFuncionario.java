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
 * @author cesarAgutsin
 *
 */
@Entity
@Table(name="DIT_USUARIO_FUNCIONARIO")
@NamedQueries(
		{
	        @NamedQuery(name = "UsuarioFuncionario.findAll", query = "select f from UsuarioFuncionario f"),
	        @NamedQuery(name = "UsuarioFuncionario.findId", query = "select f from UsuarioFuncionario f where f.idUsuarioFuncionario = :id")
	    }
)
public class UsuarioFuncionario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "CVE_ID_USUARIO_FUNCIONARIO", nullable = false, updatable = false)
	private Long idUsuarioFuncionario;
	
	@Column(name="CVE_ID_USUARIO")
	private Long idUsuario;
	
	@Column(name="DES_CARGO", length=100)
	private String descripcionCargo;
	
	@Column(name="CVE_ID_DELEGACION")
	private Long idDelegacion;
	
	@Column(name="CVE_ID_SUBDELEGACION")
	private Long idSuddelegacion;
	
	@Column(name="TIP_CENTRO_TRABAJO")
	private Long centroTrabajo;
	
	@Column(name="NUM_LADA_CONTACTO")
	private Long numeroLada;

	@Column(name="NUM_TELEFONO_CONTACTO")
	private Long numeroTelefonico;
	
	@Column(name="NUM_EXTENSION_CONTACTO")
	private Long numExtension;
	
	@Column(name="REF_CORREO_ELECTRONICO_TRABAJO", length=100)
	private String correoElectronico;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date registroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date registroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date registroActualizado;
	
	@Column(name="CVE_ID_UMF")
	private Long idUMF;
	

	/**
	 * @return the idUsuarioFuncionario
	 */
	public Long getIdUsuarioFuncionario() {
		return idUsuarioFuncionario;
	}
	/**
	 * @param idUsuarioFuncionario the idUsuarioFuncionario to set
	 */
	public void setIdUsuarioFuncionario(Long idUsuarioFuncionario) {
		this.idUsuarioFuncionario = idUsuarioFuncionario;
	}

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
	 * @return the descripcionCargo
	 */
	public String getDescripcionCargo() {
		return descripcionCargo;
	}
	/**
	 * @param descripcionCargo the descripcionCargo to set
	 */
	public void setDescripcionCargo(String descripcionCargo) {
		this.descripcionCargo = descripcionCargo;
	}

	/**
	 * @return the idDelegacion
	 */
	public Long getIdDelegacion() {
		return idDelegacion;
	}
	/**
	 * @param idDelegacion the idDelegacion to set
	 */
	public void setIdDelegacion(Long idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	/**
	 * @return the idSuddelegacion
	 */
	public Long getIdSuddelegacion() {
		return idSuddelegacion;
	}
	/**
	 * @param idSuddelegacion the idSuddelegacion to set
	 */
	public void setIdSuddelegacion(Long idSuddelegacion) {
		this.idSuddelegacion = idSuddelegacion;
	}

	/**
	 * @return the centroTrabajo
	 */
	public Long getCentroTrabajo() {
		return centroTrabajo;
	}
	/**
	 * @param centroTrabajo the centroTrabajo to set
	 */
	public void setCentroTrabajo(Long centroTrabajo) {
		this.centroTrabajo = centroTrabajo;
	}

	/**
	 * @return the numeroLada
	 */
	public Long getNumeroLada() {
		return numeroLada;
	}
	/**
	 * @param numeroLada the numeroLada to set
	 */
	public void setNumeroLada(Long numeroLada) {
		this.numeroLada = numeroLada;
	}

	/**
	 * @return the numeroTelefonico
	 */
	public Long getNumeroTelefonico() {
		return numeroTelefonico;
	}
	/**
	 * @param numeroTelefonico the numeroTelefonico to set
	 */
	public void setNumeroTelefonico(Long numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	/**
	 * @return the nUM_EXTENSION_CONTACTO
	 */
	public Long getNumExtension() {
		return numExtension;
	}
	/**
	 * @param nUM_EXTENSION_CONTACTO the nUM_EXTENSION_CONTACTO to set
	 */
	public void setNumExtension(Long numExtension) {
		this.numExtension = numExtension;
	}

	/**
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	/**
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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
	 * @return the idUMF
	 */
	public Long getIdUMF() {
		return idUMF;
	}
	/**
	 * @param idUMF the idUMF to set
	 */
	public void setIdUMF(Long idUMF) {
		this.idUMF = idUMF;
	}
	
	
}
