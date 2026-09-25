package mx.gob.imss.digital.modelo.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.persona.Moral;

/**
 * 
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tramiteMoral", namespace = "http://mx.gob.imss.digital.modelo.tramite")
@XmlRootElement(name = "tramiteMoral", namespace = "http://mx.gob.imss.digital.modelo.tramite")
public class TramiteMoral extends Tramite implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -3100687850759826326L;

    /**
     * PErsona moral asociada al tramite
     */
    private Moral moral; 
    
   	
	public Moral getMoral() {
        return moral;
    }

    public void setMoral(final Moral moral) {
        this.moral = moral;
    }
}
