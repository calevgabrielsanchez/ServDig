package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

import java.util.List;

public class DetalleRegistroMovRissSindo {
	private int numMovimientos;
	private List<RegistroMovRissSindo> lstRegistroMovRiss;
	private List<RegistroMovRissSindo> lstRegistroMovRissNoEnviado;

	public int getNumMovimientos() {
		return numMovimientos;
	}

	public void setNumMovimientos(int numMovimientos) {
		this.numMovimientos = numMovimientos;
	}

	public List<RegistroMovRissSindo> getLstRegistroMovRiss() {
		return lstRegistroMovRiss;
	}

	public void setLstRegistroMovRiss(
			List<RegistroMovRissSindo> lstRegistroMovRiss) {
		this.lstRegistroMovRiss = lstRegistroMovRiss;
	}

	public List<RegistroMovRissSindo> getLstRegistroMovRissNoEnviado() {
		return lstRegistroMovRissNoEnviado;
	}

	public void setLstRegistroMovRissNoEnviado(
			List<RegistroMovRissSindo> lstRegistroMovRissNoEnviado) {
		this.lstRegistroMovRissNoEnviado = lstRegistroMovRissNoEnviado;
	}

}
