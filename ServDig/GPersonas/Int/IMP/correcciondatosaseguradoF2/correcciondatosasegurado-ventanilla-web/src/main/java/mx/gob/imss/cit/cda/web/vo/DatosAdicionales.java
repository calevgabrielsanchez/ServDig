package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DatosAdicionales implements Serializable {

    private static final long serialVersionUID = 748127687355131771L;

    private String numeroRegistroPatronal;

    private String domicilioEmpresa;

    private String actividadEmpresa;

    public DatosAdicionales() {
        super();
    }

    public DatosAdicionales(String numeroRegistroPatronal,
            String domicilioEmpresa, String actividadEmpresa) {
        super();
        this.numeroRegistroPatronal = numeroRegistroPatronal;
        this.domicilioEmpresa = domicilioEmpresa;
        this.actividadEmpresa = actividadEmpresa;
    }

    public String getNumeroRegistroPatronal() {
        return numeroRegistroPatronal;
    }

    public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
        this.numeroRegistroPatronal = numeroRegistroPatronal;
    }

    public String getDomicilioEmpresa() {
        return domicilioEmpresa;
    }

    public void setDomicilioEmpresa(String domicilioEmpresa) {
        this.domicilioEmpresa = domicilioEmpresa;
    }

    public String getActividadEmpresa() {
        return actividadEmpresa;
    }

    public void setActividadEmpresa(String actividadEmpresa) {
        this.actividadEmpresa = actividadEmpresa;
    }

    @Override
    public String toString() {
        return "DatosAdicionales [numeroRegistroPatronal="
                + numeroRegistroPatronal + ", domicilioEmpresa="
                + domicilioEmpresa + ", actividadEmpresa=" + actividadEmpresa
                + "]";
    }

}
