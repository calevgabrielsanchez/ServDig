/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

/**
 * @author Alan Garcia
 *
 */

//Added by Alan Garcia

public class AreaNormativaDTO  implements Serializable{
	
	/** Identificador único de la versión de la clase */
	private static   long serialVersionUID = 3515802327994150187L;
	
	private long cveSsoareanorma;
	private String desAreanorma;
	

	
	public AreaNormativaDTO()
	{
		cveSsoareanorma = -99L;
		desAreanorma = "";
	}
	
	public long getCveSsoareanorma() {
		return cveSsoareanorma;
	}
	public void setCveSsoareanorma(long cveSsoareanorma) {
		this.cveSsoareanorma = cveSsoareanorma;
	}
	public String getDesAreanorma() {
		return desAreanorma;
	}
	public void setDesAreanorma(String desAreanorma) {
		this.desAreanorma = desAreanorma;
	}

}
