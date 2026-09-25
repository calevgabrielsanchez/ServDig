package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;

public class Documento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -223816665667260589L;
	protected Long cveIdDocumento;
	protected String desDocumento;
	
	
	public Long getCveIdDocumento() {
		return cveIdDocumento;
	}
	public void setCveIdDocumento(Long cveIdDocumento) {
		this.cveIdDocumento = cveIdDocumento;
	}
	public String getDesDocumento() {
		return desDocumento;
	}
	public void setDesDocumento(String desDocumento) {
		this.desDocumento = desDocumento;
	}
	

}
