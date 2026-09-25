package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.cambioClinicaMas;

import java.io.Serializable;



public class CambioClinicaMasVb implements Serializable {
	/**
	 * 
	 */
	public static final String MODEL_NAME="cambioClinicaMasModel";
	private static final long serialVersionUID = -8004164009713544969L;

	private String numDerMod;
	private String numSolMod;
	public String getNumDerMod() {
		return numDerMod;
	}
	public void setNumDerMod(String numDerMod) {
		this.numDerMod = numDerMod;
	}
	public String getNumSolMod() {
		return numSolMod;
	}
	public void setNumSolMod(String numSolMod) {
		this.numSolMod = numSolMod;
	}
	
	
}
