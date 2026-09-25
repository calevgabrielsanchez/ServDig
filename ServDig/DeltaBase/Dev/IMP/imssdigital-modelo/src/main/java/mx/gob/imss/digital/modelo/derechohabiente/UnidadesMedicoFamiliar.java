package mx.gob.imss.digital.modelo.derechohabiente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unidadesMedicoFamiliar", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
@XmlRootElement(name = "unidadesMedicoFamiliar", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
public class UnidadesMedicoFamiliar implements Serializable {
	private static final long serialVersionUID = 1L;
	private UnidadMedicoFamiliar[] unidadMedicoFamiliar;

	public UnidadMedicoFamiliar[] getUnidadMedicoFamiliar() {
		return unidadMedicoFamiliar;
	}

	public void setUnidadMedicoFamiliar(
			UnidadMedicoFamiliar[] unidadMedicoFamiliar) {
		this.unidadMedicoFamiliar = unidadMedicoFamiliar;
	}
}
