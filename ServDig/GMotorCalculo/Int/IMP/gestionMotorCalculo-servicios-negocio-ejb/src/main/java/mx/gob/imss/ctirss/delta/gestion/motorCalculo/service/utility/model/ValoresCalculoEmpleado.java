/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

/**
 * Entidad Utilitaria cn los valres del calculo para un empledo y asi poder realizar 
 * dierentes consultas y usos en el flujo
 * @author NOVUTECK1
 *
 */
public class ValoresCalculoEmpleado {

    /**
     * Numero de registro patronal
     */
    private String numeroRegistroPatronal;
    
    /**
     * Fecha de inicio de los calculos
     */
    private Calendar fechaInicioCalculo;
    /**
     * FEcha final de los calculos
     */
    private Calendar fechaFinCalculo;
    /**
     * Private Datos del empleado
     */
    private DatosEmpleado empleado;
    
    /**
     * Salario con el cual se calcula la cuota fija
     */
    private BigDecimal salarioCuotaFija;
    /**
     * SAlario con el que se calculan el resto de las cuotas
     */
    private BigDecimal salarioCalculo;
    /**
     * Salario con el cual se calcula la cuota excedente
     */
    private BigDecimal salarioExedente;
    /**
     * Zona salarial del empleado
     */
    private String zonaSalarial;
    
    /**
     * Periodos de tiempo sobre los que se calcula los montos
     */
    private List<PeriodoCalculoCuota> periodos;
    
    /**
     * Cuotas generadas para el empleado
     */
    private List<RamaCalculo> cuotas; 
    /**
     * Modalidad del trabajador
     */
    private long modalidad;
    
    /**
     * Salario con el cual se calcula la cuota fija UMA
     */
    private BigDecimal salarioCuotaFijaUma;
    
    /**
     * Salario con el cual se calcula la cuota excedente
     */
    private BigDecimal salarioExedenteUma;

    /**
     * Ultimo salario que capturo el usuario
     */
    private BigDecimal ultimoSalarioCotizado;
           
    /**
     * @return the numeroRegistroPatronal
     */
    public String getNumeroRegistroPatronal() {
        return numeroRegistroPatronal;
    }
    /**
     * @param numeroRegistroPatronal the numeroRegistroPatronal to set
     */
    public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
        this.numeroRegistroPatronal = numeroRegistroPatronal;
    }
    /**
     * @return the fechaInicioCalculo
     */
    public Calendar getFechaInicioCalculo() {
        return fechaInicioCalculo;
    }
    /**
     * @param fechaInicioCalculo the fechaInicioCalculo to set
     */
    public void setFechaInicioCalculo(Calendar fechaInicioCalculo) {
        this.fechaInicioCalculo = fechaInicioCalculo;
    }
    /**
     * @return the fechaFinCalculo
     */
    public Calendar getFechaFinCalculo() {
        return fechaFinCalculo;
    }
    /**
     * @param fechaFinCalculo the fechaFinCalculo to set
     */
    public void setFechaFinCalculo(Calendar fechaFinCalculo) {
        this.fechaFinCalculo = fechaFinCalculo;
    }
    /**
     * @return the empleado
     */
    public DatosEmpleado getEmpleado() {
        return empleado;
    }
    /**
     * @param empleado the empleado to set
     */
    public void setEmpleado(DatosEmpleado empleado) {
        this.empleado = empleado;
    }
    /**
     * @return the salarioCuotaFija
     */
    public BigDecimal getSalarioCuotaFija() {
        return salarioCuotaFija;
    }
    /**
     * @param salarioCuotaFija the salarioCuotaFija to set
     */
    public void setSalarioCuotaFija(BigDecimal salarioCuotaFija) {
        this.salarioCuotaFija = salarioCuotaFija;
    }
    /**
     * @return the salarioCalculo
     */
    public BigDecimal getSalarioCalculo() {
        return salarioCalculo;
    }
    /**
     * @param salarioCalculo the salarioCalculo to set
     */
    public void setSalarioCalculo(BigDecimal salarioCalculo) {
        this.salarioCalculo = salarioCalculo;
    }
    /**
     * @return the salarioExedente
     */
    public BigDecimal getSalarioExedente() {
        return salarioExedente;
    }
    /**
     * @param salarioExedente the salarioExedente to set
     */
    public void setSalarioExedente(BigDecimal salarioExedente) {
        this.salarioExedente = salarioExedente;
    }
    /**
     * @return the zonaSalarial
     */
    public String getZonaSalarial() {
        return zonaSalarial;
    }
    /**
     * @param zonaSalarial the zonaSalarial to set
     */
    public void setZonaSalarial(String zonaSalarial) {
        this.zonaSalarial = zonaSalarial;
    }
    /**
     * @return the periodos
     */
    public List<PeriodoCalculoCuota> getPeriodos() {
        return periodos;
    }
    /**
     * @param periodos the periodos to set
     */
    public void setPeriodos(List<PeriodoCalculoCuota> periodos) {
        this.periodos = periodos;
    }
    /**
     * @return the cuotas
     */
    public List<RamaCalculo> getCuotas() {
        return cuotas;
    }
    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(List<RamaCalculo> cuotas) {
        this.cuotas = cuotas;
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
	 * @return the salarioCuotaFijaUma
	 */
	public BigDecimal getSalarioCuotaFijaUma() {
		return salarioCuotaFijaUma;
	}
	
	/**
	 * @param salarioCuotaFijaUma the salarioCuotaFijaUma to set
	 */
	public void setSalarioCuotaFijaUma(BigDecimal salarioCuotaFijaUma) {
		this.salarioCuotaFijaUma = salarioCuotaFijaUma;
	}
	
	/**
	 * @return the salarioExedenteUma
	 */
	public BigDecimal getSalarioExedenteUma() {
		return salarioExedenteUma;
	}
	
	/**
	 * @param salarioExedenteUma the salarioExedenteUma to set
	 */
	public void setSalarioExedenteUma(BigDecimal salarioExedenteUma) {
		this.salarioExedenteUma = salarioExedenteUma;
	}

    public BigDecimal getUltimoSalarioCotizado() {
        return ultimoSalarioCotizado;
    }

    public void setUltimoSalarioCotizado(BigDecimal ultimoSalarioCotizado) {
        this.ultimoSalarioCotizado = ultimoSalarioCotizado;
    }

    @Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("numeroRegistroPatronal", numeroRegistroPatronal)
				.append("fechaInicioCalculo", fechaInicioCalculo)
				.append("fechaFinCalculo", fechaFinCalculo)
				.append("salarioCuotaFija", salarioCuotaFija)
				.append("salarioCalculo", salarioCalculo)
				.append("salarioExedente", salarioExedente)
				.append("zonaSalarial", zonaSalarial)
				.append("modalidad", modalidad)
				.append("salarioCuotaFijaUma", salarioCuotaFijaUma)
                .append("ultimoSalarioCotizado", ultimoSalarioCotizado)
				.append("salarioExedenteUma", salarioExedenteUma).toString();
	}

}
