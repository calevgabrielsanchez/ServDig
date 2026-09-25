package mx.gob.imss.ctirss.delta.model.riesgosTrabajo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RiesgosTrabajoXML implements Serializable{

    private static final long serialVersionUID = 6395324628718075592L;

    private List<RiesgoTrabajo> riesgo;

    public List<RiesgoTrabajo> getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(List<RiesgoTrabajo> riesgo) {
        this.riesgo = riesgo;
    }
    
    
}
