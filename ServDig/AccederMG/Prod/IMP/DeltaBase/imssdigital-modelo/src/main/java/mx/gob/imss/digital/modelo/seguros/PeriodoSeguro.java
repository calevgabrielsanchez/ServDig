/**
 * 
 */
package mx.gob.imss.digital.modelo.seguros;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * CLase que representa las fechas de inicio y fin de un periodo a cotizar seguro
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "periodoSeguro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "periodoSeguro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class PeriodoSeguro implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Fecha de inicio del periodo de cotizacion
     */
    private Date fechaIncial;
    
    /**
     * Fecha final del periodo de cotizacion de un seguro
     */
    private Date fechaFinal;
    /**
     * Indica si el periodo a asociar a la nueva cotizacion se trata de una renovacion.
     */
    private boolean renovacion;

    /**
     * @return the fechaIncial
     */
    public Date getFechaIncial() {
        return fechaIncial;
    }

    /**
     * @param fechaIncial the fechaIncial to set
     */
    public void setFechaIncial(Date fechaIncial) {
        this.fechaIncial = fechaIncial;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    /**
     * @return the renovacion
     */
    public boolean getRenovacion() {
        return renovacion;
    }

    /**
     * @param renovacion the renovacion to set
     */
    public void setRenovacion(boolean renovacion) {
        this.renovacion = renovacion;
    }
    
    
}
