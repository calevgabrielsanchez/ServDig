
package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class AddendaDTO {
 
    private String registroPatronal;
    private String periodo;
    private String entidadRecaudadora;    
    private Date fechaPago;
    private BigDecimal folSUA;
    
    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }
    
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }        

    public String getEntidadRecaudadora() {
        return entidadRecaudadora;
    }

    public void setEntidadRecaudadora(String entidadRecaudadora) {
        this.entidadRecaudadora = entidadRecaudadora;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getFolSUA() {
        return folSUA;
    }

    public void setFolSUA(BigDecimal folSUA) {
        this.folSUA = folSUA;
    }
    
}
