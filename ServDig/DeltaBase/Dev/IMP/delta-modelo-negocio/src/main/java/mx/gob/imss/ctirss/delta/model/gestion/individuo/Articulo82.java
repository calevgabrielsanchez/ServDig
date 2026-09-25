package mx.gob.imss.ctirss.delta.model.gestion.individuo;


import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Articulo82 extends AbstractModel {
	
	
	private static final long serialVersionUID = 1L;
	private Long cveIdArticulo82;
	private String desArticulo;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecRegistroActualizado;
	
	public Long getCveIdArticulo82() {
		return cveIdArticulo82;
	}
	public void setCveIdArticulo82(Long cveIdArticulo82) {
		this.cveIdArticulo82 = cveIdArticulo82;
	}
	public String getDesArticulo() {
		return desArticulo;
	}
	public void setDesArticulo(String desArticulo) {
		this.desArticulo = desArticulo;
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
