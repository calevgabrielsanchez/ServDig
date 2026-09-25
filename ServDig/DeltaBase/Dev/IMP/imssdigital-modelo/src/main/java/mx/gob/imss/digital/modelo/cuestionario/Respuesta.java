/**
 * 
 */
package mx.gob.imss.digital.modelo.cuestionario;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.interfaces.MensajeError;

/**
 * Modelo de respuestas y preguntas
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuesta", namespace = "http://mx.gob.imss.digital.modelo.cuestionario")
@XmlRootElement(name = "respuesta", namespace = "http://mx.gob.imss.digital.modelo.cuestionario")
public class Respuesta implements Serializable, MensajeError {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de la pregunta
     */
    private int cvePregunta;
    /**
     * Valor de la respuesta
     */
    private Opcion[] valores;

	private String numPregunta;
	private int numSeccion;

    /**
     * Mensaje de error 
     */
    private String errorFormGeneral;

   

    /**
     * @return the cvePregunta
     */
    public int getCvePregunta() {
        return cvePregunta;
    }

    /**
     * @param cvePregunta the cvePregunta to set
     */
    public void setCvePregunta(int cvePregunta) {
        this.cvePregunta = cvePregunta;
    }

    /**
     * @return the valor
     */
    public Opcion[] getValores() {
        return valores;
    }

    /**
     * @param valor the valor to set
     */
    public void setValores(Opcion[] valores) {
        this.valores = valores != null ? valores.clone() : null;
    }

    public String getNumPregunta() {
		return numPregunta;
	}

	public void setNumPregunta(String numPregunta) {
		this.numPregunta = numPregunta;
	}

	public int getNumSeccion() {
		return numSeccion;
	}

	public void setNumSeccion(int numSeccion) {
		this.numSeccion = numSeccion;
	}

	/**
     * @return the errorFormGeneral
     */
    public String getErrorFormGeneral() {
        return errorFormGeneral;
    }

    /**
     * @param errorFormGeneral the errorFormGeneral to set
     */
    public void setErrorFormGeneral(String errorFormGeneral) {
        this.errorFormGeneral = errorFormGeneral;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cvePregunta;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Respuesta other = (Respuesta) obj;
        if (cvePregunta != other.cvePregunta)
            return false;
        return true;
    }
}
