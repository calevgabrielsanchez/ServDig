package mx.gob.imss.ctirss.delta.model.riesgosTrabajo;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

public class PatronRiesgosTrabajo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4011325808476626022L;

	
	
	private long idPersona;
    private String razonSocial;
    private String nrp;
    private long tipoPersona;
    private Long delegacion;
    private String numCilco;
    private Date inicioPeriodo;
    private Date finPeriodo;
    private Subdelegacion subdelegacion;
    private Long idPatronSujetoObligado;
    
    public PatronRiesgosTrabajo() {
    	super();
    }
    
    public PatronRiesgosTrabajo(Long idPatronSujetoObligado) {
    	this();
    	this.idPatronSujetoObligado = idPatronSujetoObligado;
    }
    
    public PatronRiesgosTrabajo(Long idPatronSujetoObligado, String razonSocial) {
    	this(idPatronSujetoObligado);
    	this.razonSocial = razonSocial;
    }
    
    public PatronRiesgosTrabajo(Long idPatronSujetoObligado, String razonSocial, String nrp) {
    	this(idPatronSujetoObligado, razonSocial);
    	this.nrp = nrp;
    }
    
    public long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(long idPersona) {
        this.idPersona = idPersona;
    }
   
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public long getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(long tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Long getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(Long delegacion) {
        this.delegacion = delegacion;
    }

    public String getNumCilco() {
        return numCilco;
    }

    public void setNumCilco(String numCilco) {
        this.numCilco = numCilco;
    }

    public Date getInicioPeriodo() {
        return inicioPeriodo;
    }

    public void setInicioPeriodo(Date inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public Date getFinPeriodo() {
        return finPeriodo;
    }

    public void setFinPeriodo(Date finPeriodo) {
        this.finPeriodo = finPeriodo;
    }

	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}

	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public Long getIdPatronSujetoObligado() {
		return idPatronSujetoObligado;
	}

	public void setIdPatronSujetoObligado(Long idPatronSujetoObligado) {
		this.idPatronSujetoObligado = idPatronSujetoObligado;
	}

	@Override
    public String toString() {
        return "PatronRiesgosTrabajo{" + "idPersona=" + idPersona + ", razonSocial=" + razonSocial + ", nrp=" + nrp + ", tipoPersona=" + tipoPersona + ", delegacion=" + delegacion + ", numCilco=" + numCilco + ", inicioPeriodo=" + inicioPeriodo + ", finPeriodo=" + finPeriodo + '}';
    }
    
    

}
