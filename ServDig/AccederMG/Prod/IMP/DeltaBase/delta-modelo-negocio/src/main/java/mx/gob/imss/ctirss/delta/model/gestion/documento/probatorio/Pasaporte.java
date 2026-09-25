package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pasaporte extends DocumentoProbatorio implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -174188705923766114L;
	private String noPasaporte;
    private Date fechaCaducidad;

    public String getNoPasaporte() {
        return noPasaporte;
    }
    
    public void setNoPasaporte(String value) {
        this.noPasaporte = value;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date value) {
        this.fechaCaducidad = value;
    }

}
