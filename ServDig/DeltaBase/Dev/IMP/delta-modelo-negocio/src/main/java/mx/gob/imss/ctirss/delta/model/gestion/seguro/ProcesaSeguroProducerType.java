package mx.gob.imss.ctirss.delta.model.gestion.seguro;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="procesaSeguroRequest" , namespace="http://mx.gob.imss.delta.global.services/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcesaSeguroProducerType implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = -8237605045389633703L;

    @XmlElement(name="seguroId", namespace="http://mx.gob.imss.delta.global.services/", required = true)
    private String seguroId;

    @XmlElement(name="procesoId", namespace="http://mx.gob.imss.delta.global.services/", required = true)
    private String procesoId;

    public String getSeguroId() {
        return seguroId;
    }

    public void setSeguroId(String seguroId) {
        this.seguroId = seguroId;
    }

    public String getProcesoId() {
        return procesoId;
    }

    public void setProcesoId(String procesoId) {
        this.procesoId = procesoId;
    }
}

