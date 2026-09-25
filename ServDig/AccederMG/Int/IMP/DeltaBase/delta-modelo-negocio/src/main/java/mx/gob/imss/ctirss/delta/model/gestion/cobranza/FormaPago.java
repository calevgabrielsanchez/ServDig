package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DicFormaPago
 * 
 * @author IMSS
 *
 */
public class FormaPago extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private Long cveIdFormaPago;
	private String descripcion;

    
    public FormaPago(){
    	super();
    }


	public Long getCveIdFormaPago() {
		return cveIdFormaPago;
	}
	public void setCveIdFormaPago(Long cveIdFormaPago) {
		this.cveIdFormaPago = cveIdFormaPago;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
