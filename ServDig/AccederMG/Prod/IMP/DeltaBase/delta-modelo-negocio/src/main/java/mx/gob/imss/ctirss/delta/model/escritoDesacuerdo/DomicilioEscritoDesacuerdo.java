package mx.gob.imss.ctirss.delta.model.escritoDesacuerdo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class DomicilioEscritoDesacuerdo extends AbstractModel implements Serializable {

	private static final long serialVersionUID = 1777811626623115793L;

	private Long idDomEscrito;
	private Long idEscrito;
	private String folioRecepcion;
	private String desDomicilio;
	private Integer domNumExterior;
	private Integer domNumInterior;
	private String refCodPostal;
	private String	desCiudad;
	private String desEstado;
	private Long cveIdTipoDomicilio;
	private Date fechAlta;
	private Date fecactualiza;
	private Date fecBaja;

	public DomicilioEscritoDesacuerdo() {

	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getIdDomEscrito() {
		return idDomEscrito;
	}

	public void setIdDomEscrito(Long idDomEscrito) {
		this.idDomEscrito = idDomEscrito;
	}

	public Long getIdEscrito() {
		return idEscrito;
	}

	public void setIdEscrito(Long idEscrito) {
		this.idEscrito = idEscrito;
	}

	public String getFolioRecepcion() {
		return folioRecepcion;
	}

	public void setFolioRecepcion(String folioRecepcion) {
		this.folioRecepcion = folioRecepcion;
	}

	public String getDesDomicilio() {
		return desDomicilio;
	}

	public void setDesDomicilio(String desDomicilio) {
		this.desDomicilio = desDomicilio;
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

	public Long getCveIdTipoDomicilio() {
		return cveIdTipoDomicilio;
	}

	public void setCveIdTipoDomicilio(Long cveIdTipoDomicilio) {
		this.cveIdTipoDomicilio = cveIdTipoDomicilio;
	}

	public Date getFechAlta() {return fechAlta;}

	public void setFechAlta(Date fechAlta) { this.fechAlta = fechAlta; }

	public Date getFecactualiza() { return fecactualiza; }

	public void setFecactualiza(Date fecactualiza) { this.fecactualiza = fecactualiza; }

	public Date getFecBaja() { return fecBaja; }

	public void setFecBaja(Date fecBaja) { this.fecBaja = fecBaja; }
}