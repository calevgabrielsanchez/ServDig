/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model;

import java.util.Calendar;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.digital.modelo.cobranza.MovimientoEmpleado;

/**
 * Clase utilitara que agrupara los datos de un periodo
 * para el calculo de cuot
 * @author NOVUTECK1
 *
 */
public class PeriodoCalculoCuota {

    /**
     * Fecha inicial del peiodo
     */
    private Calendar fechaInicial;
    /**
     * Fecha final del periodo
     */
    private Calendar fechaFinal;
    /**
     * Orden del periodo, esta propiedad es utilitaria para poder
     * ordenar los periodos de tiempo
     */
    private Integer orden;
    
    /**
     * Lista de descuentos
     */
    private List<DescuentoBeneficio> descuentos;
    
    /**
     * Lista de movimientos que genera un empleado 
     */
    private List<MovimientoEmpleado> movimientos;
    /**
     * @return the fechaInicial
     */
    public Calendar getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaInicial the fechaInicial to set
     */
    public void setFechaInicial(Calendar fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * @return the fechaFinal
     */
    public Calendar getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Calendar fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

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
     * @return the descuentos
     */
    public List<DescuentoBeneficio> getDescuentos() {
        return descuentos;
    }

    /**
     * @param descuentos the descuentos to set
     */
    public void setDescuentos(List<DescuentoBeneficio> descuentos) {
        this.descuentos = descuentos;
    }

    /**
     * @return the movimientos
     */
    public List<MovimientoEmpleado> getMovimientos() {
        return movimientos;
    }

    /**
     * @param movimientos the movimientos to set
     */
    public void setMovimientos(List<MovimientoEmpleado> movimientos) {
        this.movimientos = movimientos;
    }      
    
    
    
}
