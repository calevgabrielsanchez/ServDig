
package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CedulaProfesional  extends DocumentoProbatorio implements Serializable {
    
	private static final long serialVersionUID = 2907165932563970013L;
	private String cedula;
    private String profesion;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String value) {
        this.cedula = value;
    }
    
    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String value) {
        this.profesion = value;
    }

}