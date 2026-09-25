package mx.gob.imss.ctirss.correccion.web.controller.vo;

import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipoOrigen;

public class SelectorCatVO {
	
	private CgcCatTipo tipo;
	private CgcCatOrigen origen;
	private CgcCatTipoOrigen tipoOrigen;
	private CgcCatcriterioseleccion criterioSeleccion;
	
	public SelectorCatVO(){
		
		setTipo(new CgcCatTipo());
		setOrigen(new CgcCatOrigen());
		setTipoOrigen(new CgcCatTipoOrigen());
		setCriterioSeleccion(new CgcCatcriterioseleccion());
	}
	

	public CgcCatcriterioseleccion getCriterioSeleccion() {
		return criterioSeleccion;
	}

	public void setCriterioSeleccion(CgcCatcriterioseleccion criterioSeleccion) {
		this.criterioSeleccion = criterioSeleccion;
	}

	public CgcCatTipo getTipo() {
		return tipo;
	}

	public void setTipo(CgcCatTipo tipo) {
		this.tipo = tipo;
	}

	public CgcCatOrigen getOrigen() {
		return origen;
	}

	public void setOrigen(CgcCatOrigen origen) {
		this.origen = origen;
	}

	public CgcCatTipoOrigen getTipoOrigen() {
		return tipoOrigen;
	}

	public void setTipoOrigen(CgcCatTipoOrigen tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}
	
	

}
