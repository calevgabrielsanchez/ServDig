package mx.gob.imss.digital.modelo.cobranza;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.persona.Parentesco;

import org.apache.commons.lang.builder.ToStringBuilder;
/**
 * 
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "empleadoCuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "numeroSeguridadSocial",
        "salario",
        "cuotaTotal",
        "periodos",
        "conBeneficio",
        "nombreTrabajador",
        "edad",
        "parentesco",
        "curp",
        "cuotaRecargo",
        "aplicaCuestionario",
        "inscripcion",
        "individual"
        
    })
@XmlRootElement(name = "empleadoCuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class EmpleadoCuota implements java.io.Serializable {

	/**
	 * Serial version UID
	 */
    private static final long serialVersionUID = 8572675426302174701L;

    /**
     * numero de seguridad social para identificar el trabajador
     */
    @XmlElement( nillable = false, required = true)
    private String numeroSeguridadSocial;
    
    /**
     * Cuota total por empleado
     */
    @XmlElement( nillable = false, required = true)
    private BigDecimal cuotaTotal;

    /**
     * Cuota total por empleado
     */
    @XmlElement( nillable = true, required = false)
    private BigDecimal salario;
    
    /**
     * Lista de periodos con sus cuotas
     */
    private PeriodoCuota[] periodos;
    /**
     * Indica si el trabajdor obtuvo algun beneficio
     */    
    private boolean conBeneficio = false;
    
    /**
     * Nombre del trabajador asociado a la cuota
     */
    private String nombreTrabajador;
    
    private Integer edad;
    
    private String curp;
    
    private Parentesco parentesco;
    
    /**
     * Total de recargcos cobrados
     */
    private BigDecimal cuotaRecargo = BigDecimal.ZERO;
    
    
    
    private boolean aplicaCuestionario = false;
    private boolean inscripcion = false;
    private boolean individual=false;
    
    
    public String getNumeroSeguridadSocial() {
        return numeroSeguridadSocial;
    }

    public void setNumeroSeguridadSocial(String numeroSeguridadSocial) {
        this.numeroSeguridadSocial = numeroSeguridadSocial;
    }
    
	/**
	 * @return the cuotaTotal
	 */
	public BigDecimal getCuotaTotal() {
		return cuotaTotal;
	}

	/**
	 * @param cuotaTotal the cuotaTotal to set
	 */
	public void setCuotaTotal(BigDecimal cuotaTotal) {
		this.cuotaTotal = cuotaTotal;
	}

	
	/**
	 * @return the salario
	 */
	public BigDecimal getSalario() {
		return salario;
	}

	/**
	 * @param salario the salario to set
	 */
	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}	

	/**
     * @return the periodos
     */
    public PeriodoCuota[] getPeriodos() {
        return periodos;
    }
    

    /**
     * @return the conBeneficio
     */
    public boolean getConBeneficio() {
        return conBeneficio;
    }

    /**
     * @param conBeneficio the conBeneficio to set
     */
    public void setConBeneficio(boolean conBeneficio) {
        this.conBeneficio = conBeneficio;
    }

    /**
     * @param periodos the periodos to set
     */
    public void setPeriodos(PeriodoCuota[] periodos) {
        this.periodos = periodos != null ? periodos.clone() : null;        
    }
    
    

    /**
     * @return the nombreTrabajador
     */
    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    /**
     * @param nombreTrabajador the nombreTrabajador to set
     */
    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
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

    public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public String toString() {
        return new ToStringBuilder(this)
                .append("numeroSeguridadSocial", numeroSeguridadSocial)
                .append("periodos", periodos)
                .toString();
    }
	
	 public boolean getAplicaCuestionario() {
	        return aplicaCuestionario;
	    }

	    /**
	     * @param aplicaCuestionario the aplicaCuestionario to set
	     */
    public void setAplicaCuestionario(boolean aplicaCuestionario) {
        this.aplicaCuestionario = aplicaCuestionario;
    }
    
    
	 public boolean getInscripcion() {
	     return inscripcion;
	 }
	
	 /**
	  * @param inscripcion the inscripcion to set
	  */
	 public void setInscripcion(boolean inscripcion) {
	     this.inscripcion = inscripcion;
	 }
	 
	 public boolean getIndividual() {
	     return individual;
	 }
	
	 /**
	  * @param individual the individual to set
	  */
	 public void setIndividual(boolean individual) {
	     this.individual = individual;
	 }
	 
	 
	

}
