package mx.gob.imss.ctirss.delta.model.persona.hlda;

import java.math.BigDecimal;
import java.util.List;

public class HldaVO implements java.io.Serializable {

    private static final long serialVersionUID = -1492526715223542410L;

    private String nss;
    private String apelPat;
    private String apelMat;
    private String nombre;
    private Integer totSemCot;
    private BigDecimal sdo250;
    private List<HldaPatronVO> patrones;
    private List<HldaDetalleVO> detalle;

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getApelPat() {
        return apelPat;
    }

    public void setApelPat(String apelPat) {
        this.apelPat = apelPat;
    }

    public String getApelMat() {
        return apelMat;
    }

    public void setApelMat(String apelMat) {
        this.apelMat = apelMat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTotSemCot() {
        return totSemCot;
    }

    public void setTotSemCot(Integer totSemCot) {
        this.totSemCot = totSemCot;
    }

    public BigDecimal getSdo250() {
        return sdo250;
    }

    public void setSdo250(BigDecimal sdo250) {
        this.sdo250 = sdo250;
    }

    public List<HldaPatronVO> getPatrones() {
        return patrones;
    }

    public void setPatrones(List<HldaPatronVO> patrones) {
        this.patrones = patrones;
    }

    public List<HldaDetalleVO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<HldaDetalleVO> detalle) {
        this.detalle = detalle;
    }

}
