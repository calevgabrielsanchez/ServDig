package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DicConcepto
 * 
 * @author IMSS
 *
 */
public class Concepto extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private Long cveIdConcepto;
    private String descripcion;
    
    
    public Concepto(){
    	super();
    }
    
    
	public Long getCveIdConcepto() {
		return cveIdConcepto;
	}
	public void setCveIdConcepto(Long cveIdConcepto) {
		this.cveIdConcepto = cveIdConcepto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
