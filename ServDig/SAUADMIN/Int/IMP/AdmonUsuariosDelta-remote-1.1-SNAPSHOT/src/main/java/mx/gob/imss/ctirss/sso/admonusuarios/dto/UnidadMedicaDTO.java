/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

/**
 * @author Alan René García Rico
 *
 */
public class UnidadMedicaDTO  implements Serializable{
	
	/** Identificador único de la versión de la clase */
	private static final long serialVersionUID = 3515802327994150187L;
	/** cve delegacion */
	private String cveUmf;
	/** Nombre Delegacion */
	private String nombreUmf;
	/**
	 * @return the cveUmf
	 */
	public String getCveUmf() {
		return cveUmf;
	}
	/**
	 * @param cveUmf the cveUmf to set
	 */
	public void setCveUmf(String cveUmf) {
		this.cveUmf = cveUmf;
	}
	/**
	 * @return the nombreUmf
	 */
	public String getNombreUmf() {
		return nombreUmf;
	}
	/**
	 * @param nombreUmf the nombreUmf to set
	 */
	public void setNombreUmf(String nombreUmf) {
		this.nombreUmf = nombreUmf;
	}
	
	
}