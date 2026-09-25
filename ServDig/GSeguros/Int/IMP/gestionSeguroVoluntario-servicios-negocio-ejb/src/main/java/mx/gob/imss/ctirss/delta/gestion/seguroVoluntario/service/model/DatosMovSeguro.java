/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.persona.Fisica;

/**
 * Clase con un modelo de paso para poder generar la estructura de datos par
 * soindo de un seguro a dar de alta o baja
 *
 * @author NOVUTECK1
 *
 */
public class DatosMovSeguro {

    /**
     * Indica las personas aociadas al mismo seguro a dar de alta o baja
     */
    private Fisica persona;
    /**
     * Numero de registro patronal asociado a los movimientos
     */
    private String nrp;
    /**
     * Indica si el seguro se dio con beneficio
     */
    private boolean conBeneficio;
    /**
     * Salario del trabajador
     */
    private BigDecimal salario = BigDecimal.ZERO;
    /**
     * FEcha en que se debe generar el movimiento
     */
    private Date fechaMovimiento;
    /**
     * Numero de registro patronal asociado a los movimientos con modaliad 35
     */
    private String nrp35;
    /**
     * Indica si el movimiento se aplica a futuro
     */
    private boolean movimientoFuturo = false;
    /**
     * Indica si el movimiento es de un seguro bimesral.
     */
    private boolean pagoBimestral = false;

    /**
     * Indica si el movimiento es renovacion extemporanea.
     */
    private boolean extemporaneo = false;
    /**
     * Fecha de inicio del seguro
     */
    private Date fechaInicio;

    private Domicilio domicilioSeguro;

    private boolean aplicaMovBajaIntegrado = false;

    private int idTipoTrabajador;

    /**
     * @return the persona
     */
    public Fisica getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Fisica persona) {
        this.persona = persona;
    }

    /**
     * @return the nrp
     */
    public String getNrp() {
        return nrp;
    }

    /**
     * @param nrp the nrp to set
     */
    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    /**
     * @return the conBeneficio
     */
    public boolean isConBeneficio() {
        return conBeneficio;
    }

    /**
     * @param conBeneficio the conBeneficio to set
     */
    public void setConBeneficio(boolean conBeneficio) {
        this.conBeneficio = conBeneficio;
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
     * @return the fechaMovimiento
     */
    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    /**
     * @param fechaMovimiento the fechaMovimiento to set
     */
    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    /**
     * @return the nrp35
     */
    public String getNrp35() {
        return nrp35;
    }

    /**
     * @param nrp35 the nrp35 to set
     */
    public void setNrp35(String nrp35) {
        this.nrp35 = nrp35;
    }

    /**
     * @return the movimientoFuturo
     */
    public boolean isMovimientoFuturo() {
        return movimientoFuturo;
    }

    /**
     * @param movimientoFuturo the movimientoFuturo to set
     */
    public void setMovimientoFuturo(boolean movimientoFuturo) {
        this.movimientoFuturo = movimientoFuturo;
    }

    /**
     * @return the pagoBimestral
     */
    public boolean isPagoBimestral() {
        return pagoBimestral;
    }

    /**
     * @param pagoBimestral the pagoBimestral to set
     */
    public void setPagoBimestral(boolean pagoBimestral) {
        this.pagoBimestral = pagoBimestral;
    }

    /**
     * @return the extemporaneo
     */
    public boolean isExtemporaneo() {
        return extemporaneo;
    }

    /**
     * @param extemporaneo the extemporaneo to set
     */
    public void setExtemporaneo(boolean extemporaneo) {
        this.extemporaneo = extemporaneo;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Domicilio getDomicilioSeguro() {
        return domicilioSeguro;
    }

    public void setDomicilioSeguro(Domicilio domicilioSeguro) {
        this.domicilioSeguro = domicilioSeguro;
    }

    public boolean isAplicaMovBajaIntegrado() {
        return aplicaMovBajaIntegrado;
    }

    public void setAplicaMovBajaIntegrado(boolean aplicaMovBajaIntegrado) {
        this.aplicaMovBajaIntegrado = aplicaMovBajaIntegrado;
    }

    public int getIdTipoTrabajador() {
        return idTipoTrabajador;
    }

    public void setIdTipoTrabajador(int idTipoTrabajador) {
        this.idTipoTrabajador = idTipoTrabajador;
    }
}
