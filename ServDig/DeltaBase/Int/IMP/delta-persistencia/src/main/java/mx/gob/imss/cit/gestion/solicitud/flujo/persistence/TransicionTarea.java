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
 * Bean de transicion de tarea
 * @author softtek
 *
 */
@Entity
@Table(name = "DIC_TRANSIC_TAREA")
public class TransicionTarea implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 7787926801736900676L;

    /**
     * Llave primaria de transicion de tarea
     */
    @EmbeddedId
    private TransicionTareaPK transicionTareaPK;

    /**
     * Identificador de transicion
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_TRANSICION", insertable = false, updatable = false)
    private Transicion transicion;

    /**
     * Identificador de tarea
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CVE_ID_TAREA", insertable = false, updatable = false)
    private Tarea tarea;

    /**
     * 
     * @return transicionTareaPK
     */
    public TransicionTareaPK getTransicionTareaPK() {
        return transicionTareaPK;
    }

    /**
     * 
     * @param transicionTareaPK a fijar
     */
    public void setTransicionTareaPK(TransicionTareaPK transicionTareaPK) {
        this.transicionTareaPK = transicionTareaPK;
    }

    /**
     * 
     * @return  transicion
     */
    public Transicion getTransicion() {
        return transicion;
    }

    /**
     * 
     *  @param transicion a fijar
     */
    public void setTransicion(Transicion transicion) {
        this.transicion = transicion;
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

}

/**
 * Clase para establecer el identificador de la transicion de la tarea
 * @author softtek
 *
 */
@Embeddable
class TransicionTareaPK implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4599336921491458743L;

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
     * @param idTransicion idTransicion
     */
    public void setIdTransicion(Long idTransicion) {
        this.idTransicion = idTransicion;
    }

}
