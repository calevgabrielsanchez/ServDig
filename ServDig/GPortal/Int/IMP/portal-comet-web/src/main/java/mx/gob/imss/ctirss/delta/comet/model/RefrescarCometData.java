package mx.gob.imss.ctirss.delta.comet.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlType(propOrder = { "idPersona", "idTipoPersona", "idTipoTramite",
		"regPatronal", "modalidad", "exito" })
public class RefrescarCometData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPersona;
	private Long idTipoPersona;
	private Integer idTipoTramite;
	private String regPatronal;
	private Long modalidad;
	private Boolean exito;

	public Long getIdPersona() {
		return idPersona;
	}

	@XmlElement(required = true)
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Long getIdTipoPersona() {
		return idTipoPersona;
	}

	@XmlElement(required = true)
	public void setIdTipoPersona(Long idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public Integer getIdTipoTramite() {
		return idTipoTramite;
	}

	@XmlElement(required = true)
	public void setIdTipoTramite(Integer idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}

	public String getRegPatronal() {
		return regPatronal;
	}

	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}

	public Long getModalidad() {
		return modalidad;
	}

	public void setModalidad(Long modalidad) {
		this.modalidad = modalidad;
	}

	public Boolean getExito() {
		return exito;
	}

	public void setExito(Boolean exito) {
		this.exito = exito;
	}

	public String toString() {
		return "\n"
				+ ToStringBuilder.reflectionToString(this,
						ToStringStyle.MULTI_LINE_STYLE) + "\n";
	}
}