package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Bean para la transicion de la tarea
 * @author softtek
 *
 */
@Entity
@Table(name = "DIC_TAREA_TRANSIC")
public class TareaTransicion implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 292213276844087867L;

    /**
     * Inyeccion de la calse de TareaTransicionPK
     */
    @EmbeddedId
    private TareaTransicionPK tareaTransicionPK;

    /**
     * Propiedad de Tarea
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_TAREA", insertable = false, updatable = false)
    private Tarea tarea;

    /**
     * Propiedad de Transicion
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_TRANSICION", insertable = false, updatable = false)
    private Transicion transicion;

    /**
     * 
     * @return tareaTransicion
     */
    public TareaTransicionPK getTareaTransicionPK() {
        return tareaTransicionPK;
    }

    /**
     * 
     * @param tareaTransicionPK a fijar
     */
    public void setTareaTransicionPK(TareaTransicionPK tareaTransicionPK) {
        this.tareaTransicionPK = tareaTransicionPK;
    }

    /**
     * 
     * @return tarea
     */
    public Tarea getTarea() {
        return tarea;
    }

    /**
     * 
     * @param tarea a fijar
     */
    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    /**
     * 
     * @return transicion
     */
    public Transicion getTransicion() {
        return transicion;
    }

    /**
     * 
     * @param transicion a fijar
     */
    public void setTransicion(Transicion transicion) {
        this.transicion = transicion;
    }

}

/**
 * Clase para establecer el identificador de la tarea en transicion
 * @author softtek
 *
 */
@Embeddable
class TareaTransicionPK implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = -2396495445319347901L;

    /**
     * Identificador de la tarea
     */
    @Column(name = "CVE_ID_TAREA")
    private Long idTarea;

    /**
     * Identificador de la transicion
     */
    @Column(name = "CVE_ID_TRANSICION")
    private Long idTransicion;

    /**
     * 
     * @return idTarea
     */
    public Long getIdTarea() {
        return idTarea;
    }

    /**
     * 
     * @param idTarea a fijar
     */
    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    /**
     * 
     * @return idTransicion
     */
    public Long getIdTransicion() {
        return idTransicion;
    }

    /**
     * 
     * @param idTransicion a fijar
     */
    public void setIdTransicion(Long idTransicion) {
        this.idTransicion = idTransicion;
    }

}
