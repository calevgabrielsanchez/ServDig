package mx.gob.imss.ctirss.delta.portal.web.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class FiltrosBusqueda extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private int tipoFiltro;
	private String curp;
	private String rfc;
	private String nss;
	private String nrp;

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

}
