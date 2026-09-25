package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

@XmlRootElement
public class TramiteMoral extends Tramite implements Serializable {

    private static final long serialVersionUID = -3100687850759826326L;

    private Moral moral;
    
    // Contiene los datos del ICA
  	private ICADatosRespuesta datosICA;
 // Contiene los datos del MDM
   	private MDMDatosEntrada datosMDM;
   	
	public Moral getMoral() {
        return moral;
    }

    public void setMoral(final Moral moral) {
        this.moral = moral;
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
