package mx.gob.imss.ctirss.delta.persistence;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIT_DOM_ESCRITO_DESACUERDO")
@OnSearchLlavePrimaria(atributos={"cveIdDomEscDes"})
public class DitDomicilioEscDes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_DOM_ESCRITO_DESACUERDO_GENERATOR")
	@Column(name = "CVE_ID_DOM_ESCRITO_DESACUERDO", nullable = false)
	private long cveIdDomEscDes;
	
	@Column(name = "CVE_ID_ESCRITO_DESACUERDO")
	private long cveIdDesacuerdo;
	
	@Column(name = "REF_FOLIO_RECEPCION")
	private String refFolioRecepcion;
	
	@Column(name = "DES_DOM_CALLE")
	private String desDomCalle;
	
	@Column(name = "DOM_NUM_EXTERIOR")
	private Integer domNumExterior;
	
	@Column(name = "DOM_NUM_INTERIOR")
	private Integer domNumInterior;
	
	@Column(name = "REF_CODIGO_POSTAL")
	private String refCodPostal;
	
	@Column(name = "DES_CIUDAD")
	private String	desCiudad;
	
	@Column(name = "DES_ESTADO")
	private String desEstado;
	
	@Column(name = "CVE_ID_TIPO_DOMICILIO")
	private long cveIdTipoDomicilio;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA", nullable = false)
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public long getCveIdDomEscDes() {
		return cveIdDomEscDes;
	}

	public void setCveIdDomEscDes(long cveIdDomEscDes) {
		this.cveIdDomEscDes = cveIdDomEscDes;
	}

	public long getCveIdDesacuerdo() {
		return cveIdDesacuerdo;
	}

	public void setCveIdDesacuerdo(long cveIdDesacuerdo) {
		this.cveIdDesacuerdo = cveIdDesacuerdo;
	}

	public String getRefFolioRecepcion() {
		return refFolioRecepcion;
	}

	public void setRefFolioRecepcion(String refFolioRecepcion) {
		this.refFolioRecepcion = refFolioRecepcion;
	}

	public String getDesDomCalle() {
		return desDomCalle;
	}

	public void setDesDomCalle(String desDomCalle) {
		this.desDomCalle = desDomCalle;
	}

	public Integer getDomNumExterior() {
		return domNumExterior;
	}

	public void setDomNumExterior(Integer domNumExterior) {
		this.domNumExterior = domNumExterior;
	}

	public Integer getDomNumInterior() {
		return domNumInterior;
	}

	public void setDomNumInterior(Integer domNumInterior) {
		this.domNumInterior = domNumInterior;
	}

	public String getRefCodPostal() {
		return refCodPostal;
	}

	public void setRefCodPostal(String refCodPostal) {
		this.refCodPostal = refCodPostal;
	}

	public String getDesCiudad() {
		return desCiudad;
	}

	public void setDesCiudad(String desCiudad) {
		this.desCiudad = desCiudad;
	}

	public String getDesEstado() {
		return desEstado;
	}

	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
	}

	public long getCveIdTipoDomicilio() {
		return cveIdTipoDomicilio;
	}

	public void setCveIdTipoDomicilio(long cveIdTipoDomicilio) {
		this.cveIdTipoDomicilio = cveIdTipoDomicilio;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
}