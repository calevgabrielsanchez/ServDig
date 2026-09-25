package mx.gob.imss.ctirss.delta.model.derechohabientes;

import java.io.Serializable;
import java.util.List;

public class PropiedadesDocumento implements Serializable {

	private static final long serialVersionUID = 4254712644844740409L;

	private Long idDerechohabiente;
	private Long idEstadoTramite;
	private Long idTramite;
	private Long tipoTramite;
	private String titulo;
	private Boolean autorizacion;
	private List<Long> personas;

	public Long getIdDerechohabiente() {
		return idDerechohabiente;
	}

	public void setIdDerechohabiente(Long idDerechohabiente) {
		this.idDerechohabiente = idDerechohabiente;
	}

	public Long getIdEstadoTramite() {
		return idEstadoTramite;
	}

	public void setIdEstadoTramite(Long idEstadoTramite) {
		this.idEstadoTramite = idEstadoTramite;
	}

	public Long getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}

	public Long getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(Long tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Boolean getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(Boolean autorizacion) {
		this.autorizacion = autorizacion;
	}

	public List<Long> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Long> personas) {
		this.personas = personas;
	}

}
