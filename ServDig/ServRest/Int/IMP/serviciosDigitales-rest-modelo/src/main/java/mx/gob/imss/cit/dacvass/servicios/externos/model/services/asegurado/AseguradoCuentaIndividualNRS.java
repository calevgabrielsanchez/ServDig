package mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AseguradoCuentaIndividualNRS extends AseguradoCuentaIndividual implements Serializable
{

	private static final long serialVersionUID = -7291115511165650676L;
	private String nombreRazonSocial;
	
	
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	

}
