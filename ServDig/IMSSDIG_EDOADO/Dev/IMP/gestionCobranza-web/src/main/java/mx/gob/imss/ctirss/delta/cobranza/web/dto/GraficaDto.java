package mx.gob.imss.ctirss.delta.cobranza.web.dto;

import java.io.Serializable;

public class GraficaDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8463331177246600297L;
	private String label;
	private Double value;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

	
	
}
