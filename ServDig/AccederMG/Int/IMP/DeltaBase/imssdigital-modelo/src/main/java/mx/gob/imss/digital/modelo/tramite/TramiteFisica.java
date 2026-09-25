package mx.gob.imss.digital.modelo.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.persona.Fisica;

/**
 * 
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tramiteFisica", namespace = "http://mx.gob.imss.digital.modelo.tramite")
@XmlRootElement(name = "tramiteFisica", namespace = "http://mx.gob.imss.digital.modelo.tramite")
public class TramiteFisica extends Tramite implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 8610734872732198846L;

    /**
     * Persona fisica del tramite
     */
    private Fisica fisica;

    /**
     * @return the fisica
     */
    public Fisica getFisica() {
        return fisica;
    }

    /**
     * @param fisica the fisica to set
     */
    public void setFisica(Fisica fisica) {
        this.fisica = fisica;
    }
    
    

}
