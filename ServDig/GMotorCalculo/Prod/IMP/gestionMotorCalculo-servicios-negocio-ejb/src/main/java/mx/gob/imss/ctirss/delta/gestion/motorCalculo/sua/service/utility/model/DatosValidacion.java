/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.sua.service.utility.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.imss.digital.modelo.cobranza.SumarioPatronal;
import mx.gob.imss.digital.modelo.cobranza.Trabajador;

/**
 * Clase utilitaria que contiene los datos para generar un registro de vlidación
 * @author NOVUTECK1
 *
 */
public class DatosValidacion {
    /**
     * Lista de trabajadores
     */
    private List<Trabajador> trabajadores;
    /**
     * Sumario patronal
     */
    private SumarioPatronal sumario;
    /**
     * FEcha de inicio del periodo
     */
    private Calendar fechaInicio;
    private Calendar fechaReferencia;
    /**
     * Lista de dias feriados
     */
    private List<Date> diasFeriados;
    /**
     * Indica si el archivo se calculo con beneficios
     */
    private boolean conbeneficio;
    /**
     * Modalidad del empleador
     */
    private long modalidad;
    /**
     * version del archivo de pago
     */
    private String versionSUA;
    /**
     * Indica si la generacion depagos se trata de una renovacion,
     */
    private Boolean renovacion;
    
    private Boolean aplicaRecargoPorFechaBaja;
    
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
     * @return the sumario
     */
    public SumarioPatronal getSumario() {
        return sumario;
    }
    /**
     * @param sumario the sumario to set
     */
    public void setSumario(SumarioPatronal sumario) {
        this.sumario = sumario;
    }
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
     * @return the diasFeriados
     */
    public List<Date> getDiasFeriados() {
        return diasFeriados;
    }
    /**
     * @param diasFeriados the diasFeriados to set
     */
    public void setDiasFeriados(List<Date> diasFeriados) {
        this.diasFeriados = diasFeriados;
    }
    /**
     * @return the conbeneficio
     */
    public boolean isConbeneficio() {
        return conbeneficio;
    }
    /**
     * @param conbeneficio the conbeneficio to set
     */
    public void setConbeneficio(boolean conbeneficio) {
        this.conbeneficio = conbeneficio;
    }
    /**
     * @return the modalidad
     */
    public long getModalidad() {
        return modalidad;
    }
    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(long modalidad) {
        this.modalidad = modalidad;
    }
    /**
     * @return the versionSUA
     */
    public String getVersionSUA() {
        return versionSUA;
    }
    /**
     * @param versionSUA the versionSUA to set
     */
    public void setVersionSUA(String versionSUA) {
        this.versionSUA = versionSUA;
    }
    /**
     * @return the renovacion
     */
    public Boolean getRenovacion() {
        return renovacion;
    }
    /**
     * @param renovacion the renovacion to set
     */
    public void setRenovacion(Boolean renovacion) {
        this.renovacion = renovacion;
    }

	public Boolean getAplicaRecargoPorFechaBaja() {
		return aplicaRecargoPorFechaBaja;
	}

	public void setAplicaRecargoPorFechaBaja(Boolean aplicaRecargoPorFechaBaja) {
		this.aplicaRecargoPorFechaBaja = aplicaRecargoPorFechaBaja;
	}

	public Calendar getFechaReferencia() {
		return fechaReferencia;
	}

	public void setFechaReferencia(Calendar fechaReferencia) {
		this.fechaReferencia = fechaReferencia;
	}

}
