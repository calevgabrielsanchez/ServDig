package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ModServPresDerechohab implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -507553469849795162L;

	private long cveIdModServDerechohab;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private BigDecimal numValorServicio;
	private ServicioPrestDerechohab servicioPrestDerechohab;

	public long getCveIdModServDerechohab() {
		return cveIdModServDerechohab;
	}

	public void setCveIdModServDerechohab(long cveIdModServDerechohab) {
		this.cveIdModServDerechohab = cveIdModServDerechohab;
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

	public BigDecimal getNumValorServicio() {
		return numValorServicio;
	}

	public void setNumValorServicio(BigDecimal numValorServicio) {
		this.numValorServicio = numValorServicio;
	}

	public ServicioPrestDerechohab getServicioPrestDerechohab() {
		return servicioPrestDerechohab;
	}

	public void setServicioPrestDerechohab(
			ServicioPrestDerechohab servicioPrestDerechohab) {
		this.servicioPrestDerechohab = servicioPrestDerechohab;
	}

}
