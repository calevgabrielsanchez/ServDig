package mx.gob.imss.cit.cda.web.app.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FiltroCombo extends BaseModel {

	private static final long serialVersionUID = -7684784263323259429L;
	private String enumeracion;
	private String delegacion;
	private String subdelegacion;
	
	public String getEnumeracion() {
		return enumeracion;
	}
	public void setEnumeracion(String enumeracion) {
		this.enumeracion = enumeracion;
	}
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	

}
