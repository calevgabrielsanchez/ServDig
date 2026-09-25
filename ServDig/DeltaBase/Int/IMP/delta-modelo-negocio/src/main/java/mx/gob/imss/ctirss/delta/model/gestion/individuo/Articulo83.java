package mx.gob.imss.ctirss.delta.model.gestion.individuo;


import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Articulo83 extends AbstractModel {
	
	
	private static final long serialVersionUID = 1L;
	private Long cveIdArticulo83;
	private String desArticulo;
	private Integer tiempoEspera;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecRegistroActualizado;
	
	public Long getCveIdArticulo83() {
		return cveIdArticulo83;
	}
	public void setCveIdArticulo83(Long cveIdArticulo83) {
		this.cveIdArticulo83 = cveIdArticulo83;
	}
	public String getDesArticulo() {
		return desArticulo;
	}
	public void setDesArticulo(String desArticulo) {
		this.desArticulo = desArticulo;
	}
	public Integer getTiempoEspera() {
		return tiempoEspera;
	}
	public void setTiempoEspera(Integer tiempoEspera) {
		this.tiempoEspera = tiempoEspera;
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
