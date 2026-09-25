package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TramiteRTT extends Tramite implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
        
        private Moral moral;

    public Moral getMoral() {
        return moral;
    }

    public void setMoral(Moral moral) {
        this.moral = moral;
    }
        


}
