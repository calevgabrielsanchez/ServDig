package mx.imss.estrados.dto;

import java.io.Serializable;
import java.util.Date;

public class SubdelegacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4673928877790900413L;

	/**
	 * 
	 */
	

	public SubdelegacionDTO() {
	}

	public SubdelegacionDTO(Integer cveIdSubdelegacion,
			DelegacionDTO delegacionDTO, String desSubdelegacion,
			String anioIniOper, String claveSubdelegacion,
			Date fecRegistroAlta, Date fecRegistroBaja,
			Date fecRegistroActualizado, String domicilioId) {
		super();
		this.cveIdSubdelegacion = cveIdSubdelegacion;
		this.delegacionDTO = delegacionDTO;
		this.desSubdelegacion = desSubdelegacion;
		this.anioIniOper = anioIniOper;
		this.claveSubdelegacion = claveSubdelegacion;
		this.fecRegistroAlta = fecRegistroAlta;
		this.fecRegistroBaja = fecRegistroBaja;
		this.fecRegistroActualizado = fecRegistroActualizado;
		this.domicilioId = domicilioId;
	}

	private Integer cveIdSubdelegacion;
	private DelegacionDTO delegacionDTO;
	private String desSubdelegacion;
	private String anioIniOper;
	private String claveSubdelegacion;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecRegistroActualizado;
	private String domicilioId;
	private String desRIMSSSubDelegacion;

	public Integer getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(Integer cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public DelegacionDTO getDelegacionDTO() {
		return delegacionDTO;
	}

	public void setDelegacionDTO(DelegacionDTO delegacionDTO) {
		this.delegacionDTO = delegacionDTO;
	}

	public String getDesSubdelegacion() {
		return desSubdelegacion;
	}

	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
	}

	public String getAnioIniOper() {
		return anioIniOper;
	}

	public void setAnioIniOper(String anioIniOper) {
		this.anioIniOper = anioIniOper;
	}

	public String getClaveSubdelegacion() {
		return claveSubdelegacion;
	}

	public void setClaveSubdelegacion(String claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
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

	public String getDesRIMSSSubDelegacion() {
		return desRIMSSSubDelegacion;
	}

	public void setDesRIMSSSubDelegacion(String desRIMSSSubDelegacion) {
		this.desRIMSSSubDelegacion = desRIMSSSubDelegacion;
	}

}
