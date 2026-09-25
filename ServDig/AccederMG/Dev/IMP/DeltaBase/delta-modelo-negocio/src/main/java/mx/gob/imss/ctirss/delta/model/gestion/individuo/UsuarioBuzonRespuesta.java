package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class UsuarioBuzonRespuesta extends AbstractModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3554416979951601235L;
	private String rfc;
    private String razonSocial;
    private int estatus;
    private String mensaje;

    private Integer claveError;
    private String mensajeError;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getClaveError() {
        return claveError;
    }

    public void setClaveError(Integer claveError) {
        this.claveError = claveError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
