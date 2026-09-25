/**
 * 
 */
package mx.gob.imss.ctirss.correccion.framework.base.model;

import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.session.TipoCertificado;

/**
 * Clase que representa un documento electronico. <br>
 * Un objeto del modelo adquiere la naturaleza de documento electranico debido a
 * que tiene que ser firmado electronicamente <br>
 * Los datos de la firma electranica se almacenaran en la notaria.
 * 
 * @author vaguirre
 * 
 */
public abstract class AbstractDocumentoElectronicoModel extends AbstractModel {
	public static final String FORMATO_FECHA_CADENA_ORIGINAL = "dd-MM-aaaa HH:mm:ss";
	
	/**
	 * Representa el sello IMSS generado por el componente de firma electronica.
	 */
	@Transient
	private String selloIMSS;
	
	/**
	 * Representa la cadena original para generar la firma electronica
	 */
	@Transient
	private String cadenaOriginal;
	/**
	 * Representa la firma electronica (<code>pkcs7</code>);
	 */
	@Transient
	private String firmaElectronica;
	
	/**
	 * Representa la fehca y hora actual de la firma electronica;
	 */
	@Transient
	private String fechaCadenaOriginal;
	/**
	 * Folio emitido por la notaria.
	 */
	@Transient
	// TODO VAP hacerlo persistente.
	private Long folioNotarial;

	/**
	 * Indicador del tipo de certificado del patran.
	 */
	@Transient
	private TipoCertificado tipoCertificado;

	/**
	 * @return the cadenaOriginal
	 */
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	/**
	 * @param cadenaOriginal
	 *            the cadenaOriginal to set
	 */
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	/**
	 * @return the firmaElectronica
	 */
	public String getFirmaElectronica() {
		return firmaElectronica;
	}

	/**
	 * @param firmaElectronica
	 *            the firmaElectronica to set
	 */
	public void setFirmaElectronica(String firmaElectronica) {
		this.firmaElectronica = firmaElectronica;
	}

	/**
	 * @return the folioNotarial
	 */
	public Long getFolioNotarial() {
		return folioNotarial;
	}

	/**
	 * @param folioNotarial
	 *            the folioNotarial to set
	 */
	public void setFolioNotarial(Long folioNotarial) {
		this.folioNotarial = folioNotarial;
	}

	/**
	 * @return the tipoCertificado
	 */
	public TipoCertificado getTipoCertificado() {
		return tipoCertificado;
	}

	/**
	 * @param tipoCertificado the tipoCertificado to set
	 */
	public void setTipoCertificado(TipoCertificado tipoCertificado) {
		this.tipoCertificado = tipoCertificado;
	}

	public String getFechaCadenaOriginal() {
		return fechaCadenaOriginal;
	}

	public void setFechaCadenaOriginal(String fechaCadenaOriginal) {
		this.fechaCadenaOriginal = fechaCadenaOriginal;
	}

	public String getSelloIMSS() {
		return selloIMSS;
	}

	public void setSelloIMSS(String selloIMSS) {
		this.selloIMSS = selloIMSS;
	}

}
