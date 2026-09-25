package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

public class PersonaAutorizada extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6635426158246616325L;
	private Long cvePersonaAutorizada;
	private Fisica fisica;
	private SujetoObligado sujetoObligado;
	private Date fechaAlta;
	private Date fechaBaja;
	private Date fechaActualizacion;
	
	
	public Fisica getFisica() {
		return fisica;
	}

	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}
	
	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}

	public Long getCvePersonaAutorizada() {
		return cvePersonaAutorizada;
	}

	public void setCvePersonaAutorizada(Long cvePersonaAutorizada) {
		this.cvePersonaAutorizada = cvePersonaAutorizada;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
