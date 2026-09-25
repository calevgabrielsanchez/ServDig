/**
 * @author Gerardo Salazar Vega
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 24/05/2012
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.satica;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegCancelacionGenericoTabVO;
import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagos;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;

/**
 * Objeto SaticASeguimientoPromocionVO que encapsula los objetos manejados en capa de presentación
 * del seguimiento de la promoción para SaticA
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeguimientoPromocionSaticaVO extends ControlTabs {
	
	private String cvePromocion;
	private String cveFkPatron;
	private String rolUsuario;
	private String estatusPromocion;
	private String descripcionCriterioseleccion;
	private String folioPromocion; 
	private String fechaOficioPromocion;
	private String numeroOficioPromocion;
	private String calle;
	private String colonia;
	private String numExterior;
	private String numInterior;
	private String estado;
	private String municipio;
	private String codigoPostal;
	private String registroPatronal;
	private String razonSocial;
	private String registroPatronalDeteccion;
	private String razonSocialDeteccion;
	
	/**
	 * @return the registroPatronalDeteccion
	 */
	public String getRegistroPatronalDeteccion() {
		return registroPatronalDeteccion;
	}
	/**
	 * @param registroPatronalDeteccion the registroPatronalDeteccion to set
	 */
	public void setRegistroPatronalDeteccion(String registroPatronalDeteccion) {
		this.registroPatronalDeteccion = registroPatronalDeteccion;
	}
	/**
	 * @return the razonSocialDeteccion
	 */
	public String getRazonSocialDeteccion() {
		return razonSocialDeteccion;
	}
	/**
	 * @param razonSocialDeteccion the razonSocialDeteccion to set
	 */
	public void setRazonSocialDeteccion(String razonSocialDeteccion) {
		this.razonSocialDeteccion = razonSocialDeteccion;
	}
	private SeguimientoSaticATabVO saticaSeguimientoTabVO;	
	
	/**
	 * @return cvePromocion
	 */
	public String getCvePromocion() {
		return cvePromocion;
	}
	/**
	 * @param cvePromocion the cvePromocion to set
	 */
	public void setCvePromocion(String cvePromocion) {
		this.cvePromocion = cvePromocion;
	}

	/**
	 * @return the cveFkPatron
	 */
	public String getCveFkPatron() {
		return cveFkPatron;
	}
	/**
	 * @param cveFkPatron
	 */
	public void setCveFkPatron(String cveFkPatron) {
		this.cveFkPatron = cveFkPatron;
	}

	/**
	 * @return the rolUsuario
	 */
	public String getRolUsuario() {
		return rolUsuario;
	}
	/**
	 * @param rolUsuario the rolUsuario to set
	 */
	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	/**
	 * @return the estatusPromocion
	 */
	public String getEstatusPromocion() {
		return estatusPromocion;
	}
	/**
	 * @param estatusPromocion the estatusPromocion to set
	 */
	public void setEstatusPromocion(String estatusPromocion) {
		this.estatusPromocion = estatusPromocion;
	}
	/**
	 * @return the descripcionCriterioseleccion
	 */
	public String getDescripcionCriterioseleccion() {
		return descripcionCriterioseleccion;
	}
	/**
	 * @param descripcionCriterioseleccion the descripcionCriterioseleccion to set
	 */
	public void setDescripcionCriterioseleccion(String descripcionCriterioseleccion) {
		this.descripcionCriterioseleccion = descripcionCriterioseleccion;
	}
	/**
	 * @return the folioPromocion
	 */
	public String getFolioPromocion() {
		return folioPromocion;
	}
	/**
	 * @param folioPromocion the folioPromocion to set
	 */
	public void setFolioPromocion(String folioPromocion) {
		this.folioPromocion = folioPromocion;
	}
	/**
	 * @return the fechaOficioPromocion
	 */
	public String getFechaOficioPromocion() {
		return fechaOficioPromocion;
	}
	/**
	 * @param fechaOficioPromocion the fechaOficioPromocion to set
	 */
	public void setFechaOficioPromocion(String fechaOficioPromocion) {
		this.fechaOficioPromocion = fechaOficioPromocion;
	}
	/**
	 * @return the numeroOficioPromocion
	 */
	public String getNumeroOficioPromocion() {
		return numeroOficioPromocion;
	}
	/**
	 * @param numeroOficioPromocion the numeroOficioPromocion to set
	 */
	public void setNumeroOficioPromocion(String numeroOficioPromocion) {
		this.numeroOficioPromocion = numeroOficioPromocion;
	}
	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}
	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}
	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}
	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	/**
	 * @return the numExterior
	 */
	public String getNumExterior() {
		return numExterior;
	}
	/**
	 * @param numExterior the numExterior to set
	 */
	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}
	/**
	 * @return the numinterior
	 */
	public String getNumInterior() {
		return numInterior;
	}
	/**
	 * @param numinterior the numinterior to set
	 */
	public void setNumInterior(String numinterior) {
		this.numInterior = numinterior;
	}
	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	/**
	 * @return the registroPatronal
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	/**
	 * @param registroPatronal the registroPatronal to set
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @return the saticaSeguimientoTabVO
	 */
	public SeguimientoSaticATabVO getSaticaSeguimientoTabVO() {
		return saticaSeguimientoTabVO;
	}
	/**
	 * @param saticaSeguimientoTabVO the saticaSeguimientoTabVO to set
	 */
	public void setSaticaSeguimientoTabVO(
			SeguimientoSaticATabVO saticaSeguimientoTabVO) {
		this.saticaSeguimientoTabVO = saticaSeguimientoTabVO;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}


}