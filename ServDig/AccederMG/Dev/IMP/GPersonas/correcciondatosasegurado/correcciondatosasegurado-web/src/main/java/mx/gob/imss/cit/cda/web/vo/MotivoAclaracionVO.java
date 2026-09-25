package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class MotivoAclaracionVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<String> motivosAclaracionIMSS;
	private List<String> motivosAclaracionInfonavit;
	private List<String> motivosAclaracionAfore;
	private String creditoDescontado;
	private String otro;
	private String especificacion;
	
	public MotivoAclaracionVO() {
		super();
	}
	public MotivoAclaracionVO(List<String> motivosAclaracionIMSS,
			List<String> motivosAclaracionInfonavit,
			List<String> motivosAclaracionAfore, String creditoDescontado,
			String otro) {
		super();
		this.motivosAclaracionIMSS = motivosAclaracionIMSS;
		this.motivosAclaracionInfonavit = motivosAclaracionInfonavit;
		this.motivosAclaracionAfore = motivosAclaracionAfore;
		this.creditoDescontado = creditoDescontado;
		this.otro = otro;
	}
	public List<String> getMotivosAclaracionIMSS() {
		return motivosAclaracionIMSS;
	}
	public void setMotivosAclaracionIMSS(List<String> motivosAclaracionIMSS) {
		this.motivosAclaracionIMSS = motivosAclaracionIMSS;
	}
	public List<String> getMotivosAclaracionInfonavit() {
		return motivosAclaracionInfonavit;
	}
	public void setMotivosAclaracionInfonavit(
			List<String> motivosAclaracionInfonavit) {
		this.motivosAclaracionInfonavit = motivosAclaracionInfonavit;
	}
	public List<String> getMotivosAclaracionAfore() {
		return motivosAclaracionAfore;
	}
	public void setMotivosAclaracionAfore(List<String> motivosAclaracionAfore) {
		this.motivosAclaracionAfore = motivosAclaracionAfore;
	}
	public String getCreditoDescontado() {
		return creditoDescontado;
	}
	public void setCreditoDescontado(String creditoDescontado) {
		this.creditoDescontado = creditoDescontado;
	}
	public String getOtro() {
		return otro;
	}
	public void setOtro(String otro) {
		this.otro = otro;
	}	
	public String getEspecificacion() {
		return especificacion;
	}
	public void setEspecificacion(String especificacion) {
		this.especificacion = especificacion;
	}
	
	@Override
	public String toString() {
		return "MotivoAclaracionVO [motivosAclaracionIMSS="
				+ motivosAclaracionIMSS + ", motivosAclaracionInfonavit="
				+ motivosAclaracionInfonavit + ", motivosAclaracionAfore="
				+ motivosAclaracionAfore + ", creditoDescontado="
				+ creditoDescontado + ", otro=" + otro + ", especificacion=" + especificacion +"]";
	}
}
