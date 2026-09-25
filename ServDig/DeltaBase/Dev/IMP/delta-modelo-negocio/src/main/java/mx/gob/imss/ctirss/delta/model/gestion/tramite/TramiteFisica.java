package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;

@XmlRootElement
public class TramiteFisica extends Tramite implements Serializable {

    private static final long serialVersionUID = 8610734872732198846L;

    private Fisica fisica;
    
    // Contiene los datos del ICA
 	private ICADatosRespuesta datosICA;
 // Contiene los datos del MDM
 	private MDMDatosEntrada datosMDM;
 	
	public Fisica getFisica() {
        return fisica;
    }

    public void setFisica(final Fisica fisica) {
        this.fisica = fisica;
    }

	public ICADatosRespuesta getDatosICA() {
		return datosICA;
	}

	public void setDatosICA(ICADatosRespuesta datosICA) {
		this.datosICA = datosICA;
	}
    
    public MDMDatosEntrada getDatosMDM() {
		return datosMDM;
	}

	public void setDatosMDM(MDMDatosEntrada datosMDM) {
		this.datosMDM = datosMDM;
	}

}
