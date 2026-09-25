package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO para detalle de baja por mora.
 */
public class DetalleMoraDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date fechaEfectivaBaja;
    private Date fechaUltimoPago;
    private Date fechaPrimerVencimiento;
    private Date fechaUltimoVencimiento;
    private Integer mesesMora;
    private String montosAdeudos;
    private BigDecimal recargos;
    private BigDecimal actualizaciones;
    private String periodosVencidos;
    private String registroPatronal;

    public DetalleMoraDTO() {
    }

    public Date getFechaEfectivaBaja() {
        return fechaEfectivaBaja;
    }

    public void setFechaEfectivaBaja(Date fechaEfectivaBaja) {
        this.fechaEfectivaBaja = fechaEfectivaBaja;
    }

    public Date getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(Date fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public Date getFechaPrimerVencimiento() {
        return fechaPrimerVencimiento;
    }

    public void setFechaPrimerVencimiento(Date fechaPrimerVencimiento) {
        this.fechaPrimerVencimiento = fechaPrimerVencimiento;
    }

    public Date getFechaUltimoVencimiento() {
        return fechaUltimoVencimiento;
    }

    public void setFechaUltimoVencimiento(Date fechaUltimoVencimiento) {
        this.fechaUltimoVencimiento = fechaUltimoVencimiento;
    }

    public Integer getMesesMora() {
        return mesesMora;
    }

    public void setMesesMora(Integer mesesMora) {
        this.mesesMora = mesesMora;
    }

    public String getMontosAdeudos() {
        return montosAdeudos;
    }

    public void setMontosAdeudos(String montosAdeudos) {
        this.montosAdeudos = montosAdeudos;
    }

    public BigDecimal getRecargos() {
        return recargos;
    }

    public void setRecargos(BigDecimal recargos) {
        this.recargos = recargos;
    }

    public BigDecimal getActualizaciones() {
        return actualizaciones;
    }

    public void setActualizaciones(BigDecimal actualizaciones) {
        this.actualizaciones = actualizaciones;
    }

    public String getPeriodosVencidos() {
        return periodosVencidos;
    }

    public void setPeriodosVencidos(String periodosVencidos) {
        this.periodosVencidos = periodosVencidos;
    }

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }
}
