/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:Usuario.java
 *  @Paquete:mx.gob.imss.delta.model
 *  @Fecha:07/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.model;

import java.util.Date;	

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;


/**
 * @author Lucio Duran Silva
 *
 */
public class Usuario extends AbstractModel {
	
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 8630947870585982447L;
	//
	/**
	 * 
	 */
	private String cveUsuario;
	private String usuario;
	private String password;
	private Date fecha;
	private Integer cvePk;
	private Rol rol;
	
	private Integer idDelegacion;
	private Integer idSubDelegacion;
	
	//private SacRole rol;
	//private String realPass;
	/*
	private String cveCiz;
	private int delegacion;
	private int subdelegacion;
	private String desDelegacion;
	private String desSubdelegacion;
	*/

	/**
	 * @return the usuario
	 */
	
	/**
	 * @return the cveUsuario
	 */
	public String getCveUsuario() {
		return cveUsuario;
	}

	/**
	 * @param cveUsuario the cveUsuario to set
	 */
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * 
	 */
	/*
	 * HLA Agregado 07/05/12
	 * Variable Rol
	 * */

	/**
	 * @return the usuario
	 */
	
	public Integer getCvePk() {
		return cvePk;
	}
	
	/**
	 * @param usuario the usuario to set
	 */
	
	public void setCvePk(Integer cvePk){
		this.cvePk = cvePk;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the rol
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @return the idDelegacion
	 */
	public Integer getIdDelegacion() {
		return idDelegacion;
	}

	/**
	 * @param idDelegacion the idDelegacion to set
	 */
	public void setIdDelegacion(Integer idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	/**
	 * @return the idSubDelegacion
	 */
	public Integer getIdSubDelegacion() {
		return idSubDelegacion;
	}

	/**
	 * @param idSubDelegacion the idSubDelegacion to set
	 */
	public void setIdSubDelegacion(Integer idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", password=" + password
				+ ", fecha=" + fecha + ", cvePk=" + cvePk + ", rol=" + rol
				+ ", idDelegacion=" + idDelegacion + ", idSubDelegacion="
				+ idSubDelegacion + "]";
	}
}