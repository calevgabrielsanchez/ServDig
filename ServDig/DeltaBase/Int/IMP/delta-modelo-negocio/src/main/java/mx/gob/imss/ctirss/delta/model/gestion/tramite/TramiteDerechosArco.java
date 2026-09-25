package mx.gob.imss.ctirss.delta.model.gestion.tramite;


import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TramiteDerechosArco extends Tramite implements Serializable {

    private static final long serialVersionUID = 5568754525769975162L;

    private AsignacionNSS nss;
    private String matricula;
    private String motivo;
    private String fundamentoLegal;
    private EstadoDerechohabiente estadoDerechohabiente;

    public AsignacionNSS getNss() {

        return nss;
    }

    public void setNss(AsignacionNSS nss) {

        this.nss = nss;
    }

    public String getMotivo() {

        return motivo;
    }

    public void setMotivo(String motivo) {

        this.motivo = motivo;
    }

    public String getMatricula() {

        return matricula;
    }

    public void setMatricula(String matricula) {

        this.matricula = matricula;
    }

    public String getFundamentoLegal() {

        return fundamentoLegal;
    }

    public void setFundamentoLegal(String fundamentoLegal) {

        this.fundamentoLegal = fundamentoLegal;
    }

    public EstadoDerechohabiente getEstadoDerechohabiente() {

        return estadoDerechohabiente;
    }

    public void setEstadoDerechohabiente(EstadoDerechohabiente estadoDerechohabiente) {

        this.estadoDerechohabiente = estadoDerechohabiente;
    }
}
