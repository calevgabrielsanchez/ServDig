package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TramiteAclaracionSemanasCotizadas extends Tramite implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> listNss;

    public List<String> getListNss() {
        return listNss;
    }

    public void setListNss(List<String> listNss) {
        this.listNss = listNss;
    }
}
