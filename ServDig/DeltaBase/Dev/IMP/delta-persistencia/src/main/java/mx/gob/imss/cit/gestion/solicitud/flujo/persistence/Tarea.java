package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Bean de tarea
 * @author softtek
 *
 */
@Entity
@Table(name = "DIC_TAREA")
public class Tarea implements Serializable {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 5294164046739906732L;

    /**
     * Identificador de tarea
     */
    @Id
    @Basic(optional = false)
    @Column(name = "CVE_ID_TAREA")
    private Long idTarea;

    /**
     * Nombre de la tarea
     */
    @Column(name = "NOM_TAREA")
    private String nombre;

    /**
     * Identificador del participante
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "CVE_ID_PARTICIPANTE", updatable = false, insertable = false)
    private Participante participante;

    /**
     * Variable de inicio
     */
    @Column(name = "IND_TAREA_INICIAL")
    private Boolean inicial;

    /**
     * Identificador del proceso
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "CVE_ID_PROCESO", updatable = false, insertable = false)
    private Proceso proceso;

    /**
     * Variable del subproceso
     */
    @Column(name = "REF_SUBPROCESO")
    private Boolean subProceso;

    /**
     * Temporizador
     */
    @Column(name = "REF_VARIABLE_TIMMER")
    private String nombreVariableTimmer;

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

    /**
     * 
     * @return participante
     */
    public Participante getParticipante() {
        return participante;
    }

    /**
     * 
     * @param participante a fijar
     */
    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    /**
     * 
     * @return inicial
     */
    public Boolean getInicial() {
        return inicial;
    }

    /**
     * 
     * @param inicial a fijar
     */
    public void setInicial(Boolean inicial) {
        this.inicial = inicial;
    }

    /**
     * 
     * @return proceso
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * 
     * @param proceso a fijar
     */
    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    /**
     * 
     * @return subProceso
     */
    public Boolean getSubProceso() {
        return subProceso;
    }

    /**
     * 
     * @param subProceso a fijar
     */
    public void setSubProceso(Boolean subProceso) {
        this.subProceso = subProceso;
    }

    /**
     * 
     * @return nombreVariableTimmer
     */
    public String getNombreVariableTimmer() {
        return nombreVariableTimmer;
    }

    /**
     * 
     * @param nombreVariableTimmer a fijar
     */
    public void setNombreVariableTimmer(String nombreVariableTimmer) {
        this.nombreVariableTimmer = nombreVariableTimmer;
    }

}
