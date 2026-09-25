package mx.gob.imss.ctirss.delta.model.derechohabientes.documentos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;

public class Sav007 extends Derechohabiente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8162684561540278104L;

	private String nombreAsegurado;
	public String getNombreAsegurado() {
		return nombreAsegurado;
	}

	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}

	private Date FechaProbableInicio;
	private Date FechaProbableTermino;
	private List<Derechohabiente> derechohabientes;

	public Date getFechaProbableInicio() {
		return FechaProbableInicio;
	}

	public void setFechaProbableInicio(Date fechaProbableInicio) {
		FechaProbableInicio = fechaProbableInicio;
	}

	public Date getFechaProbableTermino() {
		return FechaProbableTermino;
	}

	public void setFechaProbableTermino(Date fechaProbableTermino) {
		FechaProbableTermino = fechaProbableTermino;
	}

	public List<Derechohabiente> getDerechohabientes() {
		return derechohabientes;
	}

	public void setDerechohabientes(List<Derechohabiente> derechohabientes) {
		this.derechohabientes = derechohabientes;
	}

	
}
