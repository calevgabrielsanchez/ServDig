package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

import java.util.Map;

public class CorteTotalCanase {
	private long numRegistrosCiz1;
	private long numRegistrosCiz2;
	private long numRegistrosCiz3;
	private long numtotalRegistros;
	private Map<String, RegistroRepetidoCanase> lstCurpRepetido;

	public Map<String, RegistroRepetidoCanase> getLstCurpRepetido() {
		return lstCurpRepetido;
	}

	public void setLstCurpRepetido(
			Map<String, RegistroRepetidoCanase> lstCurpRepetido) {
		this.lstCurpRepetido = lstCurpRepetido;
	}

	public long getNumRegistrosCiz1() {
		return numRegistrosCiz1;
	}

	public void setNumRegistrosCiz1(long numRegistrosCiz1) {
		this.numRegistrosCiz1 = numRegistrosCiz1;
	}

	public long getNumRegistrosCiz2() {
		return numRegistrosCiz2;
	}

	public void setNumRegistrosCiz2(long numRegistrosCiz2) {
		this.numRegistrosCiz2 = numRegistrosCiz2;
	}

	public long getNumRegistrosCiz3() {
		return numRegistrosCiz3;
	}

	public void setNumRegistrosCiz3(long numRegistrosCiz3) {
		this.numRegistrosCiz3 = numRegistrosCiz3;
	}

	public long getNumtotalRegistros() {
		return numtotalRegistros;
	}

	public void setNumtotalRegistros(long numtotalRegistros) {
		this.numtotalRegistros = numtotalRegistros;
	}
}
