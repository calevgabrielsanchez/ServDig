package mx.gob.imss.digital.modelo.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tramiteSeguroIvroMod33",
		namespace = "http://mx.gob.imss.digital.modelo.tramite")
@XmlRootElement(name = "tramiteSeguroIvroMod33",
		namespace = "http://mx.gob.imss.digital.modelo.tramite")
/**
 * Clase de modelo que se utilizaba en una primera versión, pero los 
 * atributos que tenía se pasaron a su clase padre
 * para evitar tener N transformaciones en el OSB, 
 * se deja la clase para evitar que los seguros ya generados fallen
 */
public class TramiteSeguroIvroMod33 extends TramiteSeguroIvro implements
		Serializable {

	private static final long serialVersionUID = 1L;

}