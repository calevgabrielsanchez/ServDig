/**
 * 
 */
package mx.gob.imss.digital.modelo.seguros;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Agrupacion de varios cuestionarios para impresion
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cuestionariosSeguroReporte", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "cuestionariosSeguroReporte", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class CuestionariosSeguroReporte implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Lista de cuestionarios
     */
    private CuestionarioSeguroReporte [] cuestionarios;
    
    /**
     * @return the cuestionarios
     */
    public CuestionarioSeguroReporte[] getCuestionarios() {
        return cuestionarios;
    }
    /**
     * @param cuestionarios the cuestionarios to set
     */
    public void setCuestionarios(CuestionarioSeguroReporte[] cuestionarios) {
        this.cuestionarios = cuestionarios != null ? cuestionarios.clone() : null;
    }
    
}
