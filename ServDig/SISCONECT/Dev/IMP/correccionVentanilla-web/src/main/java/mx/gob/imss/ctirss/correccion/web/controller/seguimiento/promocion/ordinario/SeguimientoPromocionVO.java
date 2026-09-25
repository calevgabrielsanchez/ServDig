/**
 * @author Oscar German Beltran Ortega
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.ordinario;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagos;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;

/**
 * Objeto SeguimientoPromocionVO que encapsula los objetos manejados en capa de presentación
 * del seguimiento de la promoción
 *
 */   
public class SeguimientoPromocionVO extends ControlTabs{

	private Integer tipoPromocion;
	
	
	private CrtPromocion crtPromocion;
	private CrtInvitacion crtInvitacion;
	private CrtRegulapagos crtRegulapagos;
	private CrtRegulapagosdet crtRegulapagosdet;
	
	
	/**
	 * Metodo que obtiene el valor del atributo  crtPromocion
	 * @return  crtPromocion
	 */
	public CrtPromocion getCrtPromocion() {
		return crtPromocion;
	}
	/**
	 * Metodo que asigna un valor al atributo crtPromocion
	 * @param crtPromocion the crtPromocion to set
	 */
	public void setCrtPromocion(CrtPromocion crtPromocion) {
		this.crtPromocion = crtPromocion;
	}
	/**
	 * Metodo que obtiene el valor del atributo  crtInvitacion
	 * @return  crtInvitacion
	 */
	public CrtInvitacion getCrtInvitacion() {
		return crtInvitacion;
	}
	/**
	 * Metodo que asigna un valor al atributo crtInvitacion
	 * @param crtInvitacion the crtInvitacion to set
	 */
	public void setCrtInvitacion(CrtInvitacion crtInvitacion) {
		this.crtInvitacion = crtInvitacion;
	}
	/**
	 * Metodo que obtiene el valor del atributo  crtRegulapagos
	 * @return  crtRegulapagos
	 */
	public CrtRegulapagos getCrtRegulapagos() {
		return crtRegulapagos;
	}
	/**
	 * Metodo que asigna un valor al atributo crtRegulapagos
	 * @param crtRegulapagos the crtRegulapagos to set
	 */
	public void setCrtRegulapagos(CrtRegulapagos crtRegulapagos) {
		this.crtRegulapagos = crtRegulapagos;
	}
	/**
	 * Metodo que obtiene el valor del atributo  crtRegulapagosdet
	 * @return  crtRegulapagosdet
	 */
	public CrtRegulapagosdet getCrtRegulapagosdet() {
		return crtRegulapagosdet;
	}
	/**
	 * Metodo que asigna un valor al atributo crtRegulapagosdet
	 * @param crtRegulapagosdet the crtRegulapagosdet to set
	 */
	public void setCrtRegulapagosdet(CrtRegulapagosdet crtRegulapagosdet) {
		this.crtRegulapagosdet = crtRegulapagosdet;
	}
	public Integer getTipoPromocion() {
		return tipoPromocion;
	}
	public void setTipoPromocion(Integer tipoPromocion) {
		this.tipoPromocion = tipoPromocion;
	}

	
	
	
}
