package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

@XmlRootElement
public class TramiteConsultaVigencia extends Tramite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -522898665983437563L;
	private AsignacionNSS nss;
	private List<GrupoFamiliar> integrantes;
	
	public AsignacionNSS getNss() {
		return nss;
	}
	
	public void setNss(AsignacionNSS nss) {
		this.nss = nss;
	}
	
	public List<GrupoFamiliar> getIntegrantes() {
		return integrantes;
	}
	
	public void setIntegrantes(List<GrupoFamiliar> integrantes) {
		this.integrantes = integrantes;
	}
}
