package mx.gob.imss.distss.gestion.cuestionario.modelo;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;

public class TramiteCuestionarioDummy extends TramiteFisica {

	private static final long serialVersionUID = -1939042271416100081L;
	
	private RespuestasCuestionario respuestas;

	public RespuestasCuestionario getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(RespuestasCuestionario respuestas) {
		this.respuestas = respuestas;
	}

}
