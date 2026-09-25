/**
 * 
 */
package mx.gob.imss.digital.modelo.sindo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * MOdelo pcon la informacion de altas y bajas de bigencias de un trabajador en diferentes modalidades
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vigenciaTrabajdor", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "vigenciaTrabajdor", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class VigenciaTrabajdor implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Clave de error
     */
    private String claveError;
    /**
     * Mensaje de error
     */
    private String mensajeError;
    /**
     * Resultado de las vigencias
     */
    private ResultadoVigenciaTrabajdor resultado;
    /**
     * Modalidad que solicita
     */
    private String modalidadSolicitada;
    /**
     * @return the claveError
     */
    public String getClaveError() {
        return claveError;
    }
    /**
     * @param claveError the claveError to set
     */
    public void setClaveError(String claveError) {
        this.claveError = claveError;
    }
    /**
     * @return the mensajeError
     */
    public String getMensajeError() {
        return mensajeError;
    }
    /**
     * @param mensajeError the mensajeError to set
     */
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    /**
     * @return the resultado
     */
    public ResultadoVigenciaTrabajdor getResultado() {
        return resultado;
    }
    /**
     * @param resultado the resultado to set
     */
    public void setResultado(ResultadoVigenciaTrabajdor resultado) {
        this.resultado = resultado;
    }
    /**
     * @return the modalidadSolicitada
     */
    public String getModalidadSolicitada() {
        return modalidadSolicitada;
    }
    /**
     * @param modalidadSolicitada the modalidadSolicitada to set
     */
    public void setModalidadSolicitada(String modalidadSolicitada) {
        this.modalidadSolicitada = modalidadSolicitada;
    }

}
