/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: ConfiguracionCe.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.clasificacion
 *  @Fecha: 08/10/2012
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.io.Serializable;

public class ConfiguracionCe implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -3956207793727715586L;

	//private boolean boAsignar = Boolean.FALSE;
	private boolean boRatificar = Boolean.FALSE;
	private boolean boRectificar = Boolean.FALSE;
	private boolean boAutoRatificarN1 = Boolean.FALSE;
	private boolean boRechRatificarN1 = Boolean.FALSE;
	private boolean boAutoRectificarN1 = Boolean.FALSE;
	private boolean boRechRectificarN1 = Boolean.FALSE;
	private boolean boAutoRatificarN2 = Boolean.FALSE;
	private boolean boRechRatificarN2 = Boolean.FALSE;
	private boolean boAutoRectificarN2 = Boolean.FALSE;
	private boolean boRechRectificarN2 = Boolean.FALSE;
	private boolean boConfRatificar = Boolean.FALSE;
	private boolean boConfRectificar = Boolean.FALSE;
	//private boolean boLiberar = Boolean.FALSE;
	private boolean boModificar = Boolean.FALSE;
	private boolean boHojaAnalisis = Boolean.FALSE;
	private boolean boVerClem = Boolean.FALSE;
	private boolean boModificarClem = Boolean.FALSE;
	private boolean boModificarAuto = Boolean.FALSE;
	
	private boolean boIndFirma = Boolean.FALSE;
	private String urlClemFirma = null;
	
	private boolean boDesechar = Boolean.FALSE;
	
	
	public boolean isBoDesechar() {
		return boDesechar;
	}

	public void setBoDesechar(boolean boDesechar) {
		this.boDesechar = boDesechar;
	}

	/**
	 * @return the boRatificar
	 */
	public boolean isBoRatificar() {
		return boRatificar;
	}

	/**
	 * @param boRatificar
	 *            the boRatificar to set
	 */
	public void setBoRatificar(final boolean boRatificar) {
		this.boRatificar = boRatificar;
	}

	/**
	 * @return the boRectificar
	 */
	public boolean isBoRectificar() {
		return boRectificar;
	}

	/**
	 * @param boRectificar
	 *            the boRectificar to set
	 */
	public void setBoRectificar(final boolean boRectificar) {
		this.boRectificar = boRectificar;
	}

	/**
	 * @return the boAutoRatificarN1
	 */
	public boolean isBoAutoRatificarN1() {
		return boAutoRatificarN1;
	}

	/**
	 * @param boAutoRatificarN1
	 *            the boAutoRatificarN1 to set
	 */
	public void setBoAutoRatificarN1(final boolean boAutoRatificarN1) {
		this.boAutoRatificarN1 = boAutoRatificarN1;
	}

	/**
	 * @return the boRechRatificarN1
	 */
	public boolean isBoRechRatificarN1() {
		return boRechRatificarN1;
	}

	/**
	 * @param boRechRatificarN1
	 *            the boRechRatificarN1 to set
	 */
	public void setBoRechRatificarN1(final boolean boRechRatificarN1) {
		this.boRechRatificarN1 = boRechRatificarN1;
	}

	/**
	 * @return the boAutoRectificarN1
	 */
	public boolean isBoAutoRectificarN1() {
		return boAutoRectificarN1;
	}

	/**
	 * @param boAutoRectificarN1
	 *            the boAutoRectificarN1 to set
	 */
	public void setBoAutoRectificarN1(final boolean boAutoRectificarN1) {
		this.boAutoRectificarN1 = boAutoRectificarN1;
	}

	/**
	 * @return the boRechRectificarN1
	 */
	public boolean isBoRechRectificarN1() {
		return boRechRectificarN1;
	}

	/**
	 * @param boRechRectificarN1
	 *            the boRechRectificarN1 to set
	 */
	public void setBoRechRectificarN1(final boolean boRechRectificarN1) {
		this.boRechRectificarN1 = boRechRectificarN1;
	}

	/**
	 * @return the boAutoRatificarN2
	 */
	public boolean isBoAutoRatificarN2() {
		return boAutoRatificarN2;
	}

	/**
	 * @param boAutoRatificarN2
	 *            the boAutoRatificarN2 to set
	 */
	public void setBoAutoRatificarN2(final boolean boAutoRatificarN2) {
		this.boAutoRatificarN2 = boAutoRatificarN2;
	}

	/**
	 * @return the boRechRatificarN2
	 */
	public boolean isBoRechRatificarN2() {
		return boRechRatificarN2;
	}

	/**
	 * @param boRechRatificarN2
	 *            the boRechRatificarN2 to set
	 */
	public void setBoRechRatificarN2(final boolean boRechRatificarN2) {
		this.boRechRatificarN2 = boRechRatificarN2;
	}

	/**
	 * @return the boAutoRectificarN2
	 */
	public boolean isBoAutoRectificarN2() {
		return boAutoRectificarN2;
	}

	/**
	 * @param boAutoRectificarN2
	 *            the boAutoRectificarN2 to set
	 */
	public void setBoAutoRectificarN2(final boolean boAutoRectificarN2) {
		this.boAutoRectificarN2 = boAutoRectificarN2;
	}

	/**
	 * @return the boRechRectificarN2
	 */
	public boolean isBoRechRectificarN2() {
		return boRechRectificarN2;
	}

	/**
	 * @param boRechRectificarN2
	 *            the boRechRectificarN2 to set
	 */
	public void setBoRechRectificarN2(final boolean boRechRectificarN2) {
		this.boRechRectificarN2 = boRechRectificarN2;
	}

	/**
	 * @return the boConfRatificar
	 */
	public boolean isBoConfRatificar() {
		return boConfRatificar;
	}

	/**
	 * @param boConfRatificar
	 *            the boConfRatificar to set
	 */
	public void setBoConfRatificar(final boolean boConfRatificar) {
		this.boConfRatificar = boConfRatificar;
	}

	/**
	 * @return the boConfRectificar
	 */
	public boolean isBoConfRectificar() {
		return boConfRectificar;
	}

	/**
	 * @param boConfRectificar
	 *            the boConfRectificar to set
	 */
	public void setBoConfRectificar(final boolean boConfRectificar) {
		this.boConfRectificar = boConfRectificar;
	}

	/**
	 * @return the boModificar
	 */
	public boolean isBoModificar() {
		return boModificar;
	}

	/**
	 * @param boModificar
	 *            the boModificar to set
	 */
	public void setBoModificar(final boolean boModificar) {
		this.boModificar = boModificar;
	}

	/**
	 * @return the boHojaAnalisis
	 */
	public boolean isBoHojaAnalisis() {
		return boHojaAnalisis;
	}

	/**
	 * @param boHojaAnalisis
	 *            the boHojaAnalisis to set
	 */
	public void setBoHojaAnalisis(final boolean boHojaAnalisis) {
		this.boHojaAnalisis = boHojaAnalisis;
	}

	/**
	 * @return the boVerClem
	 */
	public boolean isBoVerClem() {
		return boVerClem;
	}

	/**
	 * @param boVerClem
	 *            the boVerClem to set
	 */
	public void setBoVerClem(final boolean boVerClem) {
		this.boVerClem = boVerClem;
	}

	/**
	 * @return the boModificarClem
	 */
	public boolean isBoModificarClem() {
		return boModificarClem;
	}

	/**
	 * @param boModificarClem
	 *            the boModificarClem to set
	 */
	public void setBoModificarClem(final boolean boModificarClem) {
		this.boModificarClem = boModificarClem;
	}

	/**
	 * @return the boModificarAuto
	 */
	public boolean isBoModificarAuto() {
		return boModificarAuto;
	}

	/**
	 * @param boModificarAuto
	 *            the boModificarAuto to set
	 */
	public void setBoModificarAuto(final boolean boModificarAuto) {
		this.boModificarAuto = boModificarAuto;
	}
	
	public boolean isBoIndFirma() {
		return boIndFirma;
	}

	public void setBoIndFirma(boolean boIndFirma) {
		this.boIndFirma = boIndFirma;
	}

	public String getUrlClemFirma() {
		return urlClemFirma;
	}

	public void setUrlClemFirma(String urlClemFirma) {
		this.urlClemFirma = urlClemFirma;
	}
	
	@Override
	public String toString() {
		return "ConfiguracionCe [boRatificar=" + boRatificar
				+ ", boRectificar=" + boRectificar + ", boAutoRatificarN1="
				+ boAutoRatificarN1 + ", boRechRatificarN1="
				+ boRechRatificarN1 + ", boAutoRectificarN1="
				+ boAutoRectificarN1 + ", boRechRectificarN1="
				+ boRechRectificarN1 + ", boAutoRatificarN2="
				+ boAutoRatificarN2 + ", boRechRatificarN2="
				+ boRechRatificarN2 + ", boAutoRectificarN2="
				+ boAutoRectificarN2 + ", boRechRectificarN2="
				+ boRechRectificarN2 + ", boConfRatificar=" + boConfRatificar
				+ ", boConfRectificar=" + boConfRectificar + ", boModificar="
				+ boModificar + ", boHojaAnalisis=" + boHojaAnalisis
				+ ", boVerClem=" + boVerClem + ", boModificarClem="
				+ boModificarClem + ", boModificarAuto=" + boModificarAuto
				+ ", boIndFirma=" + boIndFirma + ", urlClemFirma="
				+ urlClemFirma + "]";
	}

}
