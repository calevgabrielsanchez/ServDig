package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto;

import java.math.BigInteger;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
 
public class IfeDto extends AbstractModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger anioRegistro;
    private String claveElector;
    private String estado;
    private String municipio;
    private String localidad;
    private Long asentamiento;
    private String emision;
    private String codigoSeguridad;
    private String folio;
    private String tipoCredencial;
    
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public BigInteger getAnioRegistro() {
		return anioRegistro;
	}
	public void setAnioRegistro(BigInteger anioRegistro) {
		this.anioRegistro = anioRegistro;
	}
	public String getClaveElector() {
		return claveElector;
	}
	public void setClaveElector(String claveElector) {
		this.claveElector = claveElector;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public Long getAsentamiento() {
		return asentamiento;
	}
	public void setAsentamiento(Long asentamiento) {
		this.asentamiento = asentamiento;
	}
	public String getEmision() {
		return emision;
	}
	public void setEmision(String emision) {
		this.emision = emision;
	}
	public String getCodigoSeguridad() {
		return codigoSeguridad;
	}
	public void setCodigoSeguridad(String codigoSeguridad) {
		this.codigoSeguridad = codigoSeguridad;
	}
	public String getTipoCredencial() {
		return tipoCredencial;
	}
	public void setTipoCredencial(String tipoCredencial) {
		this.tipoCredencial = tipoCredencial;
	}
    
}
