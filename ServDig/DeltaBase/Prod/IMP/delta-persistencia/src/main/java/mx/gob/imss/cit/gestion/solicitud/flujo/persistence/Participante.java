package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bean de participante
 * @author softtek
 *
 */
@Entity
@Table(name = "DIC_PARTICIPANTE")
public class Participante implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = -3427255897418395468L;

    /**
     * Identificador del participante
     */
    @Id
    @Basic(optional = false)
    @Column(name = "CVE_ID_PARTICIPANTE")
    private Long idParticipante;

    /**
     * Nombre del participante
     */
    @Column(name = "NOM_PARTICIPANTE")
    private String nombre;

    /**
     * 
     * @return idParticipante
     */
    public Long getIdParticipante() {
        return idParticipante;
    }

    /**
     * 
     * @param idParticipante a fijar
     */
    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
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
