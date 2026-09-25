/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import mx.gob.imss.digital.modelo.cobranza.Patron;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;

/**
 * Clase utilitaria que nos servira para agrupar los datos de los suas por periodos de calculos
 * @author NOVUTECK1
 *
 */
public class PeriodoSUA {
    /**
     * fecha de inicio del periodo a calcular
     */
    private Calendar fechaInicio;
    
    /**
     * Fecha final de calculo en el periodo
     */
    private Calendar fechaFin;
    
    /**
     * PAtron asociado al pago del sua
     */
    private Patron patron;
    /**
     * Lista de trabajadores sobre los cuales se realiza el pago SUA
     */
    private List<Trabajador> trabajadores;
    /**
     * Valor para el factor de actualizacion
     */
    private BigDecimal factorActualizacion;
    /**
     * Valor para el factor de recargo
     */
    private BigDecimal factorRecargo;
    /**
     * Monto a pagar en el periodo
     */
    private BigDecimal monto;
    /**
     * @return the fechaInicio
     */
    public Calendar getFechaInicio() {
        return fechaInicio;
    }
    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    /**
     * @return the fechaFin
     */
    public Calendar getFechaFin() {
        return fechaFin;
    }
    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }
    /**
     * @return the patron
     */
    public Patron getPatron() {
        return patron;
    }
    /**
     * @param patron the patron to set
     */
    public void setPatron(Patron patron) {
        this.patron = patron;
    }
    /**
     * @return the trabajadores
     */
    public List<Trabajador> getTrabajadores() {
        return trabajadores;
    }
    /**
     * @param trabajadores the trabajadores to set
     */
    public void setTrabajadores(List<Trabajador> trabajadores) {
        this.trabajadores = trabajadores;
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
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }
    /**
     * @param monto the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    

}
