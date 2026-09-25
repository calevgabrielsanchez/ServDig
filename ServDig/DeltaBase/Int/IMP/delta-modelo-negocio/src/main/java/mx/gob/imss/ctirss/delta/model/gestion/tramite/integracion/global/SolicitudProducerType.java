package mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.global;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="JMSProducer_Input" , namespace="http://mx.gob.imss.delta.global.services/")
@XmlAccessorType(XmlAccessType.FIELD)
public class SolicitudProducerType implements Serializable {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -8237605045389633603L;
	
	@XmlElement(name="FolioSolicitud", namespace="http://mx.gob.imss.delta.global.services/", required = true)
	private String folioSolicitud;

	public String getFolioSolicitud() {
		return folioSolicitud;
	}

	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}
	
}
