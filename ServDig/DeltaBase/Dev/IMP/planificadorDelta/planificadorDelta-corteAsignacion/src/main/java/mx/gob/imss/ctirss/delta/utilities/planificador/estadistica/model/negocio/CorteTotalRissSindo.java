package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

import java.util.ArrayList;
import java.util.List;

public class CorteTotalRissSindo {
	private long numtotalMovimientos;
	private long numMovPatronales;
	private long numMovAsegurados;
	private List<RegistroMovRissSindo> lstRegistroMovRissFaltantes = new ArrayList<RegistroMovRissSindo>();

	public long getNumtotalMovimientos() {
		return numtotalMovimientos;
	}

	public void setNumtotalMovimientos(long numtotalMovimientos) {
		this.numtotalMovimientos = numtotalMovimientos;
	}

	public long getNumMovPatronales() {
		return numMovPatronales;
	}

	public void setNumMovPatronales(long numMovPatronales) {
		this.numMovPatronales = numMovPatronales;
	}

	public long getNumMovAsegurados() {
		return numMovAsegurados;
	}

	public void setNumMovAsegurados(long numMovAsegurados) {
		this.numMovAsegurados = numMovAsegurados;
	}

	public List<RegistroMovRissSindo> getLstRegistroMovRissFaltantes() {
		return lstRegistroMovRissFaltantes;
	}

	public void setLstRegistroMovRissFaltantes(
			List<RegistroMovRissSindo> lstRegistroMovRissFaltantes) {
		this.lstRegistroMovRissFaltantes = lstRegistroMovRissFaltantes;
	}
}
