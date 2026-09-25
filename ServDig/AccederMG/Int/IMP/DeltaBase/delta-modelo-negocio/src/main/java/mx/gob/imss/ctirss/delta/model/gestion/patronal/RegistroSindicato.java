package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class RegistroSindicato extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2926580776675478048L;
	private Long cveRegistroSindicato;
	private String numReferenciadocRegistro;
	private Date fechaRegistro;
	private String autoridadLaboral;
	private Long cveIdPersonaMoral;
	private Long cveIdPatronSujetoObligado;
	private String numeroRegistroPatronal;
	
	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}

	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
	}

	public Long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(Long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public Long getCveRegistroSindicato() {
		return cveRegistroSindicato;
	}

	public void setCveRegistroSindicato(Long cveRegistroSindicato) {
		this.cveRegistroSindicato = cveRegistroSindicato;
	}

	public String getNumReferenciadocRegistro() {
		return numReferenciadocRegistro;
	}

	public void setNumReferenciadocRegistro(String numReferenciadocRegistro) {
		this.numReferenciadocRegistro = numReferenciadocRegistro;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getAutoridadLaboral() {
		return autoridadLaboral;
	}

	public void setAutoridadLaboral(String autoridadLaboral) {
		this.autoridadLaboral = autoridadLaboral;
	}

	public Long getCveIdPersonaMoral() {
		return cveIdPersonaMoral;
	}

	public void setCveIdPersonaMoral(Long cveIdPersonaMoral) {
		this.cveIdPersonaMoral = cveIdPersonaMoral;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegistroSindicato [cveRegistroSindicato=");
		builder.append(cveRegistroSindicato);
		builder.append(", numReferenciadocRegistro=");
		builder.append(numReferenciadocRegistro);
		builder.append(", fechaRegistro=");
		builder.append(fechaRegistro);
		builder.append(", autoridadLaboral=");
		builder.append(autoridadLaboral);
		builder.append(", cveIdPersonaMoral=");
		builder.append(cveIdPersonaMoral);
		builder.append("]");
		return builder.toString();
	}
}
