package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;

/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de la pantalla
 * manejada en el flujo de Seguimiento de promocion Generica para 
 * derivar a otra subdelegacion
 * 
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 *
 */
public class SegDerivacionSubdelGenericoTabVO extends ControlTabs {
	
	private static final long serialVersionUID = 2L;
	
	
	private String regPatronalFis;
	private String razonSocialFis;
	
	private String regPatronalObra;
	private String razonSocialObra;

	private Long cveFkPatronFis;
	private Long cveFkPatronObra;
	
	/**
	 * @return the cveFkPatronFis
	 */
	public Long getCveFkPatronFis() {
		return cveFkPatronFis;
	}

	/**
	 * @param cveFkPatronFis the cveFkPatronFis to set
	 */
	public void setCveFkPatronFis(Long cveFkPatronFis) {
		this.cveFkPatronFis = cveFkPatronFis;
	}

	/**
	 * @return the cveFkPatronObra
	 */
	public Long getCveFkPatronObra() {
		return cveFkPatronObra;
	}

	/**
	 * @param cveFkPatronObra the cveFkPatronObra to set
	 */
	public void setCveFkPatronObra(Long cveFkPatronObra) {
		this.cveFkPatronObra = cveFkPatronObra;
	}

	/**
	 * Atributo de la fecha de derivacion a otra subdelegacion
	 */	
	private String fechaDerivacionSubdel;
	
	private String cveSubdelegacionDestino;
	/**
	 * @return the regPatronalFis
	 */
	public String getRegPatronalFis() {
		return regPatronalFis;
	}

	/**
	 * @param regPatronalFis the regPatronalFis to set
	 */
	public void setRegPatronalFis(String regPatronalFis) {
		this.regPatronalFis = regPatronalFis;
	}

	/**
	 * @return the razonSocialFis
	 */
	public String getRazonSocialFis() {
		return razonSocialFis;
	}

	/**
	 * @param razonSocialFis the razonSocialFis to set
	 */
	public void setRazonSocialFis(String razonSocialFis) {
		this.razonSocialFis = razonSocialFis;
	}

	/**
	 * @return the regPatronalObra
	 */
	public String getRegPatronalObra() {
		return regPatronalObra;
	}

	/**
	 * @param regPatronalObra the regPatronalObra to set
	 */
	public void setRegPatronalObra(String regPatronalObra) {
		this.regPatronalObra = regPatronalObra;
	}

	/**
	 * @return razonSocialObra
	 */
	public String getRazonSocialObra() {
		return razonSocialObra;
	}

	/**
	 * @param razonSocialObra
	 */
	public void setRazonSocialObra(String razonSocialObra) {
		this.razonSocialObra = razonSocialObra;
	}

	/**
	 * @return fechaDerivacionSubdel
	 */
	public String getFechaDerivacionSubdel() {
		return fechaDerivacionSubdel;
	}

	/**
	 * @param fechaDerivacionSubdel
	 */
	public void setFechaDerivacionSubdel(String fechaDerivacionSubdel) {
		this.fechaDerivacionSubdel = fechaDerivacionSubdel;
	}

	/**
	 * @return subdelegacionDestino
	 */
	public String getCveSubdelegacionDestino() {
		return cveSubdelegacionDestino;
	}

	/**
	 * @param subdelegacionDestino
	 */
	public void setCveSubdelegacionDestino(String subdelegacionDestino) {
		this.cveSubdelegacionDestino = subdelegacionDestino;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SegDerivacionSubdelGenericoTabVO [regPatronalFis="
				+ regPatronalFis + ", razonSocialFis=" + razonSocialFis
				+ ", regPatronalObra=" + regPatronalObra + ", razonSocialObra="
				+ razonSocialObra + ", cveFkPatronFis=" + cveFkPatronFis
				+ ", cveFkPatronObra=" + cveFkPatronObra
				+ ", fechaDerivacionSubdel=" + fechaDerivacionSubdel
				+ ", cveSubdelegacionDestino=" + cveSubdelegacionDestino + "]";
	}


}
