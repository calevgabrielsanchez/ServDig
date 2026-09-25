package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AutoridadEmisora;

@XmlRootElement
public class ActaTerminoUnionCivil extends DocumentoProbatorio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String lugarEmision;
	private Date fechaEmision;
	private AutoridadEmisora autoridadEmisora;
	private EntidadFederativa entidadFederativa;
	private String noReferencia;
	
	public String getLugarEmision() {
		return lugarEmision;
	}
	public void setLugarEmision(String lugarEmision) {
		this.lugarEmision = lugarEmision;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public AutoridadEmisora getAutoridadEmisora() {
		return autoridadEmisora;
	}
	public void setAutoridadEmisora(AutoridadEmisora autoridadEmisora) {
		this.autoridadEmisora = autoridadEmisora;
	}
	public EntidadFederativa getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(EntidadFederativa entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getNoReferencia() {
		return noReferencia;
	}
	public void setNoReferencia(String noReferencia) {
		this.noReferencia = noReferencia;
	}
	
}
