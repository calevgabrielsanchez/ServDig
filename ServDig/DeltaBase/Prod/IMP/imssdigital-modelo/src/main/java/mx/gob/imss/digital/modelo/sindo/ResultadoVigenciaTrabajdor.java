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
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultadoVigenciaTrabajdor", namespace = "http://mx.gob.imss.digital.modelo.sindo")
@XmlRootElement(name = "resultadoVigenciaTrabajdor", namespace = "http://mx.gob.imss.digital.modelo.sindo")
public class ResultadoVigenciaTrabajdor implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Indica si el trabajador esta vigente 
     */
    private boolean indicadorVigente;
    /**
     * Modalidades en las que esta vigente el trabajador
     */
    private ModalidadTrabajador[] modalidadesFechaVigente;
    /**
     * Modalidades de baja del trabajador
     */
    private ModalidadTrabajador[] modalidadesFechaBaja;
    /**
     * Indica si es facultativo 0 o 1 Estudiante
     */
    private Integer tipoAseguradoBaja;
    /**
     * Ultimo NRP que se dio de baja el asegurado
     */
    private String nrpBaja;
    /**
     * Numero de semanas de 
     */
    private Integer numeroSemanaAseguramientoBaja;
    /**
     * @return the indicadorVigente
     */
    public boolean getIndicadorVigente() {
        return indicadorVigente;
    }
    /**
     * @param indicadorVigente the indicadorVigente to set
     */
    public void setIndicadorVigente(boolean indicadorVigente) {
        this.indicadorVigente = indicadorVigente;
    }
    /**
     * @return the modalidadesFechaVigente
     */
    public ModalidadTrabajador[] getModalidadesFechaVigente() {
        return modalidadesFechaVigente;
    }
    /**
     * @param modalidadesFechaVigente the modalidadesFechaVigente to set
     */
    public void setModalidadesFechaVigente(ModalidadTrabajador[] modalidadesFechaVigente) {
        this.modalidadesFechaVigente = modalidadesFechaVigente != null ? modalidadesFechaVigente.clone() : null;
    }
    /**
     * @return the modalidadesFechaBaja
     */
    public ModalidadTrabajador[] getModalidadesFechaBaja() {
        return modalidadesFechaBaja;
    }
    /**
     * @param modalidadesFechaBaja the modalidadesFechaBaja to set
     */
    public void setModalidadesFechaBaja(ModalidadTrabajador[] modalidadesFechaBaja) {
        this.modalidadesFechaBaja = modalidadesFechaBaja != null ? modalidadesFechaBaja.clone() : null;
    }
    /**
     * @return the tipoAseguradoBaja
     */
    public Integer getTipoAseguradoBaja() {
        return tipoAseguradoBaja;
    }
    /**
     * @param tipoAseguradoBaja the tipoAseguradoBaja to set
     */
    public void setTipoAseguradoBaja(Integer tipoAseguradoBaja) {
        this.tipoAseguradoBaja = tipoAseguradoBaja;
    }
    /**
     * @return the nrpBaja
     */
    public String getNrpBaja() {
        return nrpBaja;
    }
    /**
     * @param nrpBaja the nrpBaja to set
     */
    public void setNrpBaja(String nrpBaja) {
        this.nrpBaja = nrpBaja;
    }
    /**
     * @return the numeroSemanaAseguramientoBaja
     */
    public Integer getNumeroSemanaAseguramientoBaja() {
        return numeroSemanaAseguramientoBaja;
    }
    /**
     * @param numeroSemanaAseguramientoBaja the numeroSemanaAseguramientoBaja to set
     */
    public void setNumeroSemanaAseguramientoBaja(Integer numeroSemanaAseguramientoBaja) {
        this.numeroSemanaAseguramientoBaja = numeroSemanaAseguramientoBaja;
    }

    
}
