package mx.imss.estrados.dto;

import java.io.Serializable;
import java.util.Date;

public class DelegacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2393880381683482411L;

	/**
	 * 
	 */
	

	public DelegacionDTO() {
	}

	public DelegacionDTO(Integer cveIdDelegacion, String desDeleg,
			String anioIniOper, String claveDelegacion, Integer tipDelegacion,
			Date fecRegistroAlta, Date fecRegistroBaja,
			Date fecRegistroActualizado, String domicilioId, Integer cveCiz) {
		super();
		this.cveIdDelegacion = cveIdDelegacion;
		this.desDeleg = desDeleg;
		this.anioIniOper = anioIniOper;
		this.claveDelegacion = claveDelegacion;
		this.tipDelegacion = tipDelegacion;
		this.fecRegistroAlta = fecRegistroAlta;
		this.fecRegistroBaja = fecRegistroBaja;
		this.fecRegistroActualizado = fecRegistroActualizado;
		this.domicilioId = domicilioId;
		this.cveCiz = cveCiz;
	}

	private Integer cveIdDelegacion;
	private String desDeleg;
	private String anioIniOper;
	private String claveDelegacion;
	private Integer tipDelegacion;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecRegistroActualizado;
	private String domicilioId;
	private Integer cveCiz;
	private String desRIMSSDelegacion;

	public Integer getCveIdDelegacion() {
		return cveIdDelegacion;
	}

	public void setCveIdDelegacion(Integer cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	public String getDesDeleg() {
		return desDeleg;
	}

	public void setDesDeleg(String desDeleg) {
		this.desDeleg = desDeleg;
	}

	public String getAnioIniOper() {
		return anioIniOper;
	}

	public void setAnioIniOper(String anioIniOper) {
		this.anioIniOper = anioIniOper;
	}

	public String getClaveDelegacion() {
		return claveDelegacion;
	}

	public void setClaveDelegacion(String claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}

	public Integer getTipDelegacion() {
		return tipDelegacion;
	}

	public void setTipDelegacion(Integer tipDelegacion) {
		this.tipDelegacion = tipDelegacion;
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

	public String getDomicilioId() {
		return domicilioId;
	}

	public void setDomicilioId(String domicilioId) {
		this.domicilioId = domicilioId;
	}

	public Integer getCveCiz() {
		return cveCiz;
	}

	public void setCveCiz(Integer cveCiz) {
		this.cveCiz = cveCiz;
	}

	public String getDesRIMSSDelegacion() {
		return desRIMSSDelegacion;
	}

	public void setDesRIMSSDelegacion(String desRIMSSDelegacion) {
		this.desRIMSSDelegacion = desRIMSSDelegacion;
	}

}
