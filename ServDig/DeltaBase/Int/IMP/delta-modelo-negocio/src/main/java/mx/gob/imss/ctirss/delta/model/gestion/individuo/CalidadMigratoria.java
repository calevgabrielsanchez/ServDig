package mx.gob.imss.ctirss.delta.model.gestion.individuo;


import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Lucio Duran Silva
 *
 */
public class CalidadMigratoria extends AbstractModel {
	
	
	private static final long serialVersionUID = 1L;
	private String cveIdCalidadCaracMigrat;
	private String desCalidadMigratoria;
	
	public String getCveIdCalidadCaracMigrat() {
		return cveIdCalidadCaracMigrat;
	}
	public void setCveIdCalidadCaracMigrat(String cveIdCalidadCaracMigrat) {
		this.cveIdCalidadCaracMigrat = cveIdCalidadCaracMigrat;
	}
	public String getDesCalidadMigratoria() {
		return desCalidadMigratoria;
	}
	public void setDesCalidadMigratoria(String desCalidadMigratoria) {
		this.desCalidadMigratoria = desCalidadMigratoria;
	}

}
