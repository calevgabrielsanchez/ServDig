package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CartillaMilitar  extends DocumentoProbatorio implements Serializable
{
	private static final long serialVersionUID = 1L;
    private String noMatricula;

    public String getNoMatricula() {
        return noMatricula;
    }
    
    public void setNoMatricula(String value) {
        this.noMatricula = value;
    }

}
