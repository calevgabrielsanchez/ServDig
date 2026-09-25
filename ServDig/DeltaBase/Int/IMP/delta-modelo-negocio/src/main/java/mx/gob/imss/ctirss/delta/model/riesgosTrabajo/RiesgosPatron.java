package mx.gob.imss.ctirss.delta.model.riesgosTrabajo;

import java.io.Serializable;
import java.util.List;

public class RiesgosPatron implements Serializable {


    private static final long serialVersionUID = -7493649100601858354L;

    private List<RiesgoTrabajo> riesgos;
    private PatronRiesgosTrabajo patron;

    public List<RiesgoTrabajo> getRiesgos() {
        return riesgos;
    }

    public void setRiesgos(List<RiesgoTrabajo> riesgos) {
        this.riesgos = riesgos;
    }

    public PatronRiesgosTrabajo getPatron() {
        return patron;
    }

    public void setPatron(PatronRiesgosTrabajo patron) {
        this.patron = patron;
    }
}
