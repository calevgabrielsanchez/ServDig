package mx.gob.imss.ctirss.delta.derechohabientes.web.bean;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.derechohabiente.RespuestaCuestionario;

public class RespuestasVO implements Serializable{
	private List<RespuestaCuestionario> respuestas;
	private Long idSolicitud;
	private Long idPersona;
	private Long idTramite;

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public List<RespuestaCuestionario> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<RespuestaCuestionario> respuestas) {
		this.respuestas = respuestas;
	}

	public Long getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}
}
