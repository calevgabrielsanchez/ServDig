package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;
import java.math.BigDecimal;
import java.util.Date;

public class PeriodoDTO {

    private Long idPagoPeriodo;
    private Integer anio;
    private Integer mes;
    private Date fechaInicio;
    private Date fechaFin;
    private BigDecimal importeBase;
    private BigDecimal importeActualizacion;
    private BigDecimal importeRecargo;
    private BigDecimal importePago;

    public PeriodoDTO() {
    }

    public Long getIdPagoPeriodo() {
        return idPagoPeriodo;
    }

    public void setIdPagoPeriodo(Long idPagoPeriodo) {
        this.idPagoPeriodo = idPagoPeriodo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getImporteBase() {
        return importeBase;
    }

    public void setImporteBase(BigDecimal importeBase) {
        this.importeBase = importeBase;
    }

    public BigDecimal getImporteActualizacion() {
        return importeActualizacion;
    }

    public void setImporteActualizacion(BigDecimal importeActualizacion) {
        this.importeActualizacion = importeActualizacion;
    }

    public BigDecimal getImporteRecargo() {
        return importeRecargo;
    }

    public void setImporteRecargo(BigDecimal importeRecargo) {
        this.importeRecargo = importeRecargo;
    }

    public BigDecimal getImportePago() {
        return importePago;
    }

    public void setImportePago(BigDecimal importePago) {
        this.importePago = importePago;
    }
}