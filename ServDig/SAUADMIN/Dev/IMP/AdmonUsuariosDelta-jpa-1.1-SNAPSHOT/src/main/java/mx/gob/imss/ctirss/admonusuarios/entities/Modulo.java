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
 * 
 * @author Alan Garcia
 * Clase de entidad para el mapeo de la tabla DIC_MODULO 
 *
 */
@Entity
@Table(name="DIC_MODULO")
@NamedQueries ({
	@NamedQuery(name = "Modulo.findAll", query = "select m from Modulo m"),
	@NamedQuery(name = "Modulo.findById", query = "select m from Modulo m where m.idModulo = :id")
	}
)
public class Modulo implements Serializable	 {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CVE_ID_MODULO", nullable = false, updatable = false)
	private Long idModulo;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable = false, updatable = false)
	private Date registroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA", nullable = true, updatable = false)
	private Date registroBaja;
	
	@Column(name="DES_MODULO", nullable = false, updatable = false)
	private String descripcion;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO", nullable = true, updatable = false)
	private Date registroActualizado;

	
	/**
	 * @return the idModulo
	 */
	public Long getIdModulo() {
		return idModulo;
	}
	/**
	 * @param idModulo the idModulo to set
	 */
	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	
}
