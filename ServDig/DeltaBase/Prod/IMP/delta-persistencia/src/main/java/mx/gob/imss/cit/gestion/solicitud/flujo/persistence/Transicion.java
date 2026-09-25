package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bean de transicion
 * @author softtek
 *
 */
@Entity
@Table(name = "DIC_TRANSICION")
public class Transicion implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = -2227323172179928276L;

    /**
     * Identificador de la transicion
     */
    @Id
    @Basic(optional = false)
    @Column(name = "CVE_ID_TRANSICION")
    private Long idTransicion;

    /**
     * Tipo de transicion
     */
    @Column(name = "NOM_TRANSICION")
    private String tipoTransicion;

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

    /**
     * 
     * @return tipoTransicion
     */
    public String getTipoTransicion() {
        return tipoTransicion;
    }

    /**
     * 
     * @param tipoTransicion a fijar
     */
    public void setTipoTransicion(String tipoTransicion) {
        this.tipoTransicion = tipoTransicion;
    }

}
