/**
 * 
 */
package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * Clase que representa el LAs cuotas que hay que pagar por periodo 
 * de maximo 2 meses fiscales 
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "periodoCuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "orden",
        "inicioPeriodo",
        "finPeriodo",
        "total",
        "cuotas",
        "movimientos",
        "factorActualizacion",
        "factorRecargo",
        "cuotaRecargo",
        "salarioPeriodo"
        
    })
@XmlRootElement(name = "periodoCuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class PeriodoCuota implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * orden del periodo, esto es para mantener organizada la secuenda de los periodos
     * que se calculan las cuotas
     */
    private Integer orden;
    /**
     * Fecha de inicio de periodo (esta fecha siempre debe ser el primer dia del mes)
     */
    @XmlElement( nillable = false, required = true)
    private Calendar inicioPeriodo;
    /**
     * Fecha final del periodo (estas fechas siempre terminan con el ultimo día)
     */
    @XmlElement( nillable = false, required = true)
    private Calendar finPeriodo;
    /**
     * Cuotas generadas para el empleado
     */
    private RamaCalculo[] cuotas;
    
    /**
     * Lista de movimientos o incidencias asociadas al empleado
     */
    private MovimientoEmpleado[] movimientos;
    /**
     * Total de cargos en el periodo
     */
    @XmlElement( nillable = false, required = true)
    private BigDecimal total = BigDecimal.ZERO;

    /**
     * Factor con el que se calculan la actualizacion de las cuotas
     */
    private BigDecimal factorActualizacion;
    /**
     * Factor con el cual se calculan los recargos
     */
    private BigDecimal factorRecargo;
    /**
     * Total de recargcos cobrados
     */
    private BigDecimal cuotaRecargo = BigDecimal.ZERO;
    
    private BigDecimal salarioPeriodo;
    
    /**
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    /**
     * @return the inicioPeriodo
     */
    public Calendar getInicioPeriodo() {
        return inicioPeriodo;
    }

    /**
     * @param inicioPeriodo the inicioPeriodo to set
     */
    public void setInicioPeriodo(Calendar inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    /**
     * @return the finPeriodo
     */
    public Calendar getFinPeriodo() {
        return finPeriodo;
    }

    /**
     * @param finPeriodo the finPeriodo to set
     */
    public void setFinPeriodo(Calendar finPeriodo) {
        this.finPeriodo = finPeriodo;
    }

    /**
     * @return the cuotas
     */
    public RamaCalculo[] getCuotas() {
        return cuotas;
    }

    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(RamaCalculo[] cuotas) {
        this.cuotas = cuotas != null ? cuotas.clone() : null;
    }

    /**
     * @return the movimientos
     */
    public MovimientoEmpleado[] getMovimientos() {
        return movimientos;
    }

    /**
     * @param movimientos the movimientos to set
     */
    public void setMovimientos(MovimientoEmpleado[] movimientos) {
        this.movimientos = movimientos != null ? movimientos.clone() : null;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the factorActualizacion
     */
    public BigDecimal getFactorActualizacion() {
        return factorActualizacion;
    }

    /**
     * @param factorActualizacion the factorActualizacion to set
     */
    public void setFactorActualizacion(BigDecimal factorActualizacion) {
        this.factorActualizacion = factorActualizacion;
    }

    /**
     * @return the factorRecargo
     */
    public BigDecimal getFactorRecargo() {
        return factorRecargo;
    }

    /**
     * @param factorRecargo the factorRecargo to set
     */
    public void setFactorRecargo(BigDecimal factorRecargo) {
        this.factorRecargo = factorRecargo;
    }

    /**
     * @return the cuotaRecargo
     */
    public BigDecimal getCuotaRecargo() {
        return cuotaRecargo;
    }

    /**
     * @param cuotaRecargo the cuotaRecargo to set
     */
    public void setCuotaRecargo(BigDecimal cuotaRecargo) {
        this.cuotaRecargo = cuotaRecargo;
    }

	public BigDecimal getSalarioPeriodo() {
		return salarioPeriodo;
	}

	public void setSalarioPeriodo(BigDecimal salarioPeriodo) {
		this.salarioPeriodo = salarioPeriodo;
	}
}
