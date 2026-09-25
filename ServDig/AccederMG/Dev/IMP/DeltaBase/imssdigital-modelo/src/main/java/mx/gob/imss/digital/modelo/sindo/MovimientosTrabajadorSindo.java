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
 * MOdelo para una lista de movimientos de un trabajador
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movimientosTrabajadorSindo", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "movimientosTrabajadorSindo", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class MovimientosTrabajadorSindo implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Lista de movimientos de cada trabajador
     */
    private MovimientoTrabajadorSindo[] movimientoTrabajadorSindo;
    
    /**
     * Lista de movimientos sindo que se aplicaran a futuro
     */
    private MovimientoTrabajadorSindo[] movimientoTrabajadorSindoFuturo;
    /**
     * @return the movimientoTrabajadorSindo
     */
    public MovimientoTrabajadorSindo[] getMovimientoTrabajadorSindo() {
        return movimientoTrabajadorSindo;
    }
    /**
     * @param movimientoTrabajadorSindo the movimientoTrabajadorSindo to set
     */
    public void setMovimientoTrabajadorSindo(MovimientoTrabajadorSindo[] movimientoTrabajadorSindo) {
        this.movimientoTrabajadorSindo = movimientoTrabajadorSindo != null ? movimientoTrabajadorSindo.clone() : null;
    }
    /**
     * @return the movimientoTrabajadorSindoFuturo
     */
    public MovimientoTrabajadorSindo[] getMovimientoTrabajadorSindoFuturo() {
        return movimientoTrabajadorSindoFuturo;
    }
    /**
     * @param movimientoTrabajadorSindoFuturo the movimientoTrabajadorSindoFuturo to set
     */
    public void setMovimientoTrabajadorSindoFuturo(
            MovimientoTrabajadorSindo[] movimientoTrabajadorSindoFuturo) {
        this.movimientoTrabajadorSindoFuturo = movimientoTrabajadorSindoFuturo != null ? movimientoTrabajadorSindoFuturo.clone() : null;
    }
    
}
