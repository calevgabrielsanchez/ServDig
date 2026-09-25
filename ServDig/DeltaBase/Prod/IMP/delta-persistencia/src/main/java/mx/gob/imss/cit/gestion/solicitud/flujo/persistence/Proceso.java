package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bean del proceso
 * @author softtek
 *
 */
@Entity
@Table(name = "DIC_PROCESO")
public class Proceso implements Serializable {

    /**
     * Numere de version
     */
    private static final long serialVersionUID = -5566413602080178830L;

    /**
     * Identificador del proceso
     */
    @Id
    @Basic(optional = false)
    @Column(name = "CVE_ID_PROCESO")
    private Long bp;

    /**
     * Nombre del proceso
     */
    @Column(name = "NOM_PROCESO")
    private String nombre;

    /**
     * 
     * @return bp
     */
    public Long getBp() {
        return bp;
    }

    /**
     * 
     * @param bp a fijar
     */
    public void setBp(Long bp) {
        this.bp = bp;
    }

    /**
     * 
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre a fijar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
