package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import java.io.Serializable;
import java.util.Date;

/**
 * Movimientos de la cuenta individual e ilogica, antes de persistir
 */
public class MovimientosCuentaIndividual implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String movimientoOrigen;
    private String movimientoDestino;
    private Long nssDestino;
    private Long nssOrigen;
    private Long consecutivo;
    private Long cuentaIndividualOrigen;
    private Long cuentaIndividualDestino;
    private Long claveMovimientoAclaracion;
    private Date fechaBaja;
    private Date fechaAlta;
    
    public String getMovimientoOrigen() {
        return movimientoOrigen;
    }
    public void setMovimientoOrigen(String movimientoOrigen) {
        this.movimientoOrigen = movimientoOrigen;
    }
    public String getMovimientoDestino() {
        return movimientoDestino;
    }
    public void setMovimientoDestino(String movimientoDestino) {
        this.movimientoDestino = movimientoDestino;
    }
    public Long getNssDestino() {
        return nssDestino;
    }
    public void setNssDestino(Long nssDestino) {
        this.nssDestino = nssDestino;
    }
    public Long getNssOrigen() {
        return nssOrigen;
    }
    public void setNssOrigen(Long nssOrigen) {
        this.nssOrigen = nssOrigen;
    }
    public Long getConsecutivo() {
        return consecutivo;
    }
    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }
    public Long getCuentaIndividualOrigen() {
        return cuentaIndividualOrigen;
    }
    public void setCuentaIndividualOrigen(Long cuentaIndividualOrigen) {
        this.cuentaIndividualOrigen = cuentaIndividualOrigen;
    }
    public Long getCuentaIndividualDestino() {
        return cuentaIndividualDestino;
    }
    public void setCuentaIndividualDestino(Long cuentaIndividualDestino) {
        this.cuentaIndividualDestino = cuentaIndividualDestino;
    }
    
    public Long getClaveMovimientoAclaracion() {
        return claveMovimientoAclaracion;
    }
    public void setClaveMovimientoAclaracion(Long claveMovimientoAclaracion) {
        this.claveMovimientoAclaracion = claveMovimientoAclaracion;
    }
    public Date getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    public Date getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    @Override
    public String toString() {
        return "MovimientosCuentaIndividual [movimientoOrigen="
                + movimientoOrigen + ", movimientoDestino=" + movimientoDestino
                + ", nssDestino=" + nssDestino + ", nssOrigen=" + nssOrigen
                + ", consecutivo=" + consecutivo + ", cuentaIndividualOrigen="
                + cuentaIndividualOrigen + ", cuentaIndividualDestino="
                + cuentaIndividualDestino + ", claveMovimientoAclaracion="
                + claveMovimientoAclaracion + ", fechaBaja=" + fechaBaja + "]";
    }
   
    
}
