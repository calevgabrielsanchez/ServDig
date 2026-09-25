package mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConsultaPatron implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7424239242417244724L;
	
	private String registroPatronal;
	private String rfc;
	private Long idTipoPersona;
	private boolean consultaRegPatronales;
	
	
	
	
	public Long getIdTipoPersona() {
		return idTipoPersona;
	}
	public void setIdTipoPersona(Long idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}
	public boolean isConsultaRegPatronales() {
		return consultaRegPatronales;
	}
	public void setConsultaRegPatronales(boolean consultaRegPatronales) {
		this.consultaRegPatronales = consultaRegPatronales;
	}
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	
	
	
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	@Override
	public String toString() {
		return "ConsultaPatron [registroPatronal=" + registroPatronal + ", rfc=" + rfc + ", idTipoPersona="
				+ idTipoPersona + ", consultaRegPatronales=" + consultaRegPatronales + "]";
	}

	

}
