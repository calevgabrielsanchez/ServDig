package mx.gob.imss.ctirss.delta.model.persona.hlda;

import java.math.BigDecimal;

public class HldaDetalleVO implements java.io.Serializable {

    private Integer semana;
    private Integer anio;
    private BigDecimal sdoProm;
    private BigDecimal sdoDic;


    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public BigDecimal getSdoProm() {
        return sdoProm;
    }

    public void setSdoProm(BigDecimal sdoProm) {
        this.sdoProm = sdoProm;
    }

    public BigDecimal getSdoDic() {
        return sdoDic;
    }

    public void setSdoDic(BigDecimal sdoDic) {
        this.sdoDic = sdoDic;
    }

}

