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
 * MOdelo que representa las respuesta contestadas en un cuestionario
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestasCuestionario", namespace = "http://mx.gob.imss.digital.modelo.cuestionario")
@XmlRootElement(name = "respuestasCuestionario", namespace = "http://mx.gob.imss.digital.modelo.cuestionario")
public class RespuestasCuestionario implements Serializable, MensajeError {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador del tipo de cuestionaio
     */
    private int tipoCuestionario;
    /**
     * Listas de respuestas con sus preguntas
     */
    private Respuesta[] respuestas;
    /**
     * Sumatoria del valor de las respuestas
     */
    private int sumatoriaRespuestas;
    
    /**
     * Mensaje de error 
     */
    private String errorFormGeneral;
    /**
     * @return the tipoCuestionario
     */
    public int getTipoCuestionario() {
        return tipoCuestionario;
    }
    /**
     * @param tipoCuestionario the tipoCuestionario to set
     */
    public void setTipoCuestionario(int tipoCuestionario) {
        this.tipoCuestionario = tipoCuestionario;
    }
    
    /**
     * @return the respuestas
     */
    public Respuesta[] getRespuestas() {
        return respuestas;
    }
    /**
     * @param respuestas the respuestas to set
     */
    public void setRespuestas(Respuesta[] respuestas) {
        this.respuestas = respuestas != null ? respuestas.clone() : null;
    }
    /**
     * @return the sumatoriaRespuestas
     */
    public int getSumatoriaRespuestas() {
        return sumatoriaRespuestas;
    }
    /**
     * @param sumatoriaRespuestas the sumatoriaRespuestas to set
     */
    public void setSumatoriaRespuestas(int sumatoriaRespuestas) {
        this.sumatoriaRespuestas = sumatoriaRespuestas;
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

    
}
