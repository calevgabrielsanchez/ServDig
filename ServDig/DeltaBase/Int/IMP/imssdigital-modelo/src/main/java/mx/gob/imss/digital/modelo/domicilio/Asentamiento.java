package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Modelo que representa el asentamiento de una persona o entidad.
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "asentamiento", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "asentamiento", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Asentamiento implements Serializable {

	/**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
	/**
     * Identificador del asentamiento
     */
    private String clave;
	/**
     * Asentamiento
     */
	private String nombre;
	/**
     * Localidad del asentamiento
     */
	private Localidad localidad;
	/**
     * Municipio del asentamiento
     */
	private Municipio municipio;
	/**
     * C.P. del asentamiento
     */
	private String codigoPostal;
	/**
     * Tipo de asentamiento
     */
	private TipoAsentamiento tipoAsentamiento;
	/**
     * Periodo de asentamiento
     */
	private long periodo;
	
	
	
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Localidad getLocalidad() {
		return localidad;
	}
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	public Municipio getMunicipio() {
		return municipio;
	}
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public TipoAsentamiento getTipoAsentamiento() {
		return tipoAsentamiento;
	}
	public void setTipoAsentamiento(TipoAsentamiento tipoAsentamiento) {
		this.tipoAsentamiento = tipoAsentamiento;
	}
	public long getPeriodo() {
		return periodo;
	}
	public void setPeriodo(long periodo) {
		this.periodo = periodo;
	}
	
}
