package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.interfaces.MensajeError;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Clase que representa los datos para el calculo de cuatos de los trabajadores asociados a un patron
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosCalculoCuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "fechaInicioCalculo",
        "fechaFinCalculo",
        "numeroRegistroPatronal",
        "modalidad",
        "zonaSalarial",
        "concepto",
        "renovacion",
        "empleados",
        "aplicaCuestionario",
        "salarioMinimo",
        "recargos",
        "errorFormGeneral",
        "idEmpleador",
        "aplicaRecargoPorFechaBaja"
    })
@XmlRootElement(name = "datosCalculoCuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class DatosCalculoCuota implements Serializable, MensajeError {
	
    /**
     * Serial version UID
     */
	private static final long serialVersionUID = 1L;

	/**
	 * Modalidad del patron
	 */
	@XmlElement( nillable = false, required = true)
    private long modalidad;
    /**
     * Zona salarial a la que pertenece el patron
     */
	@XmlElement( nillable = true, required = false)
    private String zonaSalarial;
    /**
     * Fecha de inicio de los calculos
     */
	@XmlElement( nillable = false, required = true)
    private Calendar fechaInicioCalculo;
    /**
     * FEcha final de los calculos
     */
	@XmlElement( nillable = false, required = true)
    private Calendar fechaFinCalculo;
    /**
     * Numero del registro patronal 
     */
	@XmlElement( nillable = true, required = false)
    private String numeroRegistroPatronal;
	/**
	 * Concepto de la cotizacion
	 */
	private Long concepto;
	/**
	 * Indica si la cotizacion se trata de una renovacion
	 */
	private Boolean renovacion;
	/**
     * Indica si la cotizacion se trata de una renovacion
     */
    private Boolean aplicaCuestionario;
    /**
     * Lista de emplados, sobre los cuales se generan las cuotas
     */
    private DatosEmpleado[] empleados;
    /**
     * Salario minimo de la zona
     */
    private BigDecimal salarioMinimo;
    /**
     * Mensaje de error 
     */
    private String errorFormGeneral;
    
    /**
     * Idica si la cotizacion aplica recargos.
     */
    private Boolean recargos;
    
    /**
     * IdPersona del patron que contrata el seguro
     * Se agrega este atributo por el cambio de seguro doméstico
     * con el cuál se permite crear domicilios de centro de trabajo (NRPs)
     * dentro del trámite de aseguramiento
     */
    private Long idEmpleador;
    
    private Boolean aplicaRecargoPorFechaBaja = false;
    
    public long getModalidad() {
        return modalidad;
    }

    public void setModalidad(long modalidad) {
        this.modalidad = modalidad;
    }

    public String getZonaSalarial() {
        return zonaSalarial;
    }

    public void setZonaSalarial(String zonaSalarial) {
        this.zonaSalarial = zonaSalarial;
    }

    public Calendar getFechaInicioCalculo() {
        return fechaInicioCalculo;
    }

    public void setFechaInicioCalculo(Calendar fechaInicioCalculo) {
        this.fechaInicioCalculo = fechaInicioCalculo;
    }

    public Calendar getFechaFinCalculo() {
        return fechaFinCalculo;
    }

    public void setFechaFinCalculo(Calendar fechaFinCalculo) {
        this.fechaFinCalculo = fechaFinCalculo;
    }   

    public String getNumeroRegistroPatronal() {
        return numeroRegistroPatronal;
    }

    public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
        this.numeroRegistroPatronal = numeroRegistroPatronal;
    }

    public DatosEmpleado[] getEmpleados() {
        return empleados;
    }

    public void setEmpleados(DatosEmpleado[] empleados) {
        this.empleados = empleados != null ? empleados.clone() : null;
    }

    
    /**
     * @return the concepto
     */
    public Long getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(Long concepto) {
        this.concepto = concepto;
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
    

    /**
     * @return the errorFormGeneral
     */
    public String getErrorFormGeneral() {
        return errorFormGeneral;
    }

    
    /**
     * @return the aplicaCuestionario
     */
    public Boolean getAplicaCuestionario() {
        return aplicaCuestionario;
    }

    /**
     * @param aplicaCuestionario the aplicaCuestionario to set
     */
    public void setAplicaCuestionario(Boolean aplicaCuestionario) {
        this.aplicaCuestionario = aplicaCuestionario;
    }

    /**
     * @param errorFormGeneral the errorFormGeneral to set
     */
    public void setErrorFormGeneral(String errorFormGeneral) {
        this.errorFormGeneral = errorFormGeneral;
    }
    
    

    /**
     * @return the salarioMinimo
     */
    public BigDecimal getSalarioMinimo() {
        return salarioMinimo;
    }

    /**
     * @param salarioMinimo the salarioMinimo to set
     */
    public void setSalarioMinimo(BigDecimal salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("modalidad", modalidad)
                .append("zonaSalarial", zonaSalarial)
                .append("fechaInicioCalculo", fechaInicioCalculo)
                .append("fechaFinCalculo", fechaFinCalculo)
                .append("numeroRegistroPatronal", numeroRegistroPatronal)
                .append("empleados", empleados)
                .toString();
    }

    /**
     * @return the recargos
     */
    public Boolean getRecargos() {
        return recargos;
    }

    /**
     * @param recargos the recargos to set
     */
    public void setRecargos(Boolean recargos) {
        this.recargos = recargos;
    }

	/**
	 * @return the idEmpleador
	 */
	public Long getIdEmpleador() {
		return idEmpleador;
	}

	/**
	 * @param idEmpleador the idEmpleador to set
	 */
	public void setIdEmpleador(Long idEmpleador) {
		this.idEmpleador = idEmpleador;
	}

	public Boolean getAplicaRecargoPorFechaBaja() {
		return aplicaRecargoPorFechaBaja;
	}

	public void setAplicaRecargoPorFechaBaja(Boolean aplicaRecargoPorFechaBaja) {
		this.aplicaRecargoPorFechaBaja = aplicaRecargoPorFechaBaja;
	}
    
    

}

