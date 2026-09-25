package mx.gob.imss.digital.modelo.persona;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "familiar", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "familiar", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class Familiar extends Fisica implements Serializable {

	private static final long serialVersionUID = 1L;

	private Parentesco parentesco;
	private boolean aplicaCuestionario;
	private boolean inscripcion;

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}
	
	public boolean getAplicaCuestionario() {
        return aplicaCuestionario;
    }

    /**
     * @param aplicaCuestionario the aplicaCuestionario to set
	     */
	 public void setAplicaCuestionario(boolean aplicaCuestionario) {
	     this.aplicaCuestionario = aplicaCuestionario;
	 }
	 
	 
	 
	 public boolean getInscripcion() {
	     return inscripcion;
	 }
	
	 /**
	  * @param inscripcion the inscripcion to set
	  */
	 public void setInscripcion(boolean inscripcion) {
	     this.inscripcion = inscripcion;
	 }
}
