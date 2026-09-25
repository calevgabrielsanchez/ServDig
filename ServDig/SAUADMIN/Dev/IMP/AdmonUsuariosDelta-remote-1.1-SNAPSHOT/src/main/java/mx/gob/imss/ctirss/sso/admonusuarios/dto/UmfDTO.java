/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

/**
 * @author user
 *
 */
public class UmfDTO  implements Serializable{
	
	/** Identificador único de la versión de la clase */
	private static   long serialVersionUID = 3515802327994150187L;
	private Long cveUmf;
	private String nombreUmf;
	
	public Long getCveUmf() {
		return cveUmf;
	}
	public void setCveUmf(Long cveUmf) {
		this.cveUmf = cveUmf;
	}
	public String getNombreUmf() {
		return nombreUmf;
	}
	public void setNombreUmf(String nombreUmf) {
		this.nombreUmf = nombreUmf;
	}

}