package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO para transferir información de baja por reingreso a RO al JSP.
 * NO expone entidades JPA (buenas prácticas).
 *
 * @author Sistema Bajas Reingreso RO
 * @version 1.0
 */
public class DetalleReingresoRODTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Fecha de baja de Modalidad 40
     */
    private Date fechaBajaModalidad40;

    /**
     * Fecha de reingreso al Régimen Obligatorio
     */
    private Date fechaReingresoRO;

    /**
     * Registro Patronal (destino - RO)
     */
    private String registroPatronal;

    /**
     * Nombre del patrón (destino - RO)
     */
    private String nombrePatron;

    /**
     * Salario diario en RO
     */
    private Double salarioDiario;

    /**
     * Constructor vacío
     */
    public DetalleReingresoRODTO() {
    }

    /**
     * Constructor con todos los campos
     */
    public DetalleReingresoRODTO(Date fechaBajaModalidad40,
                                  Date fechaReingresoRO,
                                  String registroPatronal,
                                  String nombrePatron,
                                  Double salarioDiario) {
        this.fechaBajaModalidad40 = fechaBajaModalidad40;
        this.fechaReingresoRO = fechaReingresoRO;
        this.registroPatronal = registroPatronal;
        this.nombrePatron = nombrePatron;
        this.salarioDiario = salarioDiario;
    }

    // ========================================================================
    // GETTERS Y SETTERS
    // ========================================================================

    public Date getFechaBajaModalidad40() {
        return fechaBajaModalidad40;
    }

    public void setFechaBajaModalidad40(Date fechaBajaModalidad40) {
        this.fechaBajaModalidad40 = fechaBajaModalidad40;
    }

    public Date getFechaReingresoRO() {
        return fechaReingresoRO;
    }

    public void setFechaReingresoRO(Date fechaReingresoRO) {
        this.fechaReingresoRO = fechaReingresoRO;
    }

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }

    public String getNombrePatron() {
        return nombrePatron;
    }

    public void setNombrePatron(String nombrePatron) {
        this.nombrePatron = nombrePatron;
    }

    public Double getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(Double salarioDiario) {
        this.salarioDiario = salarioDiario;
    }

    @Override
    public String toString() {
        return "DetalleReingresoRODTO{" +
                "fechaBajaModalidad40=" + fechaBajaModalidad40 +
                ", fechaReingresoRO=" + fechaReingresoRO +
                ", registroPatronal='" + registroPatronal + '\'' +
                ", nombrePatron='" + nombrePatron + '\'' +
                ", salarioDiario=" + salarioDiario +
                '}';
    }
}
