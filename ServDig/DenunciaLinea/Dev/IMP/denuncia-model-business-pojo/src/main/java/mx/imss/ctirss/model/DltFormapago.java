package mx.imss.ctirss.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.*;

import mx.imss.ctirss.base.model.AbstractDltFormapago;


/**
 * The persistent class for the DLT_FORMAPAGO database table.
 * 
 */
@Entity
@Table(name="DLT_FORMAPAGO")
public class DltFormapago extends AbstractDltFormapago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private int periodoPago;
	@Transient
	private String desEspecifiquePP;
	@Transient
	private int comprobantePago;
	@Transient
	private String desEspecifiqueCP;
	@Transient
	private ArrayList<String> formaPago;
	@Transient
	private String desEspecifiqueFP;
	
	public int getPeriodoPago() {
		return periodoPago;
	}
	public void setPeriodoPago(int periodoPago) {
		this.periodoPago = periodoPago;
	}
	
	public String getDesEspecifiquePP() {
		return desEspecifiquePP;
	}
	public void setDesEspecifiquePP(String desEspecifiquePP) {
		this.desEspecifiquePP = desEspecifiquePP;
	}
	public int getComprobantePago() {
		return comprobantePago;
	}
	public void setComprobantePago(int comprobantePago) {
		this.comprobantePago = comprobantePago;
	}
	
	public String getDesEspecifiqueCP() {
		return desEspecifiqueCP;
	}
	public void setDesEspecifiqueCP(String desEspecifiqueCP) {
		this.desEspecifiqueCP = desEspecifiqueCP;
	}
	public ArrayList<String> getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(ArrayList<String> formaPago) {
		this.formaPago = formaPago;
	}
	public String getDesEspecifiqueFP() {
		return desEspecifiqueFP;
	}
	public void setDesEspecifiqueFP(String desEspecifiqueFP) {
		this.desEspecifiqueFP = desEspecifiqueFP;
	}


}