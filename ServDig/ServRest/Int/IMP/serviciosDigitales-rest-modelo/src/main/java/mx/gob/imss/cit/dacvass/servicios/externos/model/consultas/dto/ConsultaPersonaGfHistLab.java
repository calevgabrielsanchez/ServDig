package mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConsultaPersonaGfHistLab implements Serializable {

	private static final long serialVersionUID = 888957202798538724L;
	
	private String refCurp;
	private String refRfc;
	private String numNss;
	private boolean consultaHistoriaLaboral;
	
	
	
	public String getRefCurp() {
		return refCurp;
	}
	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}
	public boolean isConsultaHistoriaLaboral() {
		return consultaHistoriaLaboral;
	}
	public void setConsultaHistoriaLaboral(boolean consultaHistoriaLaboral) {
		this.consultaHistoriaLaboral = consultaHistoriaLaboral;
	}
	
	
	public String getRefRfc() {
		return refRfc;
	}
	public void setRefRfc(String refRfc) {
		this.refRfc = refRfc;
	}
	public String getNumNss() {
		return numNss;
	}
	public void setNumNss(String numNss) {
		this.numNss = numNss;
	}
	@Override
	public String toString() {
		return "ConsultaPersonaGfHistLab [refCurp=" + refCurp + ", refRfc=" + refRfc + ", numNss=" + numNss
				+ ", consultaHistoriaLaboral=" + consultaHistoriaLaboral + "]";
	}
	
	
	
	
	

}
