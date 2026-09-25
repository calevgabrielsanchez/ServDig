/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:Domicilio.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.domicilio
 *  @Fecha:02/04/2012
 */
package mx.gob.imss.ctirss.delta.model.domicilio;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Domicilio extends AbstractModel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//TODO: Cambiar a Long por la base de datos.
	private Integer clave;
	
	private String calle;
	
	private String colonia;
	
	private Integer numExterior1;
	
	private String numExteriorAlf;
	
	private Integer numExterior2;
	
	private Integer numInterior;
	
	private String numInteriorAlf;
	
	private BigDecimal latitud;
	
	private BigDecimal longitud;
	
	private CodigoPostal codigoPostal;
	
	private Asentamiento asentamiento;
	
	private Localidad localidad;
	
	private TipoAmbito ambito;
	
	private Vialidad vialidadPrimaria;
	
	private Vialidad vialidadReferenciaPrimaria;
	
	private Vialidad vialidadReferenciaSecundaria;
	
	private Vialidad vialidadReferenciaPosterior;
	
	private Integer tipoBusquedaVialidad;
	
	// Este tipo de domicilio hace referencia a la tabla dg_cat_tipo_dom
	private TipoDomicilio tipoDomicilio;

	private String descripcion;
	
	//Se ingresan los objetos que complementan el domicilios en vias de comunicación
	private DomicilioCarretera domicilioCarretera;
	private DomicilioCamino domicilioCamino;
	
	
	/* Propiedades utilizada en el mï¿½dulo de administraciï¿½n de domicilios,
	 * para saber quï¿½ operaciï¿½n se hizo sobre el domicilio.
	 */
	private EstadoAdministracionEnum estadoAdministracionDomicilio;
	private EstadoAdministracionEnum estadoAdministracionAnteriorDomicilio;
	
	// Este tipo de domicilio hace referencia a la tabla dic_tipo_domicilio
	private TipoDomicilio dicTipoDomicilio;
	
	//Variable que almacena el valor de CveIdPersonafDom de la tabla de DIT_PERSONAF_DOM
	private Long cveIdPersonafDom;
	
	/**
	 * @return the clave
	 */
	public Integer getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(Integer clave) {
		this.clave = clave;
	}

	/**
	 * @return the numExterior1
	 */
	public Integer getNumExterior1() {
		return numExterior1;
	}

	/**
	 * @param numExterior1 the numExterior1 to set
	 */
	public void setNumExterior1(Integer numExterior1) {
		this.numExterior1 = numExterior1;
	}

	/**
	 * @return the numExteriorAlf
	 */
	public String getNumExteriorAlf() {
		return numExteriorAlf;
	}

	/**
	 * @param numExteriorAlf the numExteriorAlf to set
	 */
	public void setNumExteriorAlf(String numExteriorAlf) {
		this.numExteriorAlf = numExteriorAlf;
	}

	/**
	 * @return the numExterior2
	 */
	public Integer getNumExterior2() {
		return numExterior2;
	}

	/**
	 * @param numExterior2 the numExterior2 to set
	 */
	public void setNumExterior2(Integer numExterior2) {
		this.numExterior2 = numExterior2;
	}

	/**
	 * @return the numInterior
	 */
	public Integer getNumInterior() {
		return numInterior;
	}

	/**
	 * @param numInterior the numInterior to set
	 */
	public void setNumInterior(Integer numInterior) {
		this.numInterior = numInterior;
	}

	/**
	 * @return the numInteriorAlf
	 */
	public String getNumInteriorAlf() {
		return numInteriorAlf;
	}

	/**
	 * @param numInteriorAlf the numInteriorAlf to set
	 */
	public void setNumInteriorAlf(String numInteriorAlf) {
		this.numInteriorAlf = numInteriorAlf;
	}

	/**
	 * @return the latitud
	 */
	public BigDecimal getLatitud() {
		return latitud;
	}

	/**
	 * @param latitud the latitud to set
	 */
	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	/**
	 * @return the longitud
	 */
	public BigDecimal getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	/**
	 * @return the codigoPostal
	 */
	public CodigoPostal getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(CodigoPostal codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return the asentamiento
	 */
	public Asentamiento getAsentamiento() {
		return asentamiento;
	}

	/**
	 * @param asentamiento the asentamiento to set
	 */
	public void setAsentamiento(Asentamiento asentamiento) {
		this.asentamiento = asentamiento;
	}

	/**
	 * @return the ambito
	 */
	public TipoAmbito getAmbito() {
		return ambito;
	}

	/**
	 * @param ambito the ambito to set
	 */
	public void setAmbito(TipoAmbito ambito) {
		this.ambito = ambito;
	}

	public Vialidad getVialidadPrimaria() {
		return vialidadPrimaria;
	}

	public void setVialidadPrimaria(Vialidad vialidadPrimaria) {
		this.vialidadPrimaria = vialidadPrimaria;
	}

	public Vialidad getVialidadReferenciaPrimaria() {
		return vialidadReferenciaPrimaria;
	}

	public void setVialidadReferenciaPrimaria(Vialidad vialidadReferenciaPrimaria) {
		this.vialidadReferenciaPrimaria = vialidadReferenciaPrimaria;
	}

	public Vialidad getVialidadReferenciaSecundaria() {
		return vialidadReferenciaSecundaria;
	}

	public void setVialidadReferenciaSecundaria(
			Vialidad vialidadReferenciaSecundaria) {
		this.vialidadReferenciaSecundaria = vialidadReferenciaSecundaria;
	}

	public Vialidad getVialidadReferenciaPosterior() {
		return vialidadReferenciaPosterior;
	}

	public void setVialidadReferenciaPosterior(Vialidad vialidadReferenciaPosterior) {
		this.vialidadReferenciaPosterior = vialidadReferenciaPosterior;
	}

	@Override
	public String toString() {
		return "Domicilio [ambito=" + ambito + ", asentamiento=" + asentamiento
				+ ", clave=" + clave + ", codigoPostal=" + codigoPostal
				+ ", latitud=" + latitud + ", longitud=" + longitud
				+ ", numExterior1=" + numExterior1 + ", numExterior2="
				+ numExterior2 + ", numExteriorAlf=" + numExteriorAlf
				+ ", numInterior=" + numInterior + ", numInteriorAlf="
				+ numInteriorAlf + ", vialidadPrimaria=" + vialidadPrimaria
				+ ", vialidadReferenciaPosterior="
				+ vialidadReferenciaPosterior + ", vialidadReferenciaPrimaria="
				+ vialidadReferenciaPrimaria
				+ ", vialidadReferenciaSecundaria="
				+ vialidadReferenciaSecundaria + ", getAmbito()=" + getAmbito()
				+ ", getAsentamiento()=" + getAsentamiento() + ", getClave()="
				+ getClave() + ", getCodigoPostal()=" + getCodigoPostal()
				+ ", getLatitud()=" + getLatitud() + ", getLongitud()="
				+ getLongitud() + ", getNumExterior1()=" + getNumExterior1()
				+ ", getNumExterior2()=" + getNumExterior2()
				+ ", getNumExteriorAlf()=" + getNumExteriorAlf()
				+ ", getNumInterior()=" + getNumInterior()
				+ ", getNumInteriorAlf()=" + getNumInteriorAlf()
				+ ", getVialidadPrimaria()=" + getVialidadPrimaria()
				+ ", getVialidadReferenciaPosterior()="
				+ getVialidadReferenciaPosterior()
				+ ", getVialidadReferenciaPrimaria()="
				+ getVialidadReferenciaPrimaria()
				+ ", getVialidadReferenciaSecundaria()="
				+ getVialidadReferenciaSecundaria() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	/**
	 * @return the tipoDomicilio
	 */
	public TipoDomicilio getTipoDomicilio() {
		return tipoDomicilio;
	}

	/**
	 * @param tipoDomicilio the tipoDomicilio to set
	 */
	public void setTipoDomicilio(TipoDomicilio tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
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
	 * @return the estadoAdministracionDomicilio
	 */
	public EstadoAdministracionEnum getEstadoAdministracionDomicilio() {
		return estadoAdministracionDomicilio;
	}

	/**
	 * @param estadoAdministracionDomicilio the estadoAdministracionDomicilio to set
	 */
	public void setEstadoAdministracionDomicilio(
			EstadoAdministracionEnum estadoAdministracionDomicilio) {
		this.estadoAdministracionDomicilio = estadoAdministracionDomicilio;
	}

	public EstadoAdministracionEnum getEstadoAdministracionAnteriorDomicilio() {
		return estadoAdministracionAnteriorDomicilio;
	}

	public void setEstadoAdministracionAnteriorDomicilio(
			EstadoAdministracionEnum estadoAdministracionAnteriorDomicilio) {
		this.estadoAdministracionAnteriorDomicilio = estadoAdministracionAnteriorDomicilio;
	}

	public TipoDomicilio getDicTipoDomicilio() {
		return dicTipoDomicilio;
	}

	public void setDicTipoDomicilio(TipoDomicilio dicTipoDomicilio) {
		this.dicTipoDomicilio = dicTipoDomicilio;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	
	public DomicilioCarretera getDomicilioCarretera() {
		return domicilioCarretera;
	}

	public void setDomicilioCarretera(DomicilioCarretera domicilioCarretera) {
		this.domicilioCarretera = domicilioCarretera;
	}

	public DomicilioCamino getDomicilioCamino() {
		return domicilioCamino;
	}

	public void setDomicilioCamino(DomicilioCamino domicilioCamino) {
		this.domicilioCamino = domicilioCamino;
	}

	public Integer getTipoBusquedaVialidad() {
		return tipoBusquedaVialidad;
	}

	public void setTipoBusquedaVialidad(Integer tipoBusquedaVialidad) {
		this.tipoBusquedaVialidad = tipoBusquedaVialidad;
	}

	public Long getCveIdPersonafDom() {
		return cveIdPersonafDom;
	}

	public void setCveIdPersonafDom(Long cveIdPersonafDom) {
		this.cveIdPersonafDom = cveIdPersonafDom;
	}
}