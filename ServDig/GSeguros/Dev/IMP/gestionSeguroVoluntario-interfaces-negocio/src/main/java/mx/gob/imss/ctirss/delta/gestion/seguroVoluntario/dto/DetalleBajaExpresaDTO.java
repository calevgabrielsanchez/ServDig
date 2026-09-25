package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO para transferir información de baja expresa al JSP.
 * NO expone entidades JPA (buenas prácticas).
 *
 * @author Sistema Bajas
 * @version 1.0
 */
public class DetalleBajaExpresaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Fecha en que el asegurado confirmó la baja expresa.
     */
    private Date fechaBajaExpresa;

    /**
     * Constructor vacío
     */
    public DetalleBajaExpresaDTO() {
    }

    /**
     * Constructor con todos los campos
     */
    public DetalleBajaExpresaDTO(Date fechaBajaExpresa) {
        this.fechaBajaExpresa = fechaBajaExpresa;
    }

    // ========================================================================
    // GETTERS Y SETTERS
    // ========================================================================

    public Date getFechaBajaExpresa() {
        return fechaBajaExpresa;
    }

    public void setFechaBajaExpresa(Date fechaBajaExpresa) {
        this.fechaBajaExpresa = fechaBajaExpresa;
    }

    @Override
    public String toString() {
        return "DetalleBajaExpresaDTO{" +
                "fechaBajaExpresa=" + fechaBajaExpresa +
                '}';
    }
}
