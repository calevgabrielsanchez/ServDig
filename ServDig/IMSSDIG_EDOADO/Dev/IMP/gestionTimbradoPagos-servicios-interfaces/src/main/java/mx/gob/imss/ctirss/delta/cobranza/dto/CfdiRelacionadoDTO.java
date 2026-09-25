package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="cfdiRelacionado")
public class CfdiRelacionadoDTO implements Serializable {
	
	private static final long serialVersionUID = 8769011820989592626L;
	
	private String Uuid;

	public String getUuid() {
		return Uuid;
	}

	public void setUuid(String uuid) {
		Uuid = uuid;
	}

}
