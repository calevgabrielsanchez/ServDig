/**
 *  UsuarioSSO representa la informacion que se debe de recuperar de la autentificacion
 *  de los usuarios EXTERNOS e INTERNOS.
 *   
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:UsuarioSSO.java
 *  @Paquete:mx.gob.imss.delta.model
 *  @Fecha:20/07/2012
 */
package mx.gob.imss.ctirss.delta.framework.base.web.sso;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class UsuarioSSO extends AbstractModel {

	/**
	 * @category Clave del usuario
	 */
	private Integer idUsuario;
	
	/**
	 * @category Clave de la persona
	 */
	private Integer idPersona;
	
	/**
	 * @category Nombre del usuario autentificado
	 */
	private String nombre;
	
	/**
	 * @category Delegacion del usuario asignado
	 */
	private Integer delegacion;
	
	/**
	 * @category Subdelegacion del usuario
	 */
	private Integer subdelegacion;
	
	/**
	 * @category Unidad Medica Familiar
	 */
	private Integer umf;
	
	/**
	 * @category CURP
	 */
	private String curp;
	
	/**
	 * @category RFC
	 */
	private String rfc;
	
	/**
	 *  @category Perfil
	 */
	private String perfil;
	
	/**
	 *  @category sistemas
	 */
	private String[] sistemas;
	
	/**
	 *  @category perfiles
	 */
	private String[] perfiles;
	
	
	

	public String[] getSistemas() {
		return sistemas;
	}

	public void setSistemas(String[] sistemas) {
		this.sistemas = sistemas != null ? sistemas.clone() : null;
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

	

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	/**
	 * @return the delegacion
	 */
	public Integer getDelegacion() {
		return delegacion;
	}

	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(Integer delegacion) {
		this.delegacion = delegacion;
	}

	/**
	 * @return the subdelegacion
	 */
	public Integer getSubdelegacion() {
		return subdelegacion;
	}

	/**
	 * @param subdelegacion the subdelegacion to set
	 */
	public void setSubdelegacion(Integer subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	/**
	 * @return the umf
	 */
	public Integer getUmf() {
		return umf;
	}

	/**
	 * @param umf the umf to set
	 */
	public void setUmf(Integer umf) {
		this.umf = umf;
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

	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the idPersona
	 */
	public Integer getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the perfiles
	 */
	public String[] getPerfiles() {
		return perfiles;
	}

	/**
	 * @param perfiles the perfiles to set
	 */
	public void setPerfiles(String[] perfiles) {
		this.perfiles = perfiles != null ? perfiles.clone() : null;
	}
	
	
}
