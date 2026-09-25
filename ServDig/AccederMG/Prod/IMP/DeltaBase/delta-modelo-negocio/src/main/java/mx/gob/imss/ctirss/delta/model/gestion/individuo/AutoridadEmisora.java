package mx.gob.imss.ctirss.delta.model.gestion.individuo;


import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class AutoridadEmisora extends AbstractModel {
	
	
	private static final long serialVersionUID = 1L;
	private String cveIdAutoridadEmisora;
	private String desAutoridadEmisora;
	
	public String getCveIdAutoridadEmisora() {
		return cveIdAutoridadEmisora;
	}
	public void setCveIdAutoridadEmisora(String cveIdAutoridadEmisora) {
		this.cveIdAutoridadEmisora = cveIdAutoridadEmisora;
	}
	public String getDesAutoridadEmisora() {
		return desAutoridadEmisora;
	}
	public void setDesAutoridadEmisora(String desAutoridadEmisora) {
		this.desAutoridadEmisora = desAutoridadEmisora;
	}


}
