package mx.gob.imss.ctirss.delta.cobranza.modelo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ActEconomica extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8490696396229051195L;
	public ActEconomica(){
	}
	
	String claveActEco;//CLAVE_ACT_ECO    1        NUMBER (4)        None           
	String claveGpoAct;//CLAVE_GPO_ACT    3        NUMBER (2)        None           
	String descActEco;//DESC_ACT_ECO    2        CHAR (60 Byte)        None
	public String getClaveActEco() {
		return claveActEco;
	}
	public void setClaveActEco(String claveActEco) {
		this.claveActEco = claveActEco;
	}
	public String getClaveGpoAct() {
		return claveGpoAct;
	}
	public void setClaveGpoAct(String claveGpoAct) {
		this.claveGpoAct = claveGpoAct;
	}
	public String getDescActEco() {
		return descActEco;
	}
	public void setDescActEco(String descActEco) {
		this.descActEco = descActEco;
	}
	
	
}
