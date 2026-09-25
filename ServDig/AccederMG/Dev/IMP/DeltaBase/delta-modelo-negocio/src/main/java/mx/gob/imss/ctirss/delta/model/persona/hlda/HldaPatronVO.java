package mx.gob.imss.ctirss.delta.model.persona.hlda;

import java.math.BigDecimal;

public class HldaPatronVO implements java.io.Serializable {

    private static final long serialVersionUID = 8553671736386912585L;

    private String fecInis;
    private String fecFini;
    private String nomPat;
    private String regPat;
    private BigDecimal salIni;
    private BigDecimal salFin;

    public String getFecInis() {
        return fecInis;
    }

    public void setFecInis(String fecInis) {
        this.fecInis = fecInis;
    }

    public String getFecFini() {
        return fecFini;
    }

    public void setFecFini(String fecFini) {
        this.fecFini = fecFini;
    }

    public String getNomPat() {
        return nomPat;
    }

    public void setNomPat(String nomPat) {
        this.nomPat = nomPat;
    }

    public BigDecimal getSalIni() {
        return salIni;
    }

    public void setSalIni(BigDecimal salIni) {
        this.salIni = salIni;
    }

    public BigDecimal getSalFin() {
        return salFin;
    }

    public void setSalFin(BigDecimal salFin) {
        this.salFin = salFin;
    }

    public String getRegPat() {
        return regPat;
    }

    public void setRegPat(String regPat) {
        this.regPat = regPat;
    }

}
