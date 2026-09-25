package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;
import java.util.List;


public class NssCuentaIndividual implements Serializable {

	private static final long serialVersionUID = -3951180553320842449L;

	private String nss;
	private String tipoRegularizacion;
	private List<CuentaIndividual> listaCuentaIndividual;

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public List<CuentaIndividual> getListaCuentaIndividual() {
		return listaCuentaIndividual;
	}

	public void setListaCuentaIndividual(
			List<CuentaIndividual> listaCuentaIndividual) {
		this.listaCuentaIndividual = listaCuentaIndividual;
	}

	public String getTipoRegularizacion() {
		return tipoRegularizacion;
	}

	public void setTipoRegularizacion(String tipoRegularizacion) {
		this.tipoRegularizacion = tipoRegularizacion;
	}

}
