/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:GeocoderDomicilio.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.model.domicilios
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.portal.model.domicilios;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class GeocoderDomicilio extends AbstractModel {
	
	
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;

	
	
	
	
	/**
	 * Minimal constructor
	 */
	public GeocoderDomicilio(){
		
	}
	
	
	
	/**
	 * Constructor from query
	 */
	
	public GeocoderDomicilio(String codigoPostal, String claveAsentamiento,
			String nombreAsentamiento, String claveLocalidad,
			String nombreLocalidad, String claveMunicipio,
			String nombreMunicipio, String claveEntidadFederativa,
			String nombreEntidadFederativa) {
		
		
		this.codigoPostal = codigoPostal;
		this.claveAsentamiento = claveAsentamiento;
		this.nombreAsentamiento = nombreAsentamiento;
		this.claveLocalidad = claveLocalidad;
		this.nombreLocalidad = nombreLocalidad;
		this.claveMunicipio = claveMunicipio;
		this.nombreMunicipio = nombreMunicipio;
		this.claveEntidadFederativa = claveEntidadFederativa;
		this.nombreEntidadFederativa = nombreEntidadFederativa;
		
	}
	

	/**
	 * Codigo Postal
	 */
	private String codigoPostal;
	
	
	/**
	 * Asentamiento
	 */
	private String nombreAsentamiento;
	
	private String claveAsentamiento;
	
	private String tipoAsentamiento;
	
	private String nombreTipoAsentamiento;
	
	/**
	 * Localidad
	 */
	
	private String claveLocalidad;
	
	private String nombreLocalidad;
	
	/**
	 * Municipio
	 */
	private String claveMunicipio;
	private String nombreMunicipio;
	
	
	/**
	 * Entidad federativoa
	 */
	
	private String claveEntidadFederativa;
	private String nombreEntidadFederativa;

	
	
	
	/**
	 * Calle
	 */
	
	private String calle;
	private String numero;
	
	
	/**
	 * Domicilio normalizado
	 */
	
	private String domicilioNormalizado;

	/**
	 * Complementarios normalizados
	 */

	private String tipoVialidad;
	private String cveTipoVialidad;
	
	private String vialidadRef1;
	private String vialidadRef2;
	private String vialidadRef3;
	
	private String cveVialidad1;
	private String cveVialidad2;
	private String cveVialidad3;
	


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
	 * @return the nombreAsentamiento
	 */
	public String getNombreAsentamiento() {
		return nombreAsentamiento;
	}



	/**
	 * @param nombreAsentamiento the nombreAsentamiento to set
	 */
	public void setNombreAsentamiento(String nombreAsentamiento) {
		this.nombreAsentamiento = nombreAsentamiento;
	}



	/**
	 * @return the claveAsentamiento
	 */
	public String getClaveAsentamiento() {
		return claveAsentamiento;
	}



	/**
	 * @param claveAsentamiento the claveAsentamiento to set
	 */
	public void setClaveAsentamiento(String claveAsentamiento) {
		this.claveAsentamiento = claveAsentamiento;
	}



	/**
	 * @return the tipoAsentamiento
	 */
	public String getTipoAsentamiento() {
		return tipoAsentamiento;
	}



	/**
	 * @param tipoAsentamiento the tipoAsentamiento to set
	 */
	public void setTipoAsentamiento(String tipoAsentamiento) {
		this.tipoAsentamiento = tipoAsentamiento;
	}



	/**
	 * @return the nombreTipoAsentamiento
	 */
	public String getNombreTipoAsentamiento() {
		return nombreTipoAsentamiento;
	}



	/**
	 * @param nombreTipoAsentamiento the nombreTipoAsentamiento to set
	 */
	public void setNombreTipoAsentamiento(String nombreTipoAsentamiento) {
		this.nombreTipoAsentamiento = nombreTipoAsentamiento;
	}



	/**
	 * @return the claveLocalidad
	 */
	public String getClaveLocalidad() {
		return claveLocalidad;
	}



	/**
	 * @param claveLocalidad the claveLocalidad to set
	 */
	public void setClaveLocalidad(String claveLocalidad) {
		this.claveLocalidad = claveLocalidad;
	}



	/**
	 * @return the nombreLocalidad
	 */
	public String getNombreLocalidad() {
		return nombreLocalidad;
	}



	/**
	 * @param nombreLocalidad the nombreLocalidad to set
	 */
	public void setNombreLocalidad(String nombreLocalidad) {
		this.nombreLocalidad = nombreLocalidad;
	}



	/**
	 * @return the claveMunicipio
	 */
	public String getClaveMunicipio() {
		return claveMunicipio;
	}



	/**
	 * @param claveMunicipio the claveMunicipio to set
	 */
	public void setClaveMunicipio(String claveMunicipio) {
		this.claveMunicipio = claveMunicipio;
	}



	/**
	 * @return the nombreMunicipio
	 */
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}



	/**
	 * @param nombreMunicipio the nombreMunicipio to set
	 */
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}



	/**
	 * @return the claveEntidadFederativa
	 */
	public String getClaveEntidadFederativa() {
		return claveEntidadFederativa;
	}



	/**
	 * @param claveEntidadFederativa the claveEntidadFederativa to set
	 */
	public void setClaveEntidadFederativa(String claveEntidadFederativa) {
		this.claveEntidadFederativa = claveEntidadFederativa;
	}



	/**
	 * @return the nombreEntidadFederativa
	 */
	public String getNombreEntidadFederativa() {
		return nombreEntidadFederativa;
	}



	/**
	 * @param nombreEntidadFederativa the nombreEntidadFederativa to set
	 */
	public void setNombreEntidadFederativa(String nombreEntidadFederativa) {
		this.nombreEntidadFederativa = nombreEntidadFederativa;
	}



	/**
	 * @return the domicilioNormalizado
	 * El domicilio normalizado comprende de la concatenacion 
	 * de los diferentes componentes del domicilio geografico
	 */
	public String getDomicilioNormalizado() {
		StringBuffer bfr = new StringBuffer();
		//Asentamiento
		bfr.append(this.nombreAsentamiento).append(", ");
		//Localidad
		bfr.append(this.nombreLocalidad).append(", ");
		//Municipio
		bfr.append(this.nombreMunicipio).append(", ");
		//Estado
		bfr.append(this.nombreEntidadFederativa);
		return bfr.toString();
	}



	/**
	 * @param domicilioNormalizado the domicilioNormalizado to set
	 */
	public void setDomicilioNormalizado(String domicilioNormalizado) {
		
	
		
		this.domicilioNormalizado = domicilioNormalizado;
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
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}



	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	
	

}
