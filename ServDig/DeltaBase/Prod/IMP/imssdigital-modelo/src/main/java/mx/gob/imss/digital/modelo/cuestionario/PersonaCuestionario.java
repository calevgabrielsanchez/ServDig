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
 * Modelo que identifica a una persona con su cuesionario
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personaCuestionario", namespace = "http://mx.gob.imss.digital.modelo.cuestionario")
@XmlRootElement(name = "personaCuestionario", namespace = "http://mx.gob.imss.digital.modelo.cuestionario")
public class PersonaCuestionario implements Serializable, MensajeError {
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador de la persona
     */
    private Long idPersona;
    /**
     * Respuestas del cuestionario asociadas a una persona
     */
    private RespuestasCuestionario respuestasCuestionario;
    /**
     * Indica si el cuestionario es valido
     */
    private Boolean cuestionarioValido;
    /**
     * Mensajes de error
     */
    private String errorFormGeneral;
    
    private String nssPersona;
    
    /**
     * @return the idPersona
     */
    public Long getIdPersona() {
        return idPersona;
    }
    /**
     * @param idPersona the idPersona to set
     */
    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }
    /**
     * @return the respuestasCuestionario
     */
    public RespuestasCuestionario getRespuestasCuestionario() {
        return respuestasCuestionario;
    }
    /**
     * @param respuestasCuestionario the respuestasCuestionario to set
     */
    public void setRespuestasCuestionario(RespuestasCuestionario respuestasCuestionario) {
        this.respuestasCuestionario = respuestasCuestionario;
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
    /**
     * @return the cuestionarioValido
     */
    public Boolean getCuestionarioValido() {
        return cuestionarioValido;
    }
    /**
     * @param cuestionarioValido the cuestionarioValido to set
     */
    public void setCuestionarioValido(Boolean cuestionarioValido) {
        this.cuestionarioValido = cuestionarioValido;
    }
    
	public String getNssPersona() {
		return nssPersona;
	}
	public void setNssPersona(String nssPersona) {
		this.nssPersona = nssPersona;
	}

    
    
}
