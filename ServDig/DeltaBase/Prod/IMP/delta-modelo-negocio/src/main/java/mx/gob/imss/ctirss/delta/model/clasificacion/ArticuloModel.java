package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.math.BigDecimal;
import java.math.BigInteger;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ArticuloModel extends AbstractModel {

	private BigDecimal cveIdArticulo;
	private BigInteger cveIdDelegacion;
	private BigInteger cveIdSubdelegacion;

    private String desFraccion;
    private String desInciso;
    private String desDelegacion;
    private String desSubdelegacion;
    
    private BigDecimal numArticulo;
    
    private BigDecimal cveIdClem;
        
	public BigDecimal getCveIdArticulo() {
		return cveIdArticulo;
	}
	public void setCveIdArticulo(BigDecimal cveIdArticulo) {
		this.cveIdArticulo = cveIdArticulo;
	}
	public String getDesFraccion() {
		return desFraccion;
	}
	public void setDesFraccion(String desFraccion) {
		this.desFraccion = desFraccion;
	}
	public String getDesInciso() {
		return desInciso;
	}
	public void setDesInciso(String desInciso) {
		this.desInciso = desInciso;
	}
	public String getDesDelegacion() {
		return desDelegacion;
	}
	public void setDesDelegacion(String desDelegacion) {
		this.desDelegacion = desDelegacion;
	}
	public String getDesSubdelegacion() {
		return desSubdelegacion;
	}
	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
	}
	public BigInteger getCveIdDelegacion() {
		return cveIdDelegacion;
	}
	public void setCveIdDelegacion(BigInteger cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}
	public BigInteger getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}
	public void setCveIdSubdelegacion(BigInteger cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}
	public BigDecimal getNumArticulo() {
		return numArticulo;
	}
	public void setNumArticulo(BigDecimal numArticulo) {
		this.numArticulo = numArticulo;
	}
	
	public BigDecimal getCveIdClem() {
		return cveIdClem;
	}
	public void setCveIdClem(BigDecimal cveIdClem) {
		this.cveIdClem = cveIdClem;
	}
	@Override
	public String toString() {
		return "ArticuloModel [cveIdArticulo=" + cveIdArticulo + ", cveIdClem="
				+ cveIdClem + ", cveIdDelegacion=" + cveIdDelegacion
				+ ", cveIdSubdelegacion=" + cveIdSubdelegacion
				+ ", desDelegacion=" + desDelegacion + ", desFraccion="
				+ desFraccion + ", desInciso=" + desInciso
				+ ", desSubdelegacion=" + desSubdelegacion + ", numArticulo="
				+ numArticulo + "]";
	}

}
