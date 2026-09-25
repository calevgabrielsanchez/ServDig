package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PagoCVRO implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long cveIdPago;
	private Date fecInicioPeriodo;
	private Date fecFinPeriodo;
	private Date fecAvisoPago;
	private String desEstadoPago;
	private String refFolio;
	private String desEstadoSeguro;
    private BigDecimal monto;
	private BigDecimal salarioDiario;
	private String origen;
	private Integer numDias;

	private Long cveIdCompra;

    public Long getCveIdPago() {
        return cveIdPago;
    }

    public void setCveIdPago(Long cveIdPago) {
        this.cveIdPago = cveIdPago;
    }

    public Date getFecInicioPeriodo() {
        return fecInicioPeriodo;
    }

    public void setFecInicioPeriodo(Date fecInicioPeriodo) {
        this.fecInicioPeriodo = fecInicioPeriodo;
    }

    public Date getFecFinPeriodo() {
        return fecFinPeriodo;
    }

    public void setFecFinPeriodo(Date fecFinPeriodo) {
        this.fecFinPeriodo = fecFinPeriodo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecAvisoPago() {
        return fecAvisoPago;
    }

    public void setFecAvisoPago(Date fecAvisoPago) {
        this.fecAvisoPago = fecAvisoPago;
    }

    public String getDesEstadoPago() {
        return desEstadoPago;
    }

    public void setDesEstadoPago(String desEstadoPago) {
        this.desEstadoPago = desEstadoPago;
    }

    public String getRefFolio() {
        return refFolio;
    }

    public void setRefFolio(String refFolio) {
        this.refFolio = refFolio;
    }

    public String getDesEstadoSeguro() {
        return desEstadoSeguro;
    }

    public void setDesEstadoSeguro(String desEstadoSeguro) {
        this.desEstadoSeguro = desEstadoSeguro;
    }

    public BigDecimal getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(BigDecimal salarioDiario) {
        this.salarioDiario = salarioDiario;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getNumDias() {
        return numDias;
    }

    public void setNumDias(Integer numDias) {
        this.numDias = numDias;
    }

    public Long getCveIdCompra() {
        return cveIdCompra;
    }

    public void setCveIdCompra(Long cveIdCompra) {
        this.cveIdCompra = cveIdCompra;
    }
}
