/**
 * 
 */
package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Lista de compras pagadas
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actualizacionCompras", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "compras",
        "lineasEnError"
    })
@XmlRootElement(name = "actualizacionCompras", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class ActualizacionCompra implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Lista de compras pagadas
     */
    private DatosCompra[] compras;
    
    /**
     * Lista de lineas de catura que generaron un error y no se calcularon
     */
    private String[] lineasEnError;

    /**
     * @return the compras
     */
    public DatosCompra[] getCompras() {
        return compras;
    }

    /**
     * @param compras the compras to set
     */
    public void setCompras(DatosCompra[] compras) {
        this.compras = compras != null ? compras.clone() : null;
    }

    /**
     * @return the lineasEnError
     */
    public String[] getLineasEnError() {
        return lineasEnError;
    }

    /**
     * @param lineasEnError the lineasEnError to set
     */
    public void setLineasEnError(String[] lineasEnError) {
        this.lineasEnError = lineasEnError != null ? lineasEnError.clone() : null;
    }   
    
}
