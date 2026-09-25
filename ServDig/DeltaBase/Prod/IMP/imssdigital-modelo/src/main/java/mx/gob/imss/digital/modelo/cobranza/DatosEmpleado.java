package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Datos del empleado para el calculo de su cuota
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosEmpleado", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "numeroSeguridadSocial",
        "salario",
        "movimientos",
        "edad",
        "parentesco",
        "curp",
        "aplicaCuestionario",
        "inscripcion",
        "individual"
    })
@XmlRootElement(name = "datosEmpleado", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class DatosEmpleado implements Serializable {

    /**
     * Serial version uid
     */
    private static final long serialVersionUID = -8976940579208692552L;  
    
    /**
     * Salario del empleado
     */
    @XmlElement( nillable = true, required = false)
    private BigDecimal salario;
    /**
     * Numero de seguridad social
     */
    @XmlElement( nillable = false, required = true)
    private String numeroSeguridadSocial;
    /**
     * Edad
     */
    @XmlElement( nillable = false, required = false)
    private Integer edad;
    /**
	 * Parentesco
	 */
	@XmlElement( nillable = false, required = false)
    private Long parentesco;
	/**
	 * CURP
	 */
	@XmlElement( nillable = false, required = false)
    private String curp;
    
    /**
     * Movimientos o incidencias asociadas al trabajador
     */
    private MovimientoEmpleado[] movimientos;
    
    @XmlElement( nillable = false, required = false)
    private boolean aplicaCuestionario = false;
    
    @XmlElement( nillable = false, required = false)
    private boolean inscripcion = false;
    
    @XmlElement( nillable = false, required = false)
    private boolean individual=false;
    
    
    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getNumeroSeguridadSocial() {
        return numeroSeguridadSocial;
    }

    public void setNumeroSeguridadSocial(String numeroSeguridadSocial) {
        this.numeroSeguridadSocial = numeroSeguridadSocial;
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
	
	
    
    public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Long getParentesco() {
		return parentesco;
	}

	public void setParentesco(Long parentesco) {
		this.parentesco = parentesco;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
	public String toString() {
        return new ToStringBuilder(this)
                .append("salario", salario)
                .append("numeroSeguridadSocial", numeroSeguridadSocial)
                .append("edad", edad)
                .append("parentesco",parentesco)
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
