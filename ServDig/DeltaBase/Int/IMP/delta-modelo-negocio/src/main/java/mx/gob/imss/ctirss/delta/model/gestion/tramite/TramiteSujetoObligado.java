package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@XmlRootElement
public class TramiteSujetoObligado extends Tramite implements Serializable {

    private static final long serialVersionUID = 2053274128483634403L;

    private SujetoObligado sujetoObligado;
    
	private int solicitudEnProcesoOSB;
	

	public int getSolicitudEnProcesoOSB() {
		return solicitudEnProcesoOSB;
	}

	public void setSolicitudEnProcesoOSB(int solicitudEnProcesoOSB) {
		this.solicitudEnProcesoOSB = solicitudEnProcesoOSB;
	}


    public SujetoObligado getSujetoObligado() {
        return sujetoObligado;
    }

    public void setSujetoObligado(final SujetoObligado sujetoObligado) {
        this.sujetoObligado = sujetoObligado;
    }
}
