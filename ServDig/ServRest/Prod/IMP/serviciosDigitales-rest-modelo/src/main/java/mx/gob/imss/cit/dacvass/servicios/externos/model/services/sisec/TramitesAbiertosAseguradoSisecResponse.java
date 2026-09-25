package mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TramitesAbiertosAseguradoSisecResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5755848597471775836L;

	private boolean indicadorTramitesAbierto;
	private String mensaje;
	
	
	public boolean isIndicadorTramitesAbierto() {
		return indicadorTramitesAbierto;
	}
	public void setIndicadorTramitesAbierto(boolean indicadorTramitesAbierto) {
		this.indicadorTramitesAbierto = indicadorTramitesAbierto;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	

}
