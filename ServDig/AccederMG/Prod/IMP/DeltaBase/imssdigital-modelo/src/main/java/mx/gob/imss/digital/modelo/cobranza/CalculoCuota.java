package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calculoCuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza", propOrder = {
        "fechaInicioCalculo",
        "fechaFinCalculo",
        "numeroRegistroPatronal",
        "modalidad",
        "zonaSalarial",
        "cuotaTotal",
        "cuotaRecargo",
        "conBeneficio",
        "conRecargos",
        "aplicaRecargoPorFechaBaja",
        "versionSUA",
        "renovacion",
	    "empleados"
	})
@XmlRootElement(name = "calculoCuota", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class CalculoCuota implements Serializable {

    /**
     * Serial version uid
     */
    private static final long serialVersionUID = -4874097434629057523L;
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
     * Numero de registro patronal
     */
    private String numeroRegistroPatronal;
    /**
     * Modalidad del patron
     */
    @XmlElement( nillable = false, required = true)
    private long modalidad;
    /**
     * Zona salarial a la que pertenece el patron
     */
    private String zonaSalarial;
    /**
     * cuota total a pagar por el empleador
     */
    @XmlElement( nillable = false, required = true)
    private BigDecimal cuotaTotal;
    
    /**
     * Indica si el calculo se genero con algun beneficio del empleado o del trabajador
     */
    private Boolean conBeneficio = false;
    /**
     * Indica si los datos viene o se deben calcular recargos
     */    
    private Boolean conRecargos;
    /**
     * Identificador de la vesion para gnear el sua, (esto es
     * para lageneracion de archivos SUA , si el dato viene nulo o vacio se 
     * tomara como default las reglas de ivro para generarlo)
     */
    private String versionSUA;
    /**
     * Lista de cuotas por empleado
     */
    private EmpleadoCuota[] empleados;
    
    @XmlTransient
    private Long concepto;
    /**
     * Indica si el calculo se trata de una renovacion
     */
    private Boolean renovacion;
    
    /**
     * Total de recargcos cobrados
     */
    private BigDecimal cuotaRecargo = BigDecimal.ZERO;
    
    private Boolean aplicaRecargoPorFechaBaja = false;
    
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
     * @return the conBeneficio
     */
    public Boolean getConBeneficio() {
        return conBeneficio;
    }
    
    /**
     * @param conBeneficio the conBeneficio to set
     */
    public void setConBeneficio(Boolean conBeneficio) {
        this.conBeneficio = conBeneficio;
    }

    /**
     * @return the conRecargos
     */
    public Boolean getConRecargos() {
        return conRecargos;
    }

    /**
     * @param conRecargos the conRecargos to set
     */
    public void setConRecargos(Boolean conRecargos) {
        this.conRecargos = conRecargos;
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
     * @return the empleados
     */
    public EmpleadoCuota[] getEmpleados() {
        return empleados;
    }

    /**
     * @param empleados the empleados to set
     */
    public void setEmpleados(EmpleadoCuota[] empleados) {
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

    public Boolean getAplicaRecargoPorFechaBaja() {
		return aplicaRecargoPorFechaBaja;
	}

	public void setAplicaRecargoPorFechaBaja(Boolean aplicaRecargoPorFechaBaja) {
		this.aplicaRecargoPorFechaBaja = aplicaRecargoPorFechaBaja;
	}

	/**
     * To string
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("numeroRegistroPatronal", numeroRegistroPatronal)
                .append("modalidad", modalidad)
                .append("zonaSalarial", zonaSalarial)
                .append("cuotaTotal", cuotaTotal)
                .append("empleados", empleados)
                .append("aplicaRecargoPorFechaBaja", aplicaRecargoPorFechaBaja)
                .toString();
    }

}
