package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CertificacionNSS extends AbstractModel {

	private static final long serialVersionUID = 1L;
	private TipoNSSCorreccion tipoNSS;
	private List<TipoRegularizacionNSS> tipoRegularizacionNSS;

	public TipoNSSCorreccion getTipoNSS() {
		return tipoNSS;
	}

	public void setTipoNSS(TipoNSSCorreccion tipoNSS) {
		this.tipoNSS = tipoNSS;
	}

	public List<TipoRegularizacionNSS> getTipoRegularizacionNSS() {
		return tipoRegularizacionNSS;
	}

	public void setTipoRegularizacionNSS(
			List<TipoRegularizacionNSS> tipoRegularizacionNSS) {
		this.tipoRegularizacionNSS = tipoRegularizacionNSS;
	}

}
