/**
 * Atributos de sello, no. de certifiado en base64.
 */
package mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.model;

import java.io.Serializable;

/**
 * @author Lucio Duran Silva
 * 
 */
public class SelloCFDI implements Serializable {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SelloCFDI [certificado=" + certificado + ", sello=" + sello
				+ ", noCertificado=" + noCertificado + ", cadenaOriginal="
				+ cadenaOriginal + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 
	 */
	private String certificado = null;

	private String sello = null;

	private String noCertificado = null;
	
	private String cadenaOriginal = null;

	/**
	 * @return the cadenaOriginal
	 */
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	/**
	 * @param cadenaOriginal the cadenaOriginal to set
	 */
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	/**
	 * @return the certificado
	 */
	public String getCertificado() {
		return certificado;
	}

	/**
	 * @param certificado
	 *            the certificado to set
	 */
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	/**
	 * @return the sello
	 */
	public String getSello() {
		return sello;
	}

	/**
	 * @param sello
	 *            the sello to set
	 */
	public void setSello(String sello) {
		this.sello = sello;
	}

	/**
	 * @return the noCertificado
	 */
	public String getNoCertificado() {
		return noCertificado;
	}

	/**
	 * @param noCertificado
	 *            the noCertificado to set
	 */
	public void setNoCertificado(String noCertificado) {
		this.noCertificado = noCertificado;
	}

}
