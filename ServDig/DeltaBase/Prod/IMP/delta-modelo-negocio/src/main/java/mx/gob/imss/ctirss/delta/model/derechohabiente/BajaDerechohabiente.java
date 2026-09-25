package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Tramite;

@XmlRootElement
public class BajaDerechohabiente extends Tramite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fechaDefuncion;

	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}

	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}
	
	
}
