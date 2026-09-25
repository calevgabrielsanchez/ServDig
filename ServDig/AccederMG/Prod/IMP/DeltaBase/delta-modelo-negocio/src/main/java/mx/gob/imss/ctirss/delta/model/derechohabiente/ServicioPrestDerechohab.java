package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ServicioPrestDerechohab implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9069361920630183330L;

	private long cveIdServicioDerechohab;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private BigDecimal indServicioPension;
	private String nomServicioDerechohab;

	public long getCveIdServicioDerechohab() {
		return cveIdServicioDerechohab;
	}

	public void setCveIdServicioDerechohab(long cveIdServicioDerechohab) {
		this.cveIdServicioDerechohab = cveIdServicioDerechohab;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
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

	public BigDecimal getIndServicioPension() {
		return indServicioPension;
	}

	public void setIndServicioPension(BigDecimal indServicioPension) {
		this.indServicioPension = indServicioPension;
	}

	public String getNomServicioDerechohab() {
		return nomServicioDerechohab;
	}

	public void setNomServicioDerechohab(String nomServicioDerechohab) {
		this.nomServicioDerechohab = nomServicioDerechohab;
	}

}
