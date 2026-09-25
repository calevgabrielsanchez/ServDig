package mx.gob.imss.ctirss.delta.model.gestion.individuo;


import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Articulo84 extends AbstractModel {
	
	
	private static final long serialVersionUID = 1L;
	private Long cveIdArticulo84;
	private String desArticulo;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecRegistroActualizado;
	
	public Long getCveIdArticulo84() {
		return cveIdArticulo84;
	}
	public void setCveIdArticulo84(Long cveIdArticulo84) {
		this.cveIdArticulo84 = cveIdArticulo84;
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
