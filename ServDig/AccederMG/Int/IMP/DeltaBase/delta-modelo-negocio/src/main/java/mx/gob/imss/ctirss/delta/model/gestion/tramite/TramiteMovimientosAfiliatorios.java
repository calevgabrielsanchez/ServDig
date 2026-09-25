package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TramiteMovimientosAfiliatorios extends Tramite implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -146841503195472923L;
	
	private SujetoObligado sujetoObligado;

    public SujetoObligado getSujetoObligado() {
        return sujetoObligado;
    }

    public void setSujetoObligado(final SujetoObligado sujetoObligado) {
        this.sujetoObligado = sujetoObligado;
    }

}
