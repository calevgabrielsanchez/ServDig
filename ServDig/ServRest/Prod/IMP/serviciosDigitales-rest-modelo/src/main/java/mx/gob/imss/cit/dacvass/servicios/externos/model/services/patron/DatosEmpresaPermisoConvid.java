package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DatosEmpresaPermisoConvid implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8400218171149192725L;
	private String rfc;
	private Date fecAlta;
	private BigDecimal numTrabajadores;
	
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public Date getFecAlta() {
		return fecAlta;
	}
	public void setFecAlta(Date fecAlta) {
		this.fecAlta = fecAlta;
	}
	public BigDecimal getNumTrabajadores() {
		return numTrabajadores;
	}
	public void setNumTrabajadores(BigDecimal numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}
	
}
