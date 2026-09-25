package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ElementoBitacoraOmision extends AbstractModel {
    
    private String omision;
    private String pago;
    private String fecSurteEfecto;
	private String justificacion;
	private String fecAnalisis;
    private String fecRechazo;
	private long cveHistOmisiones;
    private long cveIdOmision;


    public long getCveIdOmision() {
        return cveIdOmision;
    }
    public void setCveIdOmision(long cveIdOmision) {
        this.cveIdOmision = cveIdOmision;
    }
    public String getOmision() {
        return omision;
    }
    public void setOmision(String omision) {
        this.omision = omision;
    }
    public String getJustificacion() {
        return justificacion;
    }
    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    public String getFecAnalisis() {
        return fecAnalisis;
    }
    public void setFecAnalisis(String fecAnalisis) {
        this.fecAnalisis = fecAnalisis;
    }
    public String getFecRechazo() {
        return fecRechazo;
    }
    public void setFecRechazo(String fecRechazo) {
        this.fecRechazo = fecRechazo;
    }
    public long getCveHistOmisiones() {
        return cveHistOmisiones;
    }
    public void setCveHistOmisiones(long cveHistOmisiones) {
        this.cveHistOmisiones = cveHistOmisiones;
    }

    public String getPago() {
        return pago;
    }
    public void setPago(String pago) {
        this.pago = pago;
    }
    public String getFecSurteEfecto() {
        return fecSurteEfecto;
    }
    public void setFecSurteEfecto(String fechaSurteEfecto) {
        this.fecSurteEfecto = fecSurteEfecto;
    }
    @Override
	public String toString() {
	return "ElementoBitacora [omision=" + omision
    + ", pago=" + pago + ", fecSurteEfecto=" + fecSurteEfecto
	    + ", justificacion=" + justificacion
	    + ", fecAnalisis=" + fecAnalisis + ", fecRechazo=" + fecRechazo
	    + ", cveHistOmisiones=" + cveHistOmisiones + ", cveIdOmision=" + cveIdOmision + "]";
		}

    public ElementoBitacoraOmision(){}
    
    // Constructor con Strings
    public ElementoBitacoraOmision(String omision, String pago, String fecSurteEfecto, 
                                   String justificacion, String fecAnalisis, String fecRechazo) {
        super();
        this.omision = omision;
        this.pago = pago;
        this.fecSurteEfecto = fecSurteEfecto;
        this.justificacion = justificacion;
        this.fecAnalisis = fecAnalisis;
        this.fecRechazo = fecRechazo;
    }

	private static final SimpleDateFormat DATE_FORMAT = 
        new SimpleDateFormat("dd/MM/yyyy");
    
    // Constructor corregido que recibe Date y formatea
    public ElementoBitacoraOmision(String desOmisiones, String pago, 
                                   Date fecSurteEfecto, String justificacion,
                                   Date fecAnalisis, Date fecRechazoAnalisis,
                                   Long cveHistOmisiones, Long cveIdOmisionesDetectadas) {
        this.omision = desOmisiones;  
        this.pago = pago;
        this.fecSurteEfecto = formatDate(fecSurteEfecto);
        this.justificacion = justificacion;
        this.fecAnalisis = formatDate(fecAnalisis);
        this.fecRechazo = formatDate(fecRechazoAnalisis);  
        this.cveHistOmisiones = cveHistOmisiones != null ? cveHistOmisiones : 0;
        this.cveIdOmision = cveIdOmisionesDetectadas != null ? cveIdOmisionesDetectadas : 0;  
    }
    
    private String formatDate(Date date) {
        return date != null ? DATE_FORMAT.format(date) : null;
    }
    

	

}
