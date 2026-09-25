package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;
import java.io.Serializable;
import java.util.List;

public class DatosPersonaFisica implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8291495453401547280L;
	private Long cveIdPersonaFisica;
	private String rfc;
	private String domicilioFiscalSat;
	private boolean isTramiteRegistroPortalIMSS;
	
	private List<DatosPersonaRepresentada> datosPersonaRepresentada;
	public Long getCveIdPersonaFisica() {
		return cveIdPersonaFisica;
	}
	public void setCveIdPersonaFisica(Long cveIdPersonaFisica) {
		this.cveIdPersonaFisica = cveIdPersonaFisica;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getDomicilioFiscalSat() {
		return domicilioFiscalSat;
	}
	public void setDomicilioFiscalSat(String domicilioFiscalSat) {
		this.domicilioFiscalSat = domicilioFiscalSat;
	}
	public boolean isTramiteRegistroPortalIMSS() {
		return isTramiteRegistroPortalIMSS;
	}
	public void setTramiteRegistroPortalIMSS(boolean isTramiteRegistroPortalIMSS) {
		this.isTramiteRegistroPortalIMSS = isTramiteRegistroPortalIMSS;
	}
	
	public List<DatosPersonaRepresentada> getDatosPersonaRepresentada() {
		return datosPersonaRepresentada;
	}
	public void setDatosPersonaRepresentada(List<DatosPersonaRepresentada> datosPersonaRepresentada) {
		this.datosPersonaRepresentada = datosPersonaRepresentada;
	}
	
	
	

}
