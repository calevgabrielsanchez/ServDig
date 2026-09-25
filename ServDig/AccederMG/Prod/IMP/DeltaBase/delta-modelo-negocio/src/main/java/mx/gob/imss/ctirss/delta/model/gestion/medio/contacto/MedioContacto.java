/**
 *  Medio de contacto, se refiere a los medios (correo, telefono, cuenta de twitter, etc) por los cuales
 *  se puede contactar a un ente (persona, domicilio, etc).
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:MedioContacto.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.gestion.medio.contacto
 *  @Fecha:03/05/2012
 */
package mx.gob.imss.ctirss.delta.model.gestion.medio.contacto;

import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.ViewModelItem;

/**
 * @author Lucio Duran Silva
 *
 */
public class MedioContacto extends ViewModelItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4854919993989243797L;

	//La Clave del medio de contacto.
	private Long clave;
	
	//El tipo de medio de contacto
	private TipoMedioContacto tipoMedioContacto;
	//
	private String desFormaContacto;
	
	
	// Atributos para mantener el estatus del módulo de administracion
	private EstadoAdministracionEnum estadoAdministracionMedioContacto;
	private EstadoAdministracionEnum estadoAdministracionAnteriorMedioContacto;
	
	/**
	 * @return the tipoMedioContacto
	 */
	public TipoMedioContacto getTipoMedioContacto() {
		return tipoMedioContacto;
	}

	/**
	 * @param tipoMedioContacto the tipoMedioContacto to set
	 */
	public void setTipoMedioContacto(TipoMedioContacto tipoMedioContacto) {
		this.tipoMedioContacto = tipoMedioContacto;
	}

	/**
	 * @return the clave
	 */
	public Long getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(Long clave) {
		this.clave = clave;
	}

	public String getDesFormaContacto() {
		return desFormaContacto;
	}

	public void setDesFormaContacto(String desFormaContacto) {
		this.desFormaContacto = desFormaContacto;
	}

	/**
	 * @return the estadoAdministracionMedioContacto
	 */
	public EstadoAdministracionEnum getEstadoAdministracionMedioContacto() {
		return estadoAdministracionMedioContacto;
	}

	/**
	 * @param estadoAdministracionMedioContacto the estadoAdministracionMedioContacto to set
	 */
	public void setEstadoAdministracionMedioContacto(
			EstadoAdministracionEnum estadoAdministracionMedioContacto) {
		this.estadoAdministracionMedioContacto = estadoAdministracionMedioContacto;
	}

	/**
	 * @return the estadoAdministracionAnteriorMedioContacto
	 */
	public EstadoAdministracionEnum getEstadoAdministracionAnteriorMedioContacto() {
		return estadoAdministracionAnteriorMedioContacto;
	}

	/**
	 * @param estadoAdministracionAnteriorMedioContacto the estadoAdministracionAnteriorMedioContacto to set
	 */
	public void setEstadoAdministracionAnteriorMedioContacto(
			EstadoAdministracionEnum estadoAdministracionAnteriorMedioContacto) {
		this.estadoAdministracionAnteriorMedioContacto = estadoAdministracionAnteriorMedioContacto;
	}
}
