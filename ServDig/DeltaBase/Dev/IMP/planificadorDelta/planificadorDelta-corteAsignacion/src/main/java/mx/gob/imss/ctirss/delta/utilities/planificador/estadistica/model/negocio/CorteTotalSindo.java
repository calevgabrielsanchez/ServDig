package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

import java.util.List;

public class CorteTotalSindo {
	private long numtotalMovimientos;
	private long numMovClasifVentanilla;
	private long numMovClasifInternet;
	private long numMovAltaPatronal;
	private long numMovCambioDomicilio;
	private long numMovCambioDomCt;
	private long numMovBajaSindo;
	private List<String> lstRegistroNrpRepetido;

	public long getNumtotalMovimientos() {
		return numtotalMovimientos;
	}

	public void setNumtotalMovimientos(long numtotalMovimientos) {
		this.numtotalMovimientos = numtotalMovimientos;
	}

	public long getNumMovClasifVentanilla() {
		return numMovClasifVentanilla;
	}

	public void setNumMovClasifVentanilla(long numMovClasifVentanilla) {
		this.numMovClasifVentanilla = numMovClasifVentanilla;
	}

	public long getNumMovClasifInternet() {
		return numMovClasifInternet;
	}

	public void setNumMovClasifInternet(long numMovClasifInternet) {
		this.numMovClasifInternet = numMovClasifInternet;
	}

	public long getNumMovAltaPatronal() {
		return numMovAltaPatronal;
	}

	public void setNumMovAltaPatronal(long numMovAltaPatronal) {
		this.numMovAltaPatronal = numMovAltaPatronal;
	}

	public long getNumMovCambioDomicilio() {
		return numMovCambioDomicilio;
	}

	public void setNumMovCambioDomicilio(long numMovCambioDomicilio) {
		this.numMovCambioDomicilio = numMovCambioDomicilio;
	}

	public List<String> getLstRegistroNrpRepetido() {
		return lstRegistroNrpRepetido;
	}

	public void setLstRegistroNrpRepetido(List<String> lstRegistroNrpRepetido) {
		this.lstRegistroNrpRepetido = lstRegistroNrpRepetido;
	}

	public long getNumMovBajaSindo() {
		return numMovBajaSindo;
	}

	public void setNumMovBajaSindo(long numMovBajaSindo) {
		this.numMovBajaSindo = numMovBajaSindo;
	}

	public long getNumMovCambioDomCt() {
		return numMovCambioDomCt;
	}

	public void setNumMovCambioDomCt(long numMovCambioDomCt) {
		this.numMovCambioDomCt = numMovCambioDomCt;
	}
}
