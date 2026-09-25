package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.model.negocio;

import java.util.List;

public class RegistroRepetidoCanase {
	private long frecuencia;
	private List<String> lstNumNss;

	public long getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(long frecuencia) {
		this.frecuencia = frecuencia;
	}

	public List<String> getLstNumNss() {
		return lstNumNss;
	}

	public void setLstNumNss(List<String> lstNumNss) {
		this.lstNumNss = lstNumNss;
	}
}
