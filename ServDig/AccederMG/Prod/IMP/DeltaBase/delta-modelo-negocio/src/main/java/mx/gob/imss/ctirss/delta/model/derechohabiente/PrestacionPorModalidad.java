package mx.gob.imss.ctirss.delta.model.derechohabiente;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;


public class PrestacionPorModalidad extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6148647453262567032L;
	
	private Prestacion prestacion;
	private TipoPrestacion tipoPrestacion;
	private Modalidad  modalidad;
	
	public Prestacion getPrestacion() {
		return prestacion;
	}
	public void setPrestacion(Prestacion prestacion) {
		this.prestacion = prestacion;
	}
	public TipoPrestacion getTipoPrestacion() {
		return tipoPrestacion;
	}
	public void setTipoPrestacion(TipoPrestacion tipoPrestacion) {
		this.tipoPrestacion = tipoPrestacion;
	}
	public Modalidad getModalidad() {
		return modalidad;
	}
	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}
	
}
