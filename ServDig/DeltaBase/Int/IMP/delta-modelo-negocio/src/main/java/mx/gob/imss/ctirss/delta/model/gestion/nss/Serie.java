/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.nss;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 * 
 */
public class Serie extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private NumberFormat nf = new DecimalFormat("00");

	/**
	 * Clave de la serie
	 */
	private Long idSerie;

	/**
	 * Numero de la serie
	 */
	private Long numSerie;

	/**
	 * Anio de registro
	 */
	private Integer anioRegistro;

	/**
	 * Anio de nacimiento
	 */
	private Integer anioNacimiento;

	/**
	 * Folio
	 */
	private Long folio;

	/**
	 * Nombre de la secuencia de donde se tomar&aacute; el folio para el NSS
	 */
	private String secuenciaNss;

	/**
	 * Tipo de la serie
	 */
	private TipoSerie tipoSerie;

	/**
	 * Número de serie formateado a dos dígitos
	 */
	private String numSerieFormatedo;

	/**
	 * Anio de registro formateado a dos dígitos
	 */
	private String anioRegistroFormateado;

	/**
	 * Minimal Constructor
	 */
	public Serie() {

	}

	/**
	 * Full Constructor
	 * 
	 * @param idSerie
	 * @param numSerie
	 * @param anioRegistro
	 * @param idTipoSerie
	 * @param folio
	 * @param anioNacimiento
	 */
	public Serie(Long idSerie, BigDecimal numSerie, BigDecimal anioRegistro,
			Long idTipoSerie, BigDecimal folio, Long anioNacimiento,
			String secuenciaNss) {
		this.idSerie = idSerie;
		this.numSerie = numSerie.longValue();

		if (numSerie != null) {
			this.numSerieFormatedo = nf.format(numSerie);
		}

		this.anioRegistro = anioRegistro.intValue();

		if (anioRegistro != null) {
			this.anioRegistroFormateado = nf.format(anioRegistro);
		}

		this.tipoSerie = new TipoSerie();
		this.tipoSerie.setIdTipoSerie(idTipoSerie.intValue());

		this.folio = folio.longValue();
		this.anioNacimiento = anioNacimiento.intValue();
		this.secuenciaNss = secuenciaNss;

	}

	/**
	 * @return the idSerie
	 */
	public Long getIdSerie() {
		return idSerie;
	}

	/**
	 * @param idSerie
	 *            the idSerie to set
	 */
	public void setIdSerie(Long idSerie) {
		this.idSerie = idSerie;
	}

	/**
	 * @return the numSerie
	 */
	public Long getNumSerie() {
		return numSerie;
	}

	/**
	 * @param numSerie
	 *            the numSerie to set
	 */
	public void setNumSerie(Long numSerie) {
		this.numSerie = numSerie;

		if (numSerie != null) {
			this.numSerieFormatedo = nf.format(numSerie);
		}
	}

	/**
	 * @return the anioRegistro
	 */
	public Integer getAnioRegistro() {
		return anioRegistro;
	}

	/**
	 * @param anioRegistro
	 *            the anioRegistro to set
	 */
	public void setAnioRegistro(Integer anioRegistro) {
		this.anioRegistro = anioRegistro;

		if (anioRegistro != null) {
			this.anioRegistroFormateado = nf.format(anioRegistro);
		}
	}

	/**
	 * @return the anioNacimiento
	 */
	public Integer getAnioNacimiento() {
		return anioNacimiento;
	}

	/**
	 * @param anioNacimiento
	 *            the anioNacimiento to set
	 */
	public void setAnioNacimiento(Integer anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	/**
	 * @return the folio
	 */
	public Long getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 *            the folio to set
	 */
	public void setFolio(Long folio) {
		this.folio = folio;
	}

	public String getSecuenciaNss() {
		return secuenciaNss;
	}

	public void setSecuenciaNss(String secuenciaNss) {
		this.secuenciaNss = secuenciaNss;
	}

	/**
	 * @return the tipoSerie
	 */
	public TipoSerie getTipoSerie() {
		return tipoSerie;
	}

	/**
	 * @param tipoSerie
	 *            the tipoSerie to set
	 */
	public void setTipoSerie(TipoSerie tipoSerie) {
		this.tipoSerie = tipoSerie;
	}

	public String getNumSerieFormatedo() {
		return numSerieFormatedo;
	}

	public void setNumSerieFormatedo(String numSerieFormatedo) {
		this.numSerieFormatedo = numSerieFormatedo;
	}

	public String getAnioRegistroFormateado() {
		return anioRegistroFormateado;
	}

	public void setAnioRegistroFormateado(String anioRegistroFormateado) {
		this.anioRegistroFormateado = anioRegistroFormateado;
	}
}
