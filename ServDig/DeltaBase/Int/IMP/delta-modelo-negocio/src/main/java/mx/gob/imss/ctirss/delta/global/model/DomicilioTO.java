package mx.gob.imss.ctirss.delta.global.model;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;

public class DomicilioTO extends AbstractModel {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 5367306800892920036L;

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
	
	private TipoAmbito ambito;
	
	private Vialidad vialidadPrimaria;
	
	private Vialidad vialidadReferenciaPrimaria;
	
	private Vialidad vialidadReferenciaSecundaria;
	
	private Vialidad vialidadReferenciaPosterior;
	
	private TipoDomicilio tipoDomicilio;

	private String descripcion;

	public Integer getClave() {
		return clave;
	}

	public void setClave(Integer clave) {
		this.clave = clave;
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

	public Integer getNumExterior1() {
		return numExterior1;
	}

	public void setNumExterior1(Integer numExterior1) {
		this.numExterior1 = numExterior1;
	}

	public String getNumExteriorAlf() {
		return numExteriorAlf;
	}

	public void setNumExteriorAlf(String numExteriorAlf) {
		this.numExteriorAlf = numExteriorAlf;
	}

	public Integer getNumExterior2() {
		return numExterior2;
	}

	public void setNumExterior2(Integer numExterior2) {
		this.numExterior2 = numExterior2;
	}

	public Integer getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(Integer numInterior) {
		this.numInterior = numInterior;
	}

	public String getNumInteriorAlf() {
		return numInteriorAlf;
	}

	public void setNumInteriorAlf(String numInteriorAlf) {
		this.numInteriorAlf = numInteriorAlf;
	}

	public BigDecimal getLatitud() {
		return latitud;
	}

	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	public BigDecimal getLongitud() {
		return longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public CodigoPostal getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(CodigoPostal codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Asentamiento getAsentamiento() {
		return asentamiento;
	}

	public void setAsentamiento(Asentamiento asentamiento) {
		this.asentamiento = asentamiento;
	}

	public TipoAmbito getAmbito() {
		return ambito;
	}

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

	public TipoDomicilio getTipoDomicilio() {
		return tipoDomicilio;
	}

	public void setTipoDomicilio(TipoDomicilio tipoDomicilio) {
		this.tipoDomicilio = tipoDomicilio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
