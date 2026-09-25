/**
 * AbstractModel.java
 * @package mx.gob.imss.delta.framework.base.model
 * @project delta-framework-base	
 */
package mx.gob.imss.ctirss.correccion.framework.base.model;

import java.io.Serializable;

import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.session.UserSession;


/**
 * @author Lucio Duran Silva
 * @author Marco A Nieto Plett
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
public abstract class AbstractModel implements Serializable{
	
	//atributo para poder enviar mensajes de reglas de negocio al frot
	@Transient
	private String error;

	@Transient
	private String exito;

	/**
	 * Variable que controla el estado de una solicitud
	 * de correccian 1) No Aceptada, 2) Aceptada , 3) Cancelada.<br>
	 * 
	 * La implementacian se hace a travez del objeto 
	 * <code>ValidaSolicitudCorreccion.estadoSolicitud</code>. el
	 * cual funciona como servicio.<br><br>
	 * 
	 * Ejemplo Implementacian: <br>
	 * <code>@Autowired<br>
	 *       private ValidaSolicitudCorreccion validaSolicitudService;
	 *  </code>
	 */
	@Transient
	private Integer cveStatusCorreccion;
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	@Transient
	private UserSession usuarioFirmado;
	
	/**
	 *  Otorga el estado de una solicitud de correccian
	 * @return 1) No Aceptada, 2) Aceptada , 3) Cancelada.
	 */
	public Integer getCveStatusCorreccion() {
		return cveStatusCorreccion;
	}

	/**
	 * Permite ingresar el estado de una solicitud de correcian
	 * @param cveStatusCorreccion 1) No Aceptada, 2) Aceptada , 3) Cancelada.
	 */
	public void setCveStatusCorreccion(Integer cveStatusCorreccion) {
		this.cveStatusCorreccion = cveStatusCorreccion;
	}

	/**
	 * @return the usuarioFirmado
	 */
	public UserSession getUsuarioFirmado() {
		return usuarioFirmado;
	}

	/**
	 * @param usuarioFirmado the usuarioFirmado to set
	 */
	public void setUsuarioFirmado(UserSession usuarioFirmado) {
		this.usuarioFirmado = usuarioFirmado;
	}

	public String getExito() {
		return exito;
	}

	public void setExito(String exito) {
		this.exito = exito;
	}
	
	
	

}
