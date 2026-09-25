package mx.gob.imss.ctirss.delta.model.gestion.seguro;

import java.util.Date;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;


public class PatronPlataformasDigitales extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String cveRegPatron;
	private Integer cveModalidad;
	private Integer numDigVer;
	private Date fecAlta;
	private Date fecBaja;
	private Date fecRegistroAlta;
	
	public PatronPlataformasDigitales(){
		super();
	}

	public String getCveRegPatron() {
		return cveRegPatron;
	}

	public void setCveRegPatron(String cveRegPatron) {
		this.cveRegPatron = cveRegPatron;
	}

	public Integer getCveModalidad() {
		return cveModalidad;
	}

	public void setCveModalidad(Integer cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

	public Integer getNumDigVer() {
		return numDigVer;
	}

	public void setNumDigVer(Integer numDigVer) {
		this.numDigVer = numDigVer;
	}

	public Date getFecAlta() {
		return fecAlta;
	}

	public void setFecAlta(Date fecAlta) {
		this.fecAlta = fecAlta;
	}

	public Date getFecBaja() {
		return fecBaja;
	}

	public void setFecBaja(Date fecBaja) {
		this.fecBaja = fecBaja;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	@Override
	public String toString() {
		return "[cveRegPat:"+this.getCveRegPatron()+"|cveModalidad: "+this.getCveModalidad()+"|numVer:"+this.getNumDigVer()+"]";
	}
	
	
}
