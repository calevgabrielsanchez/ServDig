package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

public class DatosFolioTramiteVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String numFolioTramite;
	private DomicilioAsignacionVO domicilioAsignacion;
	
	public String getNumFolioTramite() {
		return numFolioTramite;
	}
	public void setNumFolioTramite(String numFolioTramite) {
		this.numFolioTramite = numFolioTramite;
	}
	public DomicilioAsignacionVO getDomicilioAsignacion() {
		return domicilioAsignacion;
	}
	public void setDomicilioAsignacion(DomicilioAsignacionVO domicilioAsignacion) {
		this.domicilioAsignacion = domicilioAsignacion;
	}

}
