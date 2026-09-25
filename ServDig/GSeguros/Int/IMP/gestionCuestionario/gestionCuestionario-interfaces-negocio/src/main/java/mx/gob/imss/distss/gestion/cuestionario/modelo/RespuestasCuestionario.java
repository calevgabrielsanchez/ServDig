package mx.gob.imss.distss.gestion.cuestionario.modelo;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class RespuestasCuestionario extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private int tipoCuestionario;
	private List<Respuesta> respuestas;
	private int sumatoriaRespuestas;

	public int getTipoCuestionario() {
		return tipoCuestionario;
	}

	public void setTipoCuestionario(int tipoCuestionario) {
		this.tipoCuestionario = tipoCuestionario;
	}

	public List<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	public int getSumatoriaRespuestas() {
		return sumatoriaRespuestas;
	}

	public void setSumatoriaRespuestas(int sumatoriaRespuestas) {
		this.sumatoriaRespuestas = sumatoriaRespuestas;
	}
}
