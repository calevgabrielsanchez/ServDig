package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TipoRegularizacionNSS extends AbstractModel {

	private static final long serialVersionUID = 1L;
	private Long idTipoRegularizacionNSS;
	private String descripcionRegularizacionNSS;

	public Long getIdTipoRegularizacionNSS() {
		return idTipoRegularizacionNSS;
	}

	public void setIdTipoRegularizacionNSS(Long idTipoRegularizacionNSS) {
		this.idTipoRegularizacionNSS = idTipoRegularizacionNSS;
	}

	public String getDescripcionRegularizacionNSS() {
		return descripcionRegularizacionNSS;
	}

	public void setDescripcionRegularizacionNSS(
			String descripcionRegularizacionNSS) {
		this.descripcionRegularizacionNSS = descripcionRegularizacionNSS;
	}

}
