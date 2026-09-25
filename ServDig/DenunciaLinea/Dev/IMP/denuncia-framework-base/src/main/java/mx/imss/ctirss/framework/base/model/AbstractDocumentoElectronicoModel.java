/**
 * 
 */
package mx.imss.ctirss.framework.base.model;

import javax.persistence.Transient;

import mx.imss.ctirss.session.TipoCertificado;

/**
 * Clase que representa un documento electronico. <br>
 * Un objeto del modelo adquiere la naturaleza de documento electr�nico debido a
 * que tiene que ser firmado electronicamente <br>
 * Los datos de la firma electr�nica se almacenaran en la notaria.
 * 
 * @author vaguirre
 * 
 */
public abstract class AbstractDocumentoElectronicoModel extends AbstractModel {
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
	 * Folio emitido por la notaria.
	 */
	@Transient
	// TODO VAP hacerlo persistente.
	private Long folioNotarial;

	/**
	 * Indicador del tipo de certificado del patr�n.
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

}
