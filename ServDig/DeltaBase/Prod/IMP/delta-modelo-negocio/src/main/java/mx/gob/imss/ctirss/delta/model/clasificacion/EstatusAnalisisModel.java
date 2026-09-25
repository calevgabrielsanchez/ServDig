package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;

/**
 * 
 * @author Jguerra
 *
 */
public class EstatusAnalisisModel extends AbstractModel {

	private static final long serialVersionUID = 1L;
	private Date cveHistEstatus;
	
	private Long cveIdAnalisis;	
	
	private Long cveIdEstatus;	
	
	private String desEstatus;
	
	private String cveUsuario;
	
	private String comentario;
	
	private Long cveIdDelegacion;
	
	private Long cveIdSubdelegacion;
	
	private Fraccion fraccionActual;
	private Fraccion fraccionPropuesta;
	private Fraccion fraccionAnterior;
	private BigDecimal primaAnt;
    private BigDecimal primaDec;
	
	public Date getCveHistEstatus() {
		return cveHistEstatus;
	}

	public void setCveHistEstatus(Date cveHistEstatus) {
		this.cveHistEstatus = cveHistEstatus;
	}

	public Long getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	public void setCveIdAnalisis(Long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public Long getCveIdEstatus() {
		return cveIdEstatus;
	}

	public void setCveIdEstatus(Long cveIdEstatus) {
		this.cveIdEstatus = cveIdEstatus;
	}

	public String getDesEstatus() {
		return desEstatus;
	}

	public void setDesEstatus(String desEstatus) {
		this.desEstatus = desEstatus;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Long getCveIdDelegacion() {
		return cveIdDelegacion;
	}

	public void setCveIdDelegacion(Long cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	public Long getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(Long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public Fraccion getFraccionActual() {
		return fraccionActual;
	}

	public void setFraccionActual(Fraccion fraccionActual) {
		this.fraccionActual = fraccionActual;
	}

	public Fraccion getFraccionPropuesta() {
		return fraccionPropuesta;
	}

	public void setFraccionPropuesta(Fraccion fraccionPropuesta) {
		this.fraccionPropuesta = fraccionPropuesta;
	}

	public Fraccion getFraccionAnterior() {
		return fraccionAnterior;
	}

	public void setFraccionAnterior(Fraccion fraccionAnterior) {
		this.fraccionAnterior = fraccionAnterior;
	}

    public void setPrimaDec(BigDecimal primaDec) {
        this.primaDec = primaDec;
    }

    public BigDecimal getPrimaDec() {
        return primaDec;
    }

	/**
	 * @return the primaAnt
	 */
	public BigDecimal getPrimaAnt() {
		return primaAnt;
	}

	/**
	 * @param primaAnt the primaAnt to set
	 */
	public void setPrimaAnt(BigDecimal primaAnt) {
		this.primaAnt = primaAnt;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[cveHistEstatus=" + cveHistEstatus + ", cveIdAnalisis=" + cveIdAnalisis + ", cveIdEstatus="
				+ cveIdEstatus + ", desEstatus=" + desEstatus + ", cveUsuario=" + cveUsuario + ", comentario="
				+ comentario + ", cveIdDelegacion=" + cveIdDelegacion + ", cveIdSubdelegacion=" + cveIdSubdelegacion
				+ ", fraccionActual=" + fraccionActual + ", fraccionPropuesta=" + fraccionPropuesta
				+ ", fraccionAnterior=" + fraccionAnterior + ", primaDec=" + primaDec +"]";
	}

}
