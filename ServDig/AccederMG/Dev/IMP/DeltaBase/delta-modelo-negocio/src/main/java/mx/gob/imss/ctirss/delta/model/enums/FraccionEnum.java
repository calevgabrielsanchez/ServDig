/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

/**
 * @author NOVUTECK1
 *
 */
public enum FraccionEnum {
	
	CONSTRUCCION("411"), AGRICULTURA("011");
	
	private String numFraccion;
	private FraccionEnum(String numFraccion){
		this.numFraccion=numFraccion;
	}
	
	public String getCodigo() {
		return numFraccion;
	}

	public void setCodigo(String codigo) {
		this.numFraccion = codigo;
	}
}
