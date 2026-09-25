
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class SubdelegacionRimss extends AbstractModel {
	
	private static final long serialVersionUID = 1L;
	
	private Long cveDelegacion;
    private Long cveSubdelegacion;
    private String descDelegacion;
    private String descSubDelegacion;
    private String tipo;
    
    private Date fecRegistroAlta;
    private Date fecRegistroBaja;
    private Date fecRegistroActualizado;
    
	@Override
	public String toString() {
		return "SubdelegacionRimss [cveDelegacion=" + cveDelegacion
				+ ", cveSubdelegacion=" + cveSubdelegacion
				+ ", descDelegacion=" + descDelegacion + ", descSubDelegacion="
				+ descSubDelegacion + ", tipo=" + tipo + ", fecRegistroAlta="
				+ fecRegistroAlta + ", fecRegistroBaja=" + fecRegistroBaja
				+ ", fecRegistroActualizado=" + fecRegistroActualizado + "]";
	}
	
	public Long getCveDelegacion() {
		return cveDelegacion;
	}
	public void setCveDelegacion(Long cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public Long getCveSubdelegacion() {
		return cveSubdelegacion;
	}
	public void setCveSubdelegacion(Long cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}
	public String getDescDelegacion() {
		return descDelegacion;
	}
	public void setDescDelegacion(String descDelegacion) {
		this.descDelegacion = descDelegacion;
	}
	public String getDescSubDelegacion() {
		return descSubDelegacion;
	}
	public void setDescSubDelegacion(String descSubDelegacion) {
		this.descSubDelegacion = descSubDelegacion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
    
    
    
    
}