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
 * Respuesta de validacion del trabajador
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaValidacionTrabajador", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "respuestaValidacionTrabajador", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class RespuestaValidacionTrabajador implements Serializable {

    /**
     * serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Indica si la persona es valida para comprar seguro
     */
    private Boolean valido;
    /**
     * Indica si la persona aplica cuestionario
     */
    private Boolean aplicaCuestionario;
    /**
     * MEnsaje de error si no es valido para contratar el seguro
     */
    private String mensajeValidacion;
    /**
     * @return the valido
     */
    public Boolean getValido() {
        return valido;
    }
    /**
     * @param valido the valido to set
     */
    public void setValido(Boolean valido) {
        this.valido = valido;
    }
    /**
     * @return the aplicaCuestionario
     */
    public Boolean getAplicaCuestionario() {
        return aplicaCuestionario;
    }
    /**
     * @param aplicaCuestionario the aplicaCuestionario to set
     */
    public void setAplicaCuestionario(Boolean aplicaCuestionario) {
        this.aplicaCuestionario = aplicaCuestionario;
    }
    /**
     * @return the mensajeValidacion
     */
    public String getMensajeValidacion() {
        return mensajeValidacion;
    }
    /**
     * @param mensajeValidacion the mensajeValidacion to set
     */
    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }
    
    
}
